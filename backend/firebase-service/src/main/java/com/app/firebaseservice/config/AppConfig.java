/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.firebaseservice.config;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 *
 * @author user
 */
@Configuration
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public FirebaseApp initializeFirebaseApp() throws FileNotFoundException, IOException {
        FileInputStream serviceAccount = new FileInputStream(env.getProperty("firebase.app.account.file"));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return FirebaseApp.initializeApp(options, env.getProperty("firebase.app.name"));
    }
}
