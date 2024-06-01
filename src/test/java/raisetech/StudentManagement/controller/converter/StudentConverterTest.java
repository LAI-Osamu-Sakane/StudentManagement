package raisetech.StudentManagement.controller.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.data.ApplicationStatus;
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
    void before() {
        sut = new StudentConverter();
    }

    @Test
    void 受講生情報と受講生コース情報で受講生詳細のリストを作成できていること() {

        LocalDateTime localDateTime = LocalDateTime.now();

        Student student = createStudent();

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseId("98765");
        studentCourse.setStudentId(student.getStudentId());
        studentCourse.setCourseName("Java Standard");
        studentCourse.setStartingDate(localDateTime);
        studentCourse.setScheduledEndDate(localDateTime.plusYears(1));

        ApplicationStatus applicationStatus = new ApplicationStatus();
        applicationStatus.setStatusId("1");
        applicationStatus.setCourseId(studentCourse.getCourseId());
        applicationStatus.setStatus("仮登録");
        applicationStatus.setRegistrationDate(localDateTime);



        List<Student> studentList = List.of(student);
        List<StudentCourse> studentCourseList = List.of(studentCourse);
        List<ApplicationStatus> applicationStatusList = List.of(applicationStatus);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList, applicationStatusList);

        assertThat(actual.get(0).getStudent()).isEqualTo(student);
        assertThat(actual.get(0).getStudentsCourseList()).isEqualTo(studentCourseList);


    }

    @Test
    void 受講生情報と紐づかない受講生コース情報は無視されること() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Student student = createStudent();

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseId("98766");
        studentCourse.setStudentId("12346");
        studentCourse.setCourseName("Python Basic");
        studentCourse.setStartingDate(localDateTime);
        studentCourse.setScheduledEndDate(localDateTime.plusYears(1));

        ApplicationStatus applicationStatus = new ApplicationStatus();
        applicationStatus.setStatusId("3");
        applicationStatus.setCourseId(studentCourse.getCourseId());
        applicationStatus.setStatus("仮登録");
        applicationStatus.setRegistrationDate(localDateTime);

        List<Student> studentList = List.of(student);
        List<StudentCourse> studentCourseList = List.of(studentCourse);
        List<ApplicationStatus> applicationStatusList = List.of(applicationStatus);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList, applicationStatusList);

        assertThat(actual.get(0).getStudent()).isEqualTo(student);
        assertThat(actual.get(0).getStudentsCourseList()).isEmpty();

    }

    private static Student createStudent() {
        Student student =  new Student();
        student.setStudentId("12345");
        student.setName("坂根 治");
        student.setKana("サカネ オサム");
        student.setNickname("オサム");
        student.setMail("aaa.bb@zzz.com");
        student.setArea("関西");
        student.setAge(52);
        student.setSex("男性");
        student.setRemark("とりあえず");
        student.setDeleted(false);
        return student;
    }

}