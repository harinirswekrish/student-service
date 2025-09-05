package com.mini.school.erp.student_service.repository;

import com.mini.school.erp.student_service.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
}

