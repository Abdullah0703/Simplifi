package com.simplifi.simplifi.controller;

import com.simplifi.simplifi.models.Student;
import com.simplifi.simplifi.models.School;
import com.simplifi.simplifi.dto.StudentDTO;
import java.util.stream.Collectors;
import com.simplifi.simplifi.models.Classroom;
import com.simplifi.simplifi.repository.StudentRepository;
import com.simplifi.simplifi.repository.SchoolRepository;
import com.simplifi.simplifi.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    private Student mapToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setAge(dto.getAge());

        if (dto.getSchoolId() != null) {
            School school = schoolRepository.findById(dto.getSchoolId())
                    .orElseThrow(() -> new RuntimeException("School not found"));
            student.setSchool(school);
        }

        if (dto.getClassroomId() != null) {
            Classroom classroom = classroomRepository.findById(dto.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));
            student.setClassroom(classroom);
        }

        return student;
    }

    private StudentDTO mapToDTO(Student student) {
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

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(student -> {
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
        }).collect(Collectors.toList());
    }

    @PostMapping
    public StudentDTO createStudent(@RequestBody StudentDTO dto) {
        Student student = mapToEntity(dto);
        Student savedStudent = studentRepository.save(student);
        return mapToDTO(savedStudent);
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
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
    public StudentDTO updateStudent(@PathVariable Long id, @RequestBody StudentDTO dto) {
        Student student = studentRepository.findById(id).orElseThrow();
        student.setName(dto.getName());
        student.setAge(dto.getAge());

        if (dto.getSchoolId() != null) {
            School school = schoolRepository.findById(dto.getSchoolId())
                    .orElseThrow(() -> new RuntimeException("School not found"));
            student.setSchool(school);
        }

        if (dto.getClassroomId() != null) {
            Classroom classroom = classroomRepository.findById(dto.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));
            student.setClassroom(classroom);
        }

        return mapToDTO(studentRepository.save(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student with ID " + id + " not found.");
        }

        studentRepository.deleteById(id);
        return ResponseEntity.ok("Student deleted successfully.");
    }

}
