package com.mini.school.erp.student_service.service;

import com.mini.school.erp.student_service.dto.StudentCreateRequest;
import com.mini.school.erp.student_service.dto.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentCreateRequest request);

    List<StudentResponse> getAllStudents();

    StudentResponse getStudentById(Long id);
}
