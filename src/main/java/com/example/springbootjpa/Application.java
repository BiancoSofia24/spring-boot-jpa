package com.example.springbootjpa;

import java.util.List;

import com.example.springbootjpa.models.Student;
import com.example.springbootjpa.models.StudentIdCard;
import com.example.springbootjpa.repository.StudentIdCardRepository;
import com.example.springbootjpa.repository.StudentRepository;
import com.github.javafaker.Faker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository repository, StudentIdCardRepository cardRepository) {
		return args -> {
			Faker faker = new Faker();
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@mail.com", firstName, lastName);

			Student student = new Student(
				firstName, 
				lastName, 
				email, 
				faker.number().numberBetween(16, 52));
			
				StudentIdCard studentIdCard = new StudentIdCard("123456789", student);
				cardRepository.save(studentIdCard);

				repository.findById(1L).ifPresent(System.out::println);
				cardRepository.findById(1L).ifPresent(System.out::println);

				repository.deleteById(1L);
		};
	}

	private void generateRandomStudents(StudentRepository repository) {
		Faker faker = new Faker();
		for (int i = 0; i < 20; i++) {
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@mail.com", firstName, lastName);

			Student student = new Student(
				firstName, 
				lastName, 
				email, 
				faker.number().numberBetween(16, 52));

			repository.save(student);
		}
	}
}
