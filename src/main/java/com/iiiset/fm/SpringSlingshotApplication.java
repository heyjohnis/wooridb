package com.iiiset.fm;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.iiiset.fm.config.FileUploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileUploadProperties.class
})
public class SpringSlingshotApplication {
   public static void main(String[] args) {
      SpringApplication.run(SpringSlingshotApplication.class, args);

//		try {
//
//			FileInputStream refreshToken = new FileInputStream("/Users/heujoonkim/workspace/finance-monitor/src/main/resources/firebase-adminsdk.json");
//
//			FirebaseOptions options = new FirebaseOptions.Builder()
//			    .setCredentials(GoogleCredentials.fromStream(refreshToken))
//			    .setDatabaseUrl("https://finance-monitor-5d0ee.firebaseio.com/")
//			    .build();
//
//			FirebaseApp.initializeApp(options);			
//			
//			if (FirebaseApp.getApps().isEmpty()) {
//				FirebaseApp.initializeApp(options);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
   }
}
