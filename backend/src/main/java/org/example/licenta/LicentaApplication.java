package org.example.licenta;

import org.example.licenta.db.entities.DepartmentEntity;
import org.example.licenta.db.entities.TeamEntity;
import org.example.licenta.db.entities.UserEntity;
import org.example.licenta.db.entities.enums.UserRoles;
import org.example.licenta.db.repositories.DepartmentRepository;
import org.example.licenta.db.repositories.TeamRepository;
import org.example.licenta.db.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.stream.Stream;

@SpringBootApplication
public class LicentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicentaApplication.class, args);
	}
}

