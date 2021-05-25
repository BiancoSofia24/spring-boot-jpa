package com.example.springbootjpa.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(
    name = "Course"
)
@Table(
    name = "course"
)
public class Course {
    
    @Id
    @SequenceGenerator(
        name = "course_sequence",
        sequenceName = "course_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "course_sequence"
    )
    @Column(
        name = "id",
        updatable = false
    )
    private Long id;

    @Column(
        name = "name",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String name;

    @Column(
        name = "category",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String category;

    @ManyToMany(
        mappedBy = "courses"
    )
    private List<Student> students = new ArrayList<>();

    public Course() {
    }

    public Course(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    @Override
    public String toString() {
        return "Course{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }

}
