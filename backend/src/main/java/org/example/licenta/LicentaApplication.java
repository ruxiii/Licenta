package org.example.licenta;

import org.example.licenta.db.entities.*;
import org.example.licenta.db.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class LicentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicentaApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository,
						  AuthenticationRepository authenticationRepository,
						  UserRepository userRepository,
						  PasswordEncoder passwordEncoder,
						  DepartmentRepository departmentRepository,
						  TeamRepository teamRepository) {
		return args -> {
			if (roleRepository.findByAuthority("ADMIN").isPresent()) {
				return;
			}

			DepartmentEntity departmentEntity = new DepartmentEntity();
			departmentEntity.setDepartmentId("ADM");
			departmentEntity.setDepartmentName("Admin");
			departmentRepository.save(departmentEntity);

			TeamEntity teamEntity = new TeamEntity();
			teamEntity.setTeamId("ADM");
			teamEntity.setTeamName("Admin");
			teamEntity.setDepartmentEntity(departmentEntity);
			teamRepository.save(teamEntity);

			RoleEntity adminRole = roleRepository.save(new RoleEntity("ADMIN"));
			RoleEntity userRole = new RoleEntity("USER");
			roleRepository.save(userRole);

			Set<RoleEntity> roles = new HashSet<>();
			roles.add(adminRole);

			String password = passwordEncoder.encode("Admin1!");

			UserEntity user = new UserEntity();
			user.setUserId("T0");
			user.setUserFirstName("ADMIN");
			user.setUserName("");
			user.setUserEmail("");
			user.setUserPassword(password);
			user.setUserRole("ADMIN");
			user.setTeamEntity(teamEntity);
			userRepository.save(user);

			AuthenticationEntity admin = new AuthenticationEntity();
			admin.setUserId("T0");
			admin.setUserPassword(password);
			admin.setAuthorities(roles);

			authenticationRepository.save(admin);
		};
	}
}
