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
			Student sofia2 = new Student(
				"Sofia",
				"Vergara",
				"sofia2@mail.com",
				28
			);

			log.info("ADDING STUDENTS: ");
			repository.saveAll(List.of(sofia, franz, sofia2));

			Long count = repository.count();
			log.info("STUDENTS NUMBER: " + count);

			log.info("SELECT ALL STUDENTS");
			List<Student> studentsList = repository.findAll();
			studentsList.forEach(System.out::println);

			log.info("DELETE STUDENT: Franz");
			repository.deleteById(2L);

			repository.findStudentByEmail("sofia@mail.com")
				.ifPresentOrElse(System.out::println, () -> {
					log.error("STUDENT WITH EMAIL sofia2@mail.com NOT FOUND");
				});

			repository.selectStudentWhereFirstNameAndAgeGreaterOrEqual("Sofia", 28)
				.forEach(System.out::println);
			
			repository.selectStudentWhereFirstNameAndAgeGreaterOrEqualNative("Sofia", 28)
				.forEach(System.out::println);

			log.info("DELETE STUDENT: sofia2");
			System.out.println(repository.deleteStudentById(3L));
		};
	}
}
