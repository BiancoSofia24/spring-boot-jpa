package com.example.springbootjpa.repository;

import com.example.springbootjpa.models.StudentIdCard;

import org.springframework.data.repository.CrudRepository;

public interface StudentIdCardRepository extends CrudRepository<StudentIdCard, Long> {
    
}
