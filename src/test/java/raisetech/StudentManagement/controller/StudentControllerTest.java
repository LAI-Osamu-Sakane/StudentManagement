package raisetech.StudentManagement.controller;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService service;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 受講生詳細の一覧表示が実行できて空のリストが返ってくること() throws Exception {

//        when(service.searchStudentList()).thenReturn(List.of(new StudentDetail()));

        mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
                .andExpect(status().isOk());
//                .andExpect(content().json("[{\"student\":null,\"studentsCourseList\":null}]"));

        verify(service, times(1)).searchStudentList();
    }

    @Test
    void 受講生詳細の受講生で適切な値を入力した際に入力チェックで異常が発生しないこと() {

        Student student = new Student();
        student.setStudentId("999");
        student.setName("名前");
        student.setKana("なまえ");
        student.setMail("aaa.bb@zzz.com");

        Set< ConstraintViolation<Student>> violation = validator.validate(student);

        assertThat(violation.size()).isEqualTo(0);
    }

    @Test
    void 受講生詳細の受講生でIDに数字以外を用いたときに入力チェックにかかること() {

        Student student = new Student();
        student.setStudentId("テストです。");
        student.setName("名前");
        student.setKana("なまえ");
        student.setMail("aaa.bb@zzz.com");

        Set< ConstraintViolation<Student>> violation = validator.validate(student);

        assertThat(violation.size()).isEqualTo(1);
        assertThat(violation).extracting("message").containsOnly("数値のみ設定してください。");
    }
}