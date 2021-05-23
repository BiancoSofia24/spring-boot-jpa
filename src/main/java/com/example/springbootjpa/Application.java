package com.example.springbootjpa;

import java.util.List;

import com.example.springbootjpa.models.Student;
import com.example.springbootjpa.repository.StudentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository repository) {
		return args -> {
			Student sofia = new Student(
				"Sofia",
				"Bianco",
				"sofia@mail.com",
				28
			);
			Student franz = new Student(
				"Franz",
				"Pups",
				"franz-cat@mail.com",
				1
			);

			log.info("ADDING STUDENTS: ");
			repository.saveAll(List.of(sofia, franz));

			Long count = repository.count();
			log.info("STUDENTS NUMBER: " + count);

			log.info("FIND STUDENT WITH THE id 2");
			repository.findById(2L)
				.ifPresentOrElse(System.out::println, () -> {
					log.error("STUDENT WITH id 2 NOT FOUND");
				});

			log.info("FIND STUDENT WITH THE id 3");
			repository.findById(3L)
				.ifPresentOrElse(System.out::println, () -> {
					log.error("STUDENT WITH id 3 NOT FOUND");
				});

			log.info("SELECT ALL STUDENTS");
			List<Student> studentsList = repository.findAll();
			studentsList.forEach(System.out::println);

			log.info("DELETE STUDENT: Franz");
			repository.deleteById(2L);

			count = repository.count();
			log.info("STUDENTS NUMBER: " + count);	
		};
	}
}
