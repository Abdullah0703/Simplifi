package com.simplifi.simplifi.controller;

import com.simplifi.simplifi.models.Subject;
import com.simplifi.simplifi.models.School;
import com.simplifi.simplifi.repository.SubjectRepository;
import com.simplifi.simplifi.repository.SchoolRepository;
import com.simplifi.simplifi.dto.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @PostMapping
    public Subject createSubject(@RequestBody Subject subject) {
        if (subject.getSchool() != null && subject.getSchool().getId() != null) {
            School school = schoolRepository.findById(subject.getSchool().getId()).orElseThrow();
            subject.setSchool(school);
        }
        return subjectRepository.save(subject);
    }

    @GetMapping("/{id}")
    public SubjectDTO getSubjectById(@PathVariable Long id) {
        Subject subject = subjectRepository.findById(id).orElseThrow();
        SubjectDTO dto = new SubjectDTO();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        if (subject.getSchool() != null) {
            dto.setSchoolId(subject.getSchool().getId());
            dto.setSchoolName(subject.getSchool().getName());
        }
        return dto;
    }

    @PutMapping("/{id}")
    public Subject updateSubject(@PathVariable Long id, @RequestBody Subject subjectDetails) {
        Subject subject = subjectRepository.findById(id).orElseThrow();
        subject.setName(subjectDetails.getName());
        if (subjectDetails.getSchool() != null && subjectDetails.getSchool().getId() != null) {
            School school = schoolRepository.findById(subjectDetails.getSchool().getId()).orElseThrow();
            subject.setSchool(school);
        }
        return subjectRepository.save(subject);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable Long id) {
        subjectRepository.deleteById(id);
    }
}
