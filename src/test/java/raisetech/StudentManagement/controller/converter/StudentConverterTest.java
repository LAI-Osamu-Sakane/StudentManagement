package raisetech.StudentManagement.controller.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class StudentConverterTest {

    private StudentConverter sut;

    @BeforeEach
    void before() {sut = new StudentConverter();}

    @Test
    void 受講生情報と受講生コース情報で受講生詳細のリストを作成できていること() {
        Student student =  new Student();
        student.setStudentId("123452");
        student.setName("鈴木 祐輔");
        student.setKana("スズキ ユウスケ");
        student.setNickname("ユウスケ");
        student.setMail("mmm.nn@zzz.com");
        student.setArea("関東");
        student.setAge(45);
        student.setSex("男性");
        student.setRemark("");
        student.setDeleted(false);

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseId("2");
        studentCourse.setStudentId("123452");
        studentCourse.setCourseName("Java Standard");
        studentCourse.setStartingDate(LocalDateTime.now());
        studentCourse.setScheduledEndDate(LocalDateTime.now().plusYears(1));

        List<Student> studentList = List.of(student);
        List<StudentCourse> studentCourseList = List.of(studentCourse);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        assertThat(actual.get(0).getStudent()).isEqualTo(student);
        assertThat(actual.get(0).getStudentsCourseList()).isEqualTo(studentCourseList);


    }

    @Test
    void 受講生情報と紐づかない受講生コース情報は無視されること() {
        Student student =  new Student();
        student.setStudentId("123452");
        student.setName("鈴木 祐輔");
        student.setKana("スズキ ユウスケ");
        student.setNickname("ユウスケ");
        student.setMail("mmm.nn@zzz.com");
        student.setArea("関東");
        student.setAge(45);
        student.setSex("男性");
        student.setRemark("");
        student.setDeleted(false);

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseId("2");
        studentCourse.setStudentId("123451");
        studentCourse.setCourseName("Java Standard");
        studentCourse.setStartingDate(LocalDateTime.now());
        studentCourse.setScheduledEndDate(LocalDateTime.now().plusYears(1));

        List<Student> studentList = List.of(student);
        List<StudentCourse> studentCourseList = List.of(studentCourse);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        assertThat(actual.get(0).getStudent()).isEqualTo(student);
        assertThat(actual.get(0).getStudentsCourseList()).isEmpty();


    }

}