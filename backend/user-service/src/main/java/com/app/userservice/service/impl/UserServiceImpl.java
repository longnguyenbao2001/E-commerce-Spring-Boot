/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service.impl;

import com.app.userservice.component.DTOConverter;
import com.app.userservice.service.EncryptionService;
import com.app.userservice.service.JWTService;
import com.app.userservice.service.RoleService;
import com.app.userservice.dao.UserRepository;
import com.app.userservice.dao.VerificationTokenRepository;
import com.app.userservice.dto.AuthUserDTO;
import com.app.userservice.dto.ForgotPasswordRequestDTO;
import com.app.userservice.dto.ResetPasswordRequestDTO;
import com.app.userservice.dto.SignInUserRequestDTO;
import com.app.userservice.dto.SignUpUserRequestDTO;
import com.app.userservice.dto.SignInUserResponseDTO;
import com.app.userservice.dto.UserDTO;
import com.app.userservice.entity.Roles;
import com.app.userservice.entity.Users;
import com.app.userservice.entity.VerificationTokens;
import com.app.userservice.exception.EmailFailureException;
import com.app.userservice.exception.EmailNotAssosiatedWithUserException;
import com.app.userservice.service.UserService;
import com.app.userservice.exception.UserAlreadyExistsException;
import com.app.userservice.exception.UserNotVerifiedException;
import com.app.userservice.exception.UserNotFoundException;
import com.app.userservice.service.EmailService;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 *
 * @author user
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${app.mail.verification.resendAfter}")
    private Integer resendAfter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private DTOConverter dtoConverter;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private Environment env;

    private VerificationTokens createVerificationToken(Users user) {
        VerificationTokens verificationTokens = new VerificationTokens();
        verificationTokens.setToken(jwtService.generateJWTVerificationToken(user));
        verificationTokens.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationTokens.setUsers(user);
        user.getVerificationTokensList().add(verificationTokens);

        return verificationTokens;
    }

    @Override
    public Optional<Users> findUserByUsername(String username) {
        Optional<Users> result = userRepository.findByUsername(username);

        return result;
    }

    @Override
    public Optional<Users> findUserByUserId(Long userId) throws UserNotFoundException {
        Optional<Users> res = userRepository.findById(userId);
        if (!res.isPresent()) {
            throw new UserNotFoundException();
        }

        return res;
    }

    @Override
    @Transactional
    public UserDTO signUp(SignUpUserRequestDTO signUpUserRequestDTO)
            throws UserAlreadyExistsException, EmailFailureException {
        Optional<Users> existingUser = this.findUserByUsername(signUpUserRequestDTO.getUsername());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        Users user = new Users();
        user.setEmail(signUpUserRequestDTO.getEmail());
        user.setUsername(signUpUserRequestDTO.getUsername());
        user.setFirstName(signUpUserRequestDTO.getFirstName());
        user.setLastName(signUpUserRequestDTO.getLastName());
        user.setPassword(encryptionService.encryptPassword(signUpUserRequestDTO.getPassword()));

        Roles role = roleService.getRoleByName(env.getProperty("role.user")).get(0);
        if (role != null) {
            user.setRoles(role);
        }

        user = userRepository.save(user);

        VerificationTokens verificationTokens = this.createVerificationToken(user);
        emailService.sendVerificationEmail(verificationTokens);
        verificationTokenRepository.save(verificationTokens);

        return dtoConverter.convertUserToUserDTO(user);
    }

    @Override
    public SignInUserResponseDTO signIn(SignInUserRequestDTO signInUserRequestDTO)
            throws UserNotVerifiedException, EmailFailureException, UserNotFoundException {
        Optional<Users> existingUser = this.findUserByUsername(signInUserRequestDTO.getUsername());
        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        SignInUserResponseDTO signInUserResponseDTO = null;
        Users user = existingUser.get();
        if (encryptionService.verifyPassword(signInUserRequestDTO.getPassword(), user.getPassword())) {
            if (user.getEmailVerified()) {
                signInUserResponseDTO = new SignInUserResponseDTO();
                signInUserResponseDTO.setJwtAccessToken(jwtService.generateJWTAccessToken(user));
                signInUserResponseDTO.setUserData(dtoConverter.convertUserToUserDTO(user));

                return signInUserResponseDTO;
            } else {
                List<VerificationTokens> verificationTokenses = user.getVerificationTokensList();

                boolean isNeedResend = verificationTokenses.isEmpty()
                        || verificationTokenses
                                .get(0)
                                .getCreatedTimestamp()
                                .before(new Timestamp(System.currentTimeMillis() - (resendAfter)));

                if (isNeedResend) {
                    VerificationTokens verificationTokens = createVerificationToken(user);
                    emailService.sendVerificationEmail(verificationTokens);
                    verificationTokenRepository.save(verificationTokens);
                }

                throw new UserNotVerifiedException(isNeedResend);
            }
        }
        return signInUserResponseDTO;
    }

    @Override
    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationTokens> opToken = verificationTokenRepository.findByToken(token);
        if (opToken.isPresent()) {
            VerificationTokens verificationToken = opToken.get();
            Users user = verificationToken.getUsers();
            if (!user.getEmailVerified()) {
                user.setEmailVerified(true);
                userRepository.save(user);
                verificationTokenRepository.deleteByUsers(user);

                return true;
            }
        }
        return false;
    }

    @Override
    public void forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO)
            throws UserNotFoundException, EmailNotAssosiatedWithUserException, EmailFailureException {
        Optional<Users> opUser = userRepository.findByUsername(forgotPasswordRequestDTO.getUsername());
        if (!opUser.isPresent()) {
            throw new UserNotFoundException();
        }

        Users user = opUser.get();
        if (!user.getEmail().equals(forgotPasswordRequestDTO.getEmail())) {
            throw new EmailNotAssosiatedWithUserException();
        }

        String token = jwtService.generateJWTPasswordResetToken(user);
        emailService.sendPasswordResetEmail(forgotPasswordRequestDTO, token);
    }

    @Override
    public void resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO)
            throws UserNotFoundException, EmailNotAssosiatedWithUserException, Exception {
        String token = resetPasswordRequestDTO.getToken();

        Long userId = jwtService.getUserId(token);
        String email = jwtService.getPasswordResetEmail(token);

        Optional<Users> opUser = this.findUserByUserId(userId);

        Users user = opUser.get();
        if (!user.getEmail().equals(email)) {
            throw new EmailNotAssosiatedWithUserException();
        }

        user.setPassword(encryptionService.encryptPassword(resetPasswordRequestDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public AuthUserDTO authenticate(String accessToken)
            throws UserNotFoundException, Exception {
        if (accessToken == null) {
            return null;
        }

        accessToken = accessToken.replaceFirst("Bearer ", "");
        Long userId = jwtService.getUserId(accessToken);

        Optional<Users> opUser = this.findUserByUserId(userId);

        Users user = opUser.get();
        Roles role = user.getRoles();

        if (role == null) {
            role = new Roles();
            role.setName(env.getProperty("role.user"));
        }

        AuthUserDTO authUserDTO = dtoConverter.convertUserToAuthUserDTO(user);
        authUserDTO.setRoleName(role.getName());

        return authUserDTO;
    }
}
