/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service.impl;

import com.app.userservice.component.UserConverter;
import com.app.userservice.service.EncryptionService;
import com.app.userservice.service.JWTService;
import com.app.userservice.dao.UserRepository;
import com.app.userservice.dao.VerificationTokenRepository;
import com.app.userservice.dto.LoginUserRequestDTO;
import com.app.userservice.dto.NewUserRequestDTO;
import com.app.userservice.dto.UserDTO;
import com.app.userservice.entity.Roles;
import com.app.userservice.entity.Users;
import com.app.userservice.entity.VerificationTokens;
import com.app.userservice.exception.EmailFailureException;
import com.app.userservice.service.UserService;
import com.app.userservice.exception.UserAlreadyExistsException;
import com.app.userservice.exception.UserNotVerifiedException;
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
    private UserConverter userConverter;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JWTService jWTService;

    @Autowired
    private Environment env;

    private VerificationTokens createVerificationToken(Users user) {
        VerificationTokens verificationTokens = new VerificationTokens();
        verificationTokens.setToken(jWTService.generateVerificationJWT(user));
        verificationTokens.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationTokens.setUsers(user);
        user.getVerificationTokensList().add(verificationTokens);

        return verificationTokens;
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        Optional<Users> result = userRepository.findByUsername(username);

        return result;
    }

    @Override
    public UserDTO signUp(NewUserRequestDTO newUserRequestDTO) throws UserAlreadyExistsException, EmailFailureException {
        Optional<Users> existingUser = findByUsername(newUserRequestDTO.getUsername());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        Users user = new Users();
        user.setEmail(newUserRequestDTO.getEmail());
        user.setUsername(newUserRequestDTO.getUsername());
        user.setFirstName(newUserRequestDTO.getFirstName());
        user.setLastName(newUserRequestDTO.getLastName());
        user.setPassword(encryptionService.encryptPassword(newUserRequestDTO.getPassword()));

        user = userRepository.save(user);

        VerificationTokens verificationTokens = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationTokens);
        verificationTokenRepository.save(verificationTokens);

        return userConverter.convertUsertoUserDTO(user);
    }

    @Override
    public String signIn(LoginUserRequestDTO loginUserRequestDTO) throws UserNotVerifiedException, EmailFailureException {
        Optional<Users> existingUser = findByUsername(loginUserRequestDTO.getUsername());
        if (existingUser.isPresent()) {
            Users user = existingUser.get();
            if (encryptionService.verifyPassword(loginUserRequestDTO.getPassword(), user.getPassword())) {
                if (user.getEmailVerified()) {
                    Roles role = user.getRoles();
                    if (role == null) {
                        role = new Roles();
                        role.setName(env.getProperty("role.user"));
                    }

                    return jWTService.generateJWT(user, role);
                } else {
                    List<VerificationTokens> verificationTokenses = user.getVerificationTokensList();
                    boolean isNeedResend = verificationTokenses.size() == 0
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
        }
        return null;
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
}
