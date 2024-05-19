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
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void 受講生詳細の検索が実行できて空で帰ってくること() throws Exception {
        String studentId  = "999";
        mockMvc.perform(MockMvcRequestBuilders.get("/student/{studentId}", studentId))
                .andExpect(status().isOk());

        verify(service, times(1)).searchStudent(studentId);
    }

    @Test
    //解答をコピーしてもエラー解決できず
    void 受講生詳細の登録が実行できてからで帰ってくること() throws Exception {
        // リクエストデータは適切に構築して入力チェックの検証も兼ねている
        // 本来であれば帰りh登録されたデータが入るが、モック化すると意味がないため、レスポンスは作らない
        mockMvc.perform(post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                {
                    "student": {
                        "name": "鈴木 祐輔",
                        "kana": "鈴木 祐輔",
                        "nickname": "ゆうすけ",
                        "mail": "mmm.nn@zzz.com",
                        "area": "関東",
                        "age": 45,
                        "sex": "男",
                        "remark": "",
                        "deleted": false
                    },
                    "studentsCourseList": [
                        {
                            "studentId": "123452",
                            "courseName": "Java Standard",
                            "startingDate": "2024-05-11T13:52:41",
                            "scheduledEndDate": "2025-05-11T13:52:41"
                        }
                    ]
                }
            """
        ))
                .andExpect(status().isOk());

        verify(service, times(1)).registerStudent(any());
    }

    //解答をコピーしてもエラー解決できず
    @Test
    void 受講生詳細の更新が実行できてからで帰ってくること() throws Exception {
        // リクエストデータは適切に構築して入力チェックの検証も兼ねている
        // 本来であれば帰りh登録されたデータが入るが、モック化すると意味がないため、レスポンスは作らない
        mockMvc.perform(put("/updateStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                {
                    "student": {
                        "name": "鈴木 祐輔",
                        "kana": "鈴木 祐輔",
                        "nickname": "ゆうすけ",
                        "mail": "mmm.nn@zzz.com",
                        "area": "関東",
                        "age": 45,
                        "sex": "男",
                        "remark": "",
                        "deleted": false
                    },
                    "studentsCourseList": [
                        {
                            "studentId": "123452",
                            "courseName": "Java Standard",
                            "startingDate": "2024-05-11T13:52:41",
                            "scheduledEndDate": "2025-05-11T13:52:41"
                        }
                    ]
                }
            """
        ))
                .andExpect(status().isOk());

        verify(service, times(1)).updateStudent(any());
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

    @Test
    void 受講生詳細の例外APIが実行できてステータスが400で帰ってくること() throws Exception {
        mockMvc.perform(get("/studentLists"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("現在このAPIは使用できません。URLは【studentList】ではなく【students】を利用してください。"));
    }
}