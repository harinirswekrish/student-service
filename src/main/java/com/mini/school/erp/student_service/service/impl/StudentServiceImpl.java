package com.mini.school.erp.student_service.service.impl;

import com.mini.school.erp.student_service.dto.StudentCreateRequest;
import com.mini.school.erp.student_service.dto.StudentResponse;
import com.mini.school.erp.student_service.entity.Student;
import com.mini.school.erp.student_service.exception.ResourceNotFoundException;
import com.mini.school.erp.student_service.repository.StudentRepository;
import com.mini.school.erp.student_service.service.StudentService;
import com.mini.school.erp.student_service.validation.business.BusinessValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final BusinessValidation businessValidation;

    public StudentResponse createStudent(StudentCreateRequest request) {
        businessValidation.validateNewStudent(request.getEmail());

        Student student = Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dob(request.getDob())
                .email(request.getEmail())
                .build();

        Student saved = studentRepository.save(student);

        return StudentResponse.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .email(saved.getEmail())
                .build();
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(s -> new StudentResponse(s.getId(), s.getFirstName(), s.getLastName(), s.getEmail()))
                .collect(Collectors.toList());
    }

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));

        return StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .build();
    }
}