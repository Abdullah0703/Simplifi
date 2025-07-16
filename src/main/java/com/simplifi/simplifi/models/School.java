package com.simplifi.simplifi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

@Entity
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private java.util.List<Student> students;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private java.util.List<Teacher> teachers;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private java.util.List<Subject> subjects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public java.util.List<Student> getStudents() {
        return students;
    }

    public void setStudents(java.util.List<Student> students) {
        this.students = students;
    }

    public java.util.List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(java.util.List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public java.util.List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(java.util.List<Subject> subjects) {
        this.subjects = subjects;
    }

}
