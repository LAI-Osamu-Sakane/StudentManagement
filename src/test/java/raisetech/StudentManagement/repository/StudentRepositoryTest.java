package raisetech.StudentManagement.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@MybatisTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @Test
    void 受講生の全件検索が行えること() {

        List<Student> actual = sut.search();

        assertThat(actual.size()).isEqualTo(3);
    }

    @Test
    void 受講生一件の検索が実行でき内容が正しいこと() {
        String studentId = "2";
        Student expected = new Student();
        expected.setStudentId(studentId);
        expected.setName("鈴木 祐輔");
        expected.setKana("スズキ ユウスケ");
        expected.setNickname("ユウスケ");
        expected.setMail("cc.dd@zzz.com");
        expected.setArea("関東");
        expected.setAge(40);
        expected.setSex("男性");
        expected.setRemark("とりあえず");
        expected.setDeleted(false);

        Student actual = sut.searchStudent(studentId);

        Assertions.assertEquals(expected.getStudentId(), actual.getStudentId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getKana(), actual.getKana());
        Assertions.assertEquals(expected.getNickname(), actual.getNickname());
        Assertions.assertEquals(expected.getMail(), actual.getMail());
        Assertions.assertEquals(expected.getArea(), actual.getArea());
        Assertions.assertEquals(expected.getAge(), actual.getAge());
        Assertions.assertEquals(expected.getSex(), actual.getSex());
        Assertions.assertEquals(expected.getRemark(), actual.getRemark());
        Assertions.assertEquals(expected.isDeleted(), actual.isDeleted());

    }

    @Test
    void 受講生コース情報一覧が検索できること() {

        List<StudentCourse> actual = sut.searchStudentCourseList();

        assertThat(actual.size()).isEqualTo(4);


    }

    @Test
    void 受講生IDに紐づく受講生のコース情報を検索でき内容が正しいこと() {
        String studentId = "1";


        List<StudentCourse> actual = sut.searchStudentCourse(studentId);

        assertThat(actual.size()).isEqualTo(2);
        Assertions.assertEquals(studentId, actual.get(0).getStudentId());

    }

    @Test
    void 受講生の登録が行えること() {
        Student student = new Student();
        student.setName("鈴木 祐輔");
        student.setKana("スズキ ユウスケ");
        student.setNickname("ユウスケ");
        student.setMail("mmm.nn@zzz.com");
        student.setArea("関東");
        student.setAge(45);
        student.setSex("男性");
        student.setRemark("");
        student.setDeleted(false);

        sut.registerStudent(student);

        List<Student> actual = sut.search();

        assertThat(actual.size()).isEqualTo(4);
    }

    @Test
    void 受講生コース情報の登録が行えること() {
        String studentId = "2";
        LocalDateTime localDateTime = LocalDateTime.now();
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(studentId);
        studentCourse.setCourseName("PHP ADVANCE");
        studentCourse.setStartingDate(localDateTime);
        studentCourse.setScheduledEndDate(localDateTime.plusYears(1));

        sut.registerStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourse(studentId);

        assertThat(actual.size()).isEqualTo(2);
        Assertions.assertEquals(studentId, actual.get(0).getStudentId());
    }

    @Test
    void 受講生の更新が行えること() {
        String studentId = "2";
        Student expected = new Student();
        expected.setStudentId(studentId);
        expected.setName("鈴木 祐輔");
        expected.setKana("スズキ ユウスケ");
        expected.setNickname("ユウスケ");
        expected.setMail("mmm.nn@zzz.com");
        expected.setArea("関東");
        expected.setAge(30);
        expected.setSex("男性");
        expected.setRemark("なんとなく");
        expected.setDeleted(false);

        sut.updateStudent(expected);

        Student actual = sut.searchStudent(studentId);

        Assertions.assertEquals(expected.getRemark(), actual.getRemark());
    }

    @Test
    void 受講生コース情報の更新が行えること() {
        String studentId = "3";
        String courseName = "Java Script Basic";
        LocalDateTime localDateTime = LocalDateTime.now();
        StudentCourse expected = new StudentCourse();

        expected.setStudentId(studentId);
        expected.setCourseId("4");
        expected.setCourseName(courseName);
        expected.setStartingDate(localDateTime);
        expected.setScheduledEndDate(localDateTime.plusYears(1));

        sut.updateStudentCourse(expected);

        List<StudentCourse> actual = sut.searchStudentCourse(studentId);

        Assertions.assertEquals(expected.getCourseName(), actual.get(0).getCourseName());
        Assertions.assertEquals(expected.getStartingDate().getYear(), actual.get(0).getStartingDate().getYear());
        Assertions.assertEquals(expected.getScheduledEndDate().getYear(), actual.get(0).getScheduledEndDate().getYear());
    }
}