package com.simplifi.simplifi.controller;

import com.simplifi.simplifi.dto.SubjectDTO;
import com.simplifi.simplifi.models.School;
import com.simplifi.simplifi.models.Subject;
import com.simplifi.simplifi.repository.SchoolRepository;
import com.simplifi.simplifi.repository.SubjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    private SubjectDTO mapToDTO(Subject subject) {
        SubjectDTO dto = new SubjectDTO();
        dto.setId(subject.getId());
        dto.setName(subject.getName());

        if (subject.getSchool() != null) {
            dto.setSchoolId(subject.getSchool().getId());
            dto.setSchoolName(subject.getSchool().getName());
        }

        return dto;
    }

    private Subject mapToEntity(SubjectDTO dto) {
        Subject subject = new Subject();
        subject.setName(dto.getName());

        if (dto.getSchoolId() != null) {
            School school = schoolRepository.findById(dto.getSchoolId())
                    .orElseThrow(() -> new RuntimeException("School not found"));
            subject.setSchool(school);
        }

        return subject;
    }

    @GetMapping
    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id) {
        return subjectRepository.findById(id)
                .map(subject -> ResponseEntity.ok(mapToDTO(subject)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody SubjectDTO dto) {
        Subject subject = mapToEntity(dto);
        Subject saved = subjectRepository.save(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> updateSubject(@PathVariable Long id, @RequestBody SubjectDTO dto) {
        Subject existing = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        existing.setName(dto.getName());

        if (dto.getSchoolId() != null) {
            School school = schoolRepository.findById(dto.getSchoolId())
                    .orElseThrow(() -> new RuntimeException("School not found"));
            existing.setSchool(school);
        }

        return ResponseEntity.ok(mapToDTO(subjectRepository.save(existing)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long id) {
        if (!subjectRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Subject with ID " + id + " not found.");
        }

        subjectRepository.deleteById(id);
        return ResponseEntity.ok("Subject deleted successfully.");
    }
}
