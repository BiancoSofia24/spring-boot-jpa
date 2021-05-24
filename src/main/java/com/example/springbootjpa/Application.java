package com.example.springbootjpa;

import java.util.List;

import com.example.springbootjpa.models.Student;
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
	CommandLineRunner commandLineRunner(StudentRepository repository) {
		return args -> {
			generateRandomStudents(repository);

			PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("firstName").ascending());
			Page<Student> page = repository.findAll(pageRequest);
			System.out.println(page);
		};
	}

	private void sorting(StudentRepository repository) {
		Sort sort = Sort.by("firstName").ascending()
			.and(Sort.by("age").descending());

		repository.findAll(sort)
			.forEach((student) -> {
				System.out.println(student.getFirstName() + " " + student.getAge());
			});
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
