package com.simplifi.simplifi.controller;

import com.simplifi.simplifi.models.Teacher;
import com.simplifi.simplifi.models.Subject;
import com.simplifi.simplifi.models.School;
import com.simplifi.simplifi.repository.TeacherRepository;
import com.simplifi.simplifi.repository.SubjectRepository;
import com.simplifi.simplifi.repository.SchoolRepository;
import com.simplifi.simplifi.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        if (teacher.getSubject() != null && teacher.getSubject().getId() != null) {
            Subject subject = subjectRepository.findById(teacher.getSubject().getId()).orElseThrow();
            teacher.setSubject(subject);
        }
        if (teacher.getSchool() != null && teacher.getSchool().getId() != null) {
            School school = schoolRepository.findById(teacher.getSchool().getId()).orElseThrow();
            teacher.setSchool(school);
        }
        return teacherRepository.save(teacher);
    }

    @GetMapping("/{id}")
    public TeacherDTO getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow();
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

    @PutMapping("/{id}")
    public Teacher updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow();
        teacher.setName(teacherDetails.getName());
        if (teacherDetails.getSubject() != null && teacherDetails.getSubject().getId() != null) {
            Subject subject = subjectRepository.findById(teacherDetails.getSubject().getId()).orElseThrow();
            teacher.setSubject(subject);
        }
        if (teacherDetails.getSchool() != null && teacherDetails.getSchool().getId() != null) {
            School school = schoolRepository.findById(teacherDetails.getSchool().getId()).orElseThrow();
            teacher.setSchool(school);
        }
        return teacherRepository.save(teacher);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherRepository.deleteById(id);
    }
}
