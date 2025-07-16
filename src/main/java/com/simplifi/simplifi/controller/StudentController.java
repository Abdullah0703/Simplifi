package com.simplifi.simplifi.controller;

import com.simplifi.simplifi.models.Student;
import com.simplifi.simplifi.models.School;
import com.simplifi.simplifi.dto.StudentDTO;
import com.simplifi.simplifi.models.Classroom;
import com.simplifi.simplifi.repository.StudentRepository;
import com.simplifi.simplifi.repository.SchoolRepository;
import com.simplifi.simplifi.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private ClassroomRepository classroomRepository;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        if (student.getSchool() != null && student.getSchool().getId() != null) {
            School school = schoolRepository.findById(student.getSchool().getId()).orElseThrow();
            student.setSchool(school);
        }
        if (student.getClassroom() != null && student.getClassroom().getId() != null) {
            Classroom classroom = classroomRepository.findById(student.getClassroom().getId()).orElseThrow();
            student.setClassroom(classroom);
        }
        return studentRepository.save(student);
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable Long id) {
        Student student = studentRepository.findById(id).orElseThrow();
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        if (student.getSchool() != null) {
            dto.setSchoolId(student.getSchool().getId());
            dto.setSchoolName(student.getSchool().getName());
        }
        if (student.getClassroom() != null) {
            dto.setClassroomId(student.getClassroom().getId());
            dto.setClassroomName(student.getClassroom().getName());
        }
        return dto;
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Student student = studentRepository.findById(id).orElseThrow();
        student.setName(studentDetails.getName());
        student.setAge(studentDetails.getAge());
        if (studentDetails.getSchool() != null && studentDetails.getSchool().getId() != null) {
            School school = schoolRepository.findById(studentDetails.getSchool().getId()).orElseThrow();
            student.setSchool(school);
        }
        if (studentDetails.getClassroom() != null && studentDetails.getClassroom().getId() != null) {
            Classroom classroom = classroomRepository.findById(studentDetails.getClassroom().getId()).orElseThrow();
            student.setClassroom(classroom);
        }
        return studentRepository.save(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}
