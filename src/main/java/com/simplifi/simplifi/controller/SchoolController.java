package com.simplifi.simplifi.controller;

import com.simplifi.simplifi.dto.SchoolDTO;
import com.simplifi.simplifi.models.School;
import com.simplifi.simplifi.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    @Autowired
    private SchoolRepository schoolRepository;

    @GetMapping
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    @PostMapping
    public School createSchool(@RequestBody School school) {
        return schoolRepository.save(school);
    }

    @GetMapping("/{id}")
    public SchoolDTO getSchoolById(@PathVariable Long id) {
        School school = schoolRepository.findById(id).orElseThrow();
        SchoolDTO dto = new SchoolDTO();
        dto.setId(school.getId());
        dto.setName(school.getName());
        dto.setAddress(school.getAddress());
        return dto;
    }

    @PutMapping("/{id}")
    public School updateSchool(@PathVariable Long id, @RequestBody School schoolDetails) {
        School school = schoolRepository.findById(id).orElseThrow();
        school.setName(schoolDetails.getName());
        school.setAddress(schoolDetails.getAddress());
        return schoolRepository.save(school);
    }

    @DeleteMapping("/{id}")
    public void deleteSchool(@PathVariable Long id) {
        schoolRepository.deleteById(id);
    }
}
