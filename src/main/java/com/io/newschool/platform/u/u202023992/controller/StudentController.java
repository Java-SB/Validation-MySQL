package com.io.newschool.platform.u.u202023992.controller;

import com.io.newschool.platform.u.u202023992.exception.ValidationException;
import com.io.newschool.platform.u.u202023992.model.Student;
import com.io.newschool.platform.u.u202023992.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentRepository _studentRepository;
    public StudentController(StudentRepository studentRepository) {
        _studentRepository = studentRepository;
    }

    //URL: http://localhost:8080/api/v1/students
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(_studentRepository.findAll());
    }

    //URL: http://localhost:8080/api/v1/students
    //Method: POST
    @Transactional
    @PostMapping("")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        existsStudent(student);
        validateBirthDate(student);
        validateStudent(student);
        return new ResponseEntity<>(_studentRepository.save(student), HttpStatus.CREATED);
    }

    //Validation Rules for the Student
    private void validateBirthDate(Student student) {
        LocalDate fechaActual = LocalDate.now();
        if(student.getBirthdate() == null || student.getBirthdate().toString().trim().isEmpty()) {
            throw new ValidationException("No permite que se registre un student sin fecha de nacimiento");
        }
        Period edad = Period.between(student.getBirthdate(), fechaActual);

        if(edad.getYears() < 18) {
            throw new ValidationException("No permite que se registre un student que sea menor de edad");
        }
    }
    private void validateStudent(Student student) {
        if(student.getGender() == null || student.getGender().trim().isEmpty()) {
            throw new ValidationException("No permite que se registre un student sin género");
        }
        if (!student.getGender().equalsIgnoreCase("Male") && !student.getGender().equalsIgnoreCase("Female")) {
            throw new ValidationException("No permite que se registre un student que no sea 'Male' o 'Female'");
        }
        if(student.getName() == null || student.getName().trim().isEmpty()) {
            throw new ValidationException("No permite que se registre un student sin nombre");
        }
        if (student.getDni() < 10000000) {
            throw new ValidationException("No permite que se registre un student sin DNI, digite correctamente!!");
        }
        if(student.getStreetAddress() == null || student.getStreetAddress().trim().isEmpty()) {
            throw new ValidationException("No permite que se registre un student sin dirección");
        }
        if(student.getSpecialty() == null || student.getSpecialty().trim().isEmpty()) {
            throw new ValidationException("No permite que se registre un student sin especialidad");
        }
    }
    private void existsStudent(Student student) {
        if(_studentRepository.existsByDni(student.getDni())) {
            throw new ValidationException("No permite que se registre un student con el mismo DNI: " + student.getDni());
        }
        if(_studentRepository.existsByStreetAddress(student.getStreetAddress())) {
            throw new ValidationException("No permite que se registre un student con la misma dirección: " + student.getStreetAddress());
        }
    }
}
