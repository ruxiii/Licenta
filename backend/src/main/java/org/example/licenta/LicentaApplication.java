package org.example.licenta;

import jdk.jfr.Event;
import org.example.licenta.db.entities.*;
import org.example.licenta.db.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
						  EventRepository eventRepository,
						  ReservationRepository reservationRepository){
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

				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

				ReservationEntity reservationEntity = new ReservationEntity();

				reservationEntity.setReservationDate(LocalDate.parse("21-01-2024", dateFormatter));
				reservationEntity.setReservationStartHour(LocalTime.parse("12:00", timeFormatter));
				reservationEntity.setReservationEndHour(LocalTime.parse("12:00", timeFormatter));

				UserEntity user = userRepository.findById("T0").orElse(null);
				EventEntity eventEntity = eventRepository.findById(1L).orElse(null);

				reservationEntity.setUserEntity(user);
				reservationEntity.setPlaceEntity(placeRepository.findById("D1-6S").orElse(null));
				reservationEntity.setEventEntity(eventEntity);
				reservationRepository.save(reservationEntity);

//				JAVA-6S
				PlaceEntity placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("JAVA-6S");
				placeEntity.setMapEntity(mapRepository.findById("6S").orElse(null));
				placeRepository.save(placeEntity);

//				SICILY-6S
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("SICILY-6S");
				placeEntity.setMapEntity(mapRepository.findById("6S").orElse(null));
				placeRepository.save(placeEntity);

//				CYPRUS-6S
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("CYPRUS-6S");
				placeEntity.setMapEntity(mapRepository.findById("6S").orElse(null));
				placeRepository.save(placeEntity);

//				RHODES-6S
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("RHODES-6S");
				placeEntity.setMapEntity(mapRepository.findById("6S").orElse(null));
				placeRepository.save(placeEntity);

//				SEYCHELLES-6S
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("SEYCHELLES-6S");
				placeEntity.setMapEntity(mapRepository.findById("6S").orElse(null));
				placeRepository.save(placeEntity);

//				THASSOS-6S
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("THASSOS-6S");
				placeEntity.setMapEntity(mapRepository.findById("6S").orElse(null));
				placeRepository.save(placeEntity);

//				PALAWAN-6S
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("PALAWAN-6S");
				placeEntity.setMapEntity(mapRepository.findById("6S").orElse(null));
				placeRepository.save(placeEntity);

//				SANTORINI-6N
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("SANTORINI-6N");
				placeEntity.setMapEntity(mapRepository.findById("6N").orElse(null));
				placeRepository.save(placeEntity);

//				SARDINIA-6N
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("SARDINIA-6N");
				placeEntity.setMapEntity(mapRepository.findById("6N").orElse(null));
				placeRepository.save(placeEntity);

//				CRETA-6N
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("CRETA-6N");
				placeEntity.setMapEntity(mapRepository.findById("6N").orElse(null));
				placeRepository.save(placeEntity);

//				COMOROS-6N
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("COMOROS-6N");
				placeEntity.setMapEntity(mapRepository.findById("6N").orElse(null));
				placeRepository.save(placeEntity);

//				MALLORCA-5N
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("MALLORCA-5N");
				placeEntity.setMapEntity(mapRepository.findById("5N").orElse(null));
				placeRepository.save(placeEntity);

//				CAPRI-5N
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("CAPRI-5N");
				placeEntity.setMapEntity(mapRepository.findById("5N").orElse(null));
				placeRepository.save(placeEntity);

//				NAXOS-5N
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("NAXOS-5N");
				placeEntity.setMapEntity(mapRepository.findById("5N").orElse(null));
				placeRepository.save(placeEntity);

//				CORSICA-5N
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("CORSICA-5N");
				placeEntity.setMapEntity(mapRepository.findById("5N").orElse(null));
				placeRepository.save(placeEntity);

//				CURACAO-5N
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("CURACAO-5N");
				placeEntity.setMapEntity(mapRepository.findById("5N").orElse(null));
				placeRepository.save(placeEntity);

//				PAROS-5N
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("PAROS-5N");
				placeEntity.setMapEntity(mapRepository.findById("5N").orElse(null));
				placeRepository.save(placeEntity);

//				GUADELOUPE-5N
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("GUADELOUPE-5N");
				placeEntity.setMapEntity(mapRepository.findById("5N").orElse(null));
				placeRepository.save(placeEntity);

//				GALAPAGOS-5N
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("GALAPAGOS-5N");
				placeEntity.setMapEntity(mapRepository.findById("5N").orElse(null));
				placeRepository.save(placeEntity);

//				SECZONE-PS2
				placeEntity = new PlaceEntity();
				placeEntity.setPlaceNameId("SECZONE2-5N");
				placeEntity.setMapEntity(mapRepository.findById("5N").orElse(null));
				placeRepository.save(placeEntity);
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
