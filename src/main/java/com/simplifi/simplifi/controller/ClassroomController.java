package com.simplifi.simplifi.controller;

import com.simplifi.simplifi.dto.ClassroomDTO;
import com.simplifi.simplifi.models.Classroom;
import com.simplifi.simplifi.models.School;
import com.simplifi.simplifi.models.Teacher;
import com.simplifi.simplifi.models.Subject;
import com.simplifi.simplifi.repository.ClassroomRepository;
import com.simplifi.simplifi.repository.SchoolRepository;
import com.simplifi.simplifi.repository.TeacherRepository;
import com.simplifi.simplifi.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping
    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    @PostMapping
    public Classroom createClassroom(@RequestBody Classroom classroom) {
        if (classroom.getSchool() != null && classroom.getSchool().getId() != null) {
            School school = schoolRepository.findById(classroom.getSchool().getId()).orElseThrow();
            classroom.setSchool(school);
        }
        if (classroom.getTeacher() != null && classroom.getTeacher().getId() != null) {
            Teacher teacher = teacherRepository.findById(classroom.getTeacher().getId()).orElseThrow();
            classroom.setTeacher(teacher);
        }
        if (classroom.getSubject() != null && classroom.getSubject().getId() != null) {
            Subject subject = subjectRepository.findById(classroom.getSubject().getId()).orElseThrow();
            classroom.setSubject(subject);
        }
        return classroomRepository.save(classroom);
    }

    @GetMapping("/{id}")
    public ClassroomDTO getClassroomById(@PathVariable Long id) {
        Classroom classroom = classroomRepository.findById(id).orElseThrow();
        ClassroomDTO dto = new ClassroomDTO();
        dto.setId(classroom.getId());
        dto.setName(classroom.getName());
        if (classroom.getSchool() != null) {
            dto.setSchoolId(classroom.getSchool().getId());
            dto.setSchoolName(classroom.getSchool().getName());
        }
        if (classroom.getTeacher() != null) {
            dto.setTeacherId(classroom.getTeacher().getId());
            dto.setTeacherName(classroom.getTeacher().getName());
        }
        if (classroom.getSubject() != null) {
            dto.setSubjectId(classroom.getSubject().getId());
            dto.setSubjectName(classroom.getSubject().getName());
        }
        return dto;
    }

    @PutMapping("/{id}")
    public Classroom updateClassroom(@PathVariable Long id, @RequestBody Classroom classroomDetails) {
        Classroom classroom = classroomRepository.findById(id).orElseThrow();
        classroom.setName(classroomDetails.getName());
        if (classroomDetails.getSchool() != null && classroomDetails.getSchool().getId() != null) {
            School school = schoolRepository.findById(classroomDetails.getSchool().getId()).orElseThrow();
            classroom.setSchool(school);
        }
        if (classroomDetails.getTeacher() != null && classroomDetails.getTeacher().getId() != null) {
            Teacher teacher = teacherRepository.findById(classroomDetails.getTeacher().getId()).orElseThrow();
            classroom.setTeacher(teacher);
        }
        if (classroomDetails.getSubject() != null && classroomDetails.getSubject().getId() != null) {
            Subject subject = subjectRepository.findById(classroomDetails.getSubject().getId()).orElseThrow();
            classroom.setSubject(subject);
        }
        return classroomRepository.save(classroom);
    }

    @DeleteMapping("/{id}")
    public void deleteClassroom(@PathVariable Long id) {
        classroomRepository.deleteById(id);
    }
}
