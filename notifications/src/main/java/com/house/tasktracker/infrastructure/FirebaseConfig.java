package com.house.tasktracker.infrastructure;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    private final String PATH = "C:\\Users\\mihaicra\\house-tasks-tracker-firebase-adminsdk-chgey-4d90db019e.json";

    @PostConstruct
    public void initializeFirebaseCloud() throws IOException {
        FileInputStream refreshToken = new FileInputStream(PATH);

        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(refreshToken))
                                                               .build();

        FirebaseApp.initializeApp(options);
    }
}
