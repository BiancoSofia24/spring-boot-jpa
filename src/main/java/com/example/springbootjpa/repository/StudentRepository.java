package com.example.springbootjpa.repository;

import com.example.springbootjpa.models.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
}
