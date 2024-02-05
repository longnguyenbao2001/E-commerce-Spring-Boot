/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.dao;

import com.app.userservice.entity.Users;
import com.app.userservice.entity.VerificationTokens;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author user
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationTokens, Long> {

    Optional<VerificationTokens> findByToken(String token);

    void deleteByUsers(Users user);
}
