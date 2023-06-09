package com.project;

import java.io.*;
import java.io.IOException;
import java.util.Objects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class SpringBootMysqlImageUploadApplication {

    public static void main(String[] args)  throws IOException{
    	ClassLoader classLoader =SpringBootMysqlImageUploadApplication.class.getClassLoader();
    	
    	File file = new File(Objects.requireNonNull(classLoader.getResource("servicePasswordKey.json")).getFile());
    	FileInputStream serviceAccount = new FileInputStream(file.getAbsoluteFile());

               FirebaseOptions options = new FirebaseOptions.Builder()
                       .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                       .setDatabaseUrl("https://chatapp-e6e15.firebaseio.com")
                       .build();

               if (FirebaseApp.getApps().isEmpty()) {
                   FirebaseApp.initializeApp(options);
               }
           
    	
        SpringApplication.run(SpringBootMysqlImageUploadApplication.class, args);
        System.out.print("Running");
    }

}
