package com.simplifi.simplifi.controller;

import com.simplifi.simplifi.dto.ClassroomDTO;
import com.simplifi.simplifi.models.Classroom;
import com.simplifi.simplifi.models.School;
import com.simplifi.simplifi.models.Student;
import com.simplifi.simplifi.models.Teacher;
import com.simplifi.simplifi.models.Subject;
import com.simplifi.simplifi.repository.ClassroomRepository;
import com.simplifi.simplifi.repository.SchoolRepository;
import com.simplifi.simplifi.repository.StudentRepository;
import com.simplifi.simplifi.repository.TeacherRepository;
import com.simplifi.simplifi.repository.SubjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private StudentRepository studentRepository;

    private ClassroomDTO mapToDTO(Classroom classroom) {
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

    private Classroom mapToEntity(ClassroomDTO dto) {
        Classroom classroom = new Classroom();
        classroom.setName(dto.getName());

        if (dto.getSchoolId() != null) {
            School school = schoolRepository.findById(dto.getSchoolId())
                    .orElseThrow(() -> new RuntimeException("School not found"));
            classroom.setSchool(school);
        }
        if (dto.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            classroom.setTeacher(teacher);
        }
        if (dto.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
            classroom.setSubject(subject);
        }

        return classroom;
    }

    @GetMapping
    public List<ClassroomDTO> getAllClassrooms() {
        return classroomRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ClassroomDTO> createClassroom(@RequestBody ClassroomDTO dto) {
        Classroom classroom = mapToEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapToDTO(classroomRepository.save(classroom)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDTO> getClassroomById(@PathVariable Long id) {
        Optional<Classroom> classroom = classroomRepository.findById(id);
        return classroom.map(value -> ResponseEntity.ok(mapToDTO(value)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomDTO> updateClassroom(@PathVariable Long id, @RequestBody ClassroomDTO dto) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        classroom.setName(dto.getName());

        if (dto.getSchoolId() != null) {
            School school = schoolRepository.findById(dto.getSchoolId())
                    .orElseThrow(() -> new RuntimeException("School not found"));
            classroom.setSchool(school);
        }
        if (dto.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            classroom.setTeacher(teacher);
        }
        if (dto.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
            classroom.setSubject(subject);
        }

        return ResponseEntity.ok(mapToDTO(classroomRepository.save(classroom)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClassroom(@PathVariable Long id) {
        if (!classroomRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Classroom not found.");
        }

        List<Student> students = studentRepository.findByClassroomId(id);
        if (!students.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Cannot delete classroom. Students are still assigned to it.");
        }

        classroomRepository.deleteById(id);
        return ResponseEntity.ok("Classroom deleted successfully.");
    }
}
