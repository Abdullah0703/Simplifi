package com.simplifi.simplifi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simplifi.simplifi.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

    List<Student> findByClassroomId(Long id);

    List<Student> findBySchoolId(Long id);  
} 
