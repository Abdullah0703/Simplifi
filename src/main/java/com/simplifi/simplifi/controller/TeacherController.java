package com.simplifi.simplifi.controller;

import com.simplifi.simplifi.dto.TeacherDTO;
import com.simplifi.simplifi.models.School;
import com.simplifi.simplifi.models.Subject;
import com.simplifi.simplifi.models.Teacher;
import com.simplifi.simplifi.repository.SchoolRepository;
import com.simplifi.simplifi.repository.SubjectRepository;
import com.simplifi.simplifi.repository.TeacherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    private TeacherDTO mapToDTO(Teacher teacher) {
        TeacherDTO dto = new TeacherDTO();
        dto.setId(teacher.getId());
        dto.setName(teacher.getName());

        if (teacher.getSubject() != null) {
            dto.setSubjectId(teacher.getSubject().getId());
            dto.setSubjectName(teacher.getSubject().getName());
        }
        if (teacher.getSchool() != null) {
            dto.setSchoolId(teacher.getSchool().getId());
            dto.setSchoolName(teacher.getSchool().getName());
        }

        return dto;
    }

    @GetMapping
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.map(value -> ResponseEntity.ok(mapToDTO(value)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO dto) {
        Teacher teacher = new Teacher();
        teacher.setName(dto.getName());

        if (dto.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
            teacher.setSubject(subject);
        }

        if (dto.getSchoolId() != null) {
            School school = schoolRepository.findById(dto.getSchoolId())
                    .orElseThrow(() -> new RuntimeException("School not found"));
            teacher.setSchool(school);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(teacherRepository.save(teacher)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id, @RequestBody TeacherDTO dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        teacher.setName(dto.getName());

        if (dto.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
            teacher.setSubject(subject);
        }

        if (dto.getSchoolId() != null) {
            School school = schoolRepository.findById(dto.getSchoolId())
                    .orElseThrow(() -> new RuntimeException("School not found"));
            teacher.setSchool(school);
        }

        return ResponseEntity.ok(mapToDTO(teacherRepository.save(teacher)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        if (!teacherRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Teacher with ID " + id + " not found.");
        }

        teacherRepository.deleteById(id);
        return ResponseEntity.ok("Teacher deleted successfully.");
    }
}
