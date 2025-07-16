package com.simplifi.simplifi.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.simplifi.simplifi.models.Classroom;
import com.simplifi.simplifi.models.School;
import com.simplifi.simplifi.models.Student;
import com.simplifi.simplifi.models.Subject;
import com.simplifi.simplifi.models.Teacher;
import com.simplifi.simplifi.repository.ClassroomRepository;
import com.simplifi.simplifi.repository.SchoolRepository;
import com.simplifi.simplifi.repository.StudentRepository;
import com.simplifi.simplifi.repository.SubjectRepository;
import com.simplifi.simplifi.repository.TeacherRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            SchoolRepository schoolRepo,
            StudentRepository studentRepo,
            TeacherRepository teacherRepo,
            SubjectRepository subjectRepo,
            ClassroomRepository classroomRepo
    ) {
        return args -> {
            // Create Subjects
            Subject math = new Subject();
            math.setName("Math");
            Subject science = new Subject();
            science.setName("Science");

            subjectRepo.saveAll(List.of(math, science));

            // Create a School
            School school = new School();
            school.setName("Greenfield High");
            school.setAddress("123 School Lane");

            schoolRepo.save(school);

            // Create Teachers
            Teacher t1 = new Teacher();
            t1.setName("Mr. Ali");
            t1.setSubject(math);
            t1.setSchool(school);

            Teacher t2 = new Teacher();
            t2.setName("Ms. Sara");
            t2.setSubject(science);
            t2.setSchool(school);

            teacherRepo.saveAll(List.of(t1, t2));

            // Create Students
            Student s1 = new Student();
            s1.setName("Ahmed");
            s1.setAge(15);
            s1.setSchool(school);

            Student s2 = new Student();
            s2.setName("Zara");
            s2.setAge(16);
            s2.setSchool(school);

            studentRepo.saveAll(List.of(s1, s2));

            // Create Classrooms
            Classroom c1 = new Classroom();
            c1.setName("Class A");
            c1.setTeacher(t1);
            c1.setSchool(school);
            c1.setSubject(math); // Set subject for classroom

            Classroom c2 = new Classroom();
            c2.setName("Class B");
            c2.setTeacher(t2);
            c2.setSchool(school);
            c2.setSubject(science); // Set subject for classroom

            classroomRepo.saveAll(List.of(c1, c2));

            // Assign students to classrooms
            s1.setClassroom(c1);
            s2.setClassroom(c2);
            studentRepo.saveAll(List.of(s1, s2));

            System.out.println("✅ Sample data initialized!");
        };
    }
}
