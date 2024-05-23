package org.example.licenta;

import jdk.jfr.Event;
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
						  TeamRepository teamRepository,
						  MapRepository mapRepository,
						  PlaceRepository placeRepository,
						  EventRepository eventRepository){
		return args -> {
			if (roleRepository.findByAuthority("ADMIN").isPresent()) {
//				TODO: DE AVUT GRIJA CU ASTA CA NU MAI MERGE DACA N AM HARTA IN BD
				if (placeRepository.findAll().size() == 0){
					MapEntity map_6S = mapRepository.findById("6S").orElse(null);
					for (int i = 1; i < 111; i++){
						PlaceEntity place = new PlaceEntity();
						place.setPlaceNameId("D" + i + "-6S");
						place.setMapEntity(map_6S);
						placeRepository.save(place);
					}

                    MapEntity map_6N = mapRepository.findById("6N").orElse(null);
                    for (int i = 1; i < 131; i++){
                        PlaceEntity place = new PlaceEntity();
                        place.setPlaceNameId("D" + i + "-6N");
                        place.setMapEntity(map_6N);
                        placeRepository.save(place);
                    }

                    MapEntity map_5N = mapRepository.findById("5N").orElse(null);
                    for (int i = 1; i < 57; i++){
                        PlaceEntity place = new PlaceEntity();
                        place.setPlaceNameId("D" + i + "-5N");
                        place.setMapEntity(map_5N);
                        placeRepository.save(place);
                    }

					MapEntity map_PS2 = mapRepository.findById("PS2").orElse(null);
					for (int i = 1; i < 101; i++){
						PlaceEntity place = new PlaceEntity();
						place.setPlaceNameId("P" + i + "-PS2");
						place.setMapEntity(map_PS2);
						placeRepository.save(place);
					}
				}
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

			EventEntity eventEntity = new EventEntity();
			eventEntity.setEventName("Daily booking");
			eventRepository.save(eventEntity);

			EventEntity eventEntity1 = new EventEntity();
			eventEntity1.setEventName("Meeting / Training");
			eventRepository.save(eventEntity1);

			EventEntity eventEntity2 = new EventEntity();
			eventEntity2.setEventName("Visit");
			eventRepository.save(eventEntity2);
		};
	}
}
