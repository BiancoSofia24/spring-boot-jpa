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
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55));

            student.addBook(
                    new Book(LocalDateTime.now().minusDays(4), "Clean Code"));

            student.addBook(
                    new Book(LocalDateTime.now(), "Clean Architecture"));

            student.addBook(
                    new Book(LocalDateTime.now().minusYears(1), "Spring Data JPA"));

            StudentIdCard studentIdCard = new StudentIdCard(
                    "123456789",
                    student);

            student.setStudentIdCard(studentIdCard);

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

            repository.save(student);

            repository.findById(1L)
                    .ifPresent(s -> {
                        System.out.println("Fetch books lazy...");
                        List<Book> books = student.getBooks();
                        books.forEach(book -> {
                            System.out.println(
                                    s.getFirstName() + " borrowed " + book.getBookName());
                        });
                    });

        };
	}

}
