package com.mini.school.erp.student_service.validation.business;

import com.mini.school.erp.student_service.exception.BusinessValidationException;
import com.mini.school.erp.student_service.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessValidation {

    private final StudentRepository studentRepository;
    public void validateNewStudent(String email) {
        if (studentRepository.existsByEmail(email)) {
            throw new BusinessValidationException("A student with this email already exists");
        }
    }
}
