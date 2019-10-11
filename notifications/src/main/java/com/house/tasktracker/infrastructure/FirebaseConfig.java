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

    private final String PATH = "/home/mihaic/Downloads/house-tasks-tracker-firebase-adminsdk-u8ouh-6d2ad5791c.json";

    @PostConstruct
    public void initializeFirebaseCloud() throws IOException {
        FileInputStream refreshToken = new FileInputStream(PATH);

        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(refreshToken))
                                                               .build();

        FirebaseApp.initializeApp(options);
    }
}
