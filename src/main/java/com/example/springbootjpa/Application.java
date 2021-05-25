package com.example.springbootjpa;

import java.time.LocalDateTime;
import java.util.List;

import com.example.springbootjpa.models.Book;
import com.example.springbootjpa.models.Student;
import com.example.springbootjpa.models.StudentIdCard;
import com.example.springbootjpa.repository.StudentIdCardRepository;
import com.example.springbootjpa.repository.StudentRepository;
import com.github.javafaker.Faker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

			System.out.println("ADDING STUDENT");
			Student student = new Student(firstName, lastName, email, faker.number().numberBetween(16, 52));
			System.out.println(student);

			System.out.println("ADDING BOOKS");
			student.addBook(new Book(LocalDateTime.now().minusDays(4), "Clean Code"));
			student.addBook(new Book(LocalDateTime.now(), "Clean Architecture"));
			student.addBook(new Book(LocalDateTime.now().minusYears(1), "Spring Boot Framework"));

			System.out.println("ADDING STUDENT ID CARD");
			StudentIdCard studentIdCard = new StudentIdCard("123456789", student);
			cardRepository.save(studentIdCard);

			repository.findById(1L).ifPresent((s) -> {
				System.out.println("FETCH BOOK LAZY...");
				List<Book> books = student.getBooks();
				books.forEach((book) -> {
					System.out.println(s.getFirstName() + " borrowed " + book.getBookName());
				});
			});

			repository.findById(1L).ifPresent(System.out::println);
			cardRepository.findById(1L).ifPresent(System.out::println);

			// repository.deleteById(1L);
		};
	}

}
