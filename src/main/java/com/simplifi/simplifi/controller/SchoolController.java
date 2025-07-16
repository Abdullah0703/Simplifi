package com.simplifi.simplifi.controller;

import com.simplifi.simplifi.dto.SchoolDTO;
import com.simplifi.simplifi.models.Classroom;
import com.simplifi.simplifi.models.School;
import com.simplifi.simplifi.models.Student;
import com.simplifi.simplifi.repository.ClassroomRepository;
import com.simplifi.simplifi.repository.SchoolRepository;
import com.simplifi.simplifi.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private StudentRepository studentRepository;

    private SchoolDTO mapToDTO(School school) {
        SchoolDTO dto = new SchoolDTO();
        dto.setId(school.getId());
        dto.setName(school.getName());
        dto.setAddress(school.getAddress());
        return dto;
    }

    private School mapToEntity(SchoolDTO dto) {
        School school = new School();
        school.setName(dto.getName());
        school.setAddress(dto.getAddress());
        return school;
    }

    @GetMapping
    public List<SchoolDTO> getAllSchools() {
        return schoolRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<SchoolDTO> createSchool(@RequestBody SchoolDTO dto) {
        School school = mapToEntity(dto);
        School saved = schoolRepository.save(school);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDTO> getSchoolById(@PathVariable Long id) {
        return schoolRepository.findById(id)
                .map(school -> ResponseEntity.ok(mapToDTO(school)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolDTO> updateSchool(@PathVariable Long id, @RequestBody SchoolDTO dto) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));

        school.setName(dto.getName());
        school.setAddress(dto.getAddress());

        return ResponseEntity.ok(mapToDTO(schoolRepository.save(school)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchool(@PathVariable Long id) {
        if (!schoolRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");
        }

        List<Classroom> classrooms = classroomRepository.findBySchoolId(id);
        List<Student> students = studentRepository.findBySchoolId(id);

        if (!classrooms.isEmpty() || !students.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Cannot delete school. There are classrooms or students still assigned to it.");
        }

        schoolRepository.deleteById(id);
        return ResponseEntity.ok("School deleted successfully.");
    }
}
