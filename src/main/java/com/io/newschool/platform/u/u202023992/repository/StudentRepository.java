package com.io.newschool.platform.u.u202023992.repository;

import com.io.newschool.platform.u.u202023992.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Boolean existsByDni(Long dni);
    Boolean existsByStreetAddress(String streetAddress);
}
