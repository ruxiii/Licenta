package org.example.licenta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LicentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicentaApplication.class, args);
	}}
//		ClassLoader classLoader = LicentaApplication.class.getClassLoader();
//
//		URL resourceUrl = classLoader.getResource("config/gestionare-spatii-comune-firebase-adminsdk-7bv6v-fb71d4cd97.json");
//		if (resourceUrl != null) {
//			File resourceFile = new File(resourceUrl.getFile());
//			try {
//				FileInputStream serviceAccount = new FileInputStream(resourceFile);
//
//				FirebaseOptions options = new FirebaseOptions.Builder()
//						.setCredentials(GoogleCredentials.fromStream(serviceAccount))
//						.build();
//
//				if(FirebaseApp.getApps().isEmpty()) {
//					FirebaseApp.initializeApp(options);
//				}
//
//				SpringApplication.run(LicentaApplication.class, args);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else {
//			System.err.println("Resource not found!");
//		}
//
//	}
//}

