package com.assignment2.question2_student_api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment2.question2_student_api.model.Student;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private List<Student> students;

    public StudentController() {
        students = new ArrayList<>();

        students.add(new Student(1L, "Souvenir", "Hirwa", "souvenir@uni.edu", "Computer Science", 3.6));
        students.add(new Student(2L, "Fabrice", "King", "fabrice@uni.edu", "Business", 3.2));
        students.add(new Student(3L, "Peter", "keza", "peter@uni.edu", "Computer Science", 3.9));
        students.add(new Student(4L, "Maria", "Ishimwe", "maria@uni.edu", "Engineering", 3.4));
        students.add(new Student(5L, "Liam", "Feza", "liam@uni.edu", "Business", 2.9));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {

        return students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(@PathVariable String major) {

        List<Student> result = students.stream()
                .filter(s -> s.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterByGpa(@RequestParam Double gpa) {

        List<Student> result = students.stream()
                .filter(s -> s.getGpa() >= gpa)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long studentId,
            @RequestBody Student updatedStudent) {

        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {

                student.setFirstName(updatedStudent.getFirstName());
                student.setLastName(updatedStudent.getLastName());
                student.setEmail(updatedStudent.getEmail());
                student.setMajor(updatedStudent.getMajor());
                student.setGpa(updatedStudent.getGpa());

                return ResponseEntity.ok(student);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
