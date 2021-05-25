package com.example.springbootjpa;

import java.time.LocalDateTime;
import java.util.List;

import com.example.springbootjpa.models.Book;
import com.example.springbootjpa.models.Course;
import com.example.springbootjpa.models.Enrolment;
import com.example.springbootjpa.models.EnrolmentId;
import com.example.springbootjpa.models.Student;
import com.example.springbootjpa.models.StudentIdCard;
import com.example.springbootjpa.repository.StudentIdCardRepository;
import com.example.springbootjpa.repository.StudentRepository;
import com.github.javafaker.Faker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository repository, StudentIdCardRepository cardRepository) {
		return args -> {

            Faker faker = new Faker();

			log.info("Creating student");
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@mail.com", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55));

			log.info("Adding books to the created student");
            student.addBook(new Book(LocalDateTime.now().minusDays(4), "Clean Code"));

            student.addBook(new Book(LocalDateTime.now(), "Clean Architecture"));

            student.addBook(new Book(LocalDateTime.now().minusYears(1), "Spring Data JPA"));

			log.info("Adding an Id Card to the created student");
            StudentIdCard studentIdCard = new StudentIdCard(
                    "123456789",
                    student);
            student.setStudentIdCard(studentIdCard);

			log.info("Enrolling the student to a few courses");
            student.addEnrolment(new Enrolment(
                new EnrolmentId(1L, 1L),
                student,
                new Course("React", "IT"),
                LocalDateTime.now()
            ));

            student.addEnrolment(new Enrolment(
                new EnrolmentId(1L, 2L),
                student,
                new Course("Spring Boot", "IT"),
                LocalDateTime.now().minusDays(18)
            ));

            student.addEnrolment(new Enrolment(
                new EnrolmentId(1L, 2L),
                student,
                new Course("Spring Boot", "IT"),
                LocalDateTime.now().minusDays(18)
            ));

			log.info("Save the student into the db");
            repository.save(student);

            repository.findById(1L)
                .ifPresent(s -> {
                    log.info("Fetch books lazy...");
                    List<Book> books = student.getBooks();
                    books.forEach((book) -> {
                        log.info(s.getFirstName() + " borrowed " + book.getBookName());
                    });
                });

        };
	}

}
