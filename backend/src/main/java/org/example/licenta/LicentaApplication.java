package org.example.licenta;

import org.example.licenta.db.entities.*;
import org.example.licenta.db.repositories.AuthenticationRepository;
import org.example.licenta.db.repositories.RoleRepository;
import org.example.licenta.db.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class LicentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicentaApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, AuthenticationRepository authenticationRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (roleRepository.findByAuthority("ADMIN").isPresent()) {
				return;
			}

			DepartmentEntity departmentEntity = new DepartmentEntity();
			departmentEntity.setDepartmentId("ADM");
			departmentEntity.setDepartmentName("Admin");

			TeamEntity teamEntity = new TeamEntity();
			teamEntity.setTeamId("ADM");
			teamEntity.setDepartmentEntity(departmentEntity);

			RoleEntity adminRole = roleRepository.save(new RoleEntity("ADMIN"));
			RoleEntity userRole = new RoleEntity("USER");
			roleRepository.save(userRole);

			Set<RoleEntity> roles = new HashSet<>();
			roles.add(adminRole);

			UserEntity user = new UserEntity();
			user.setUserId("T0");
			user.setUserFirstName("ADMIN");
			user.setUserName("");
			user.setUserEmail("");
			user.setUserPassword(passwordEncoder.encode("Admin1!"));
			user.setUserRole("ADMIN");
			user.setTeamEntity(teamEntity);
			userRepository.save(user);

			AuthenticationEntity admin = new AuthenticationEntity();
			admin.setUserId("T0");
			admin.setUserPassword(passwordEncoder.encode("Admin1!"));
			admin.setAuthorities(roles);

			authenticationRepository.save(admin);
		};
	}





//public static void main(String[] args) {
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
//	}
}
