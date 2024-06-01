package raisetech.StudentManagement.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.ApplicationStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentConverter converter;

    private StudentService sut;

    @BeforeEach
    void before() {
        sut = new StudentService(repository, converter);
    }

    @Test
    void 受講生詳細一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {

        //Mock化、Stabともいう
        //Mockito

        //事前準備
//        StudentService sut = new StudentService(repository, converter);
//        List<StudentDetail> expected = new ArrayList<>();
        List<Student> studentList = new ArrayList<>();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        List<ApplicationStatus> applicationStatusList = new ArrayList<>();
        when(repository.search()).thenReturn(studentList);
        when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
        when(repository.searchApplicationStatusList()).thenReturn((applicationStatusList));

        //実行
        List<StudentDetail> actual = sut.searchStudentList();

        //検証
//        Assertions.assertEquals(expected, actual);
        verify(repository, times(1)).search();
        verify(repository, times(1)).searchStudentCourseList();
        verify(repository, times(1)).searchApplicationStatusList();
        verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList, applicationStatusList);

        //後処理
        //データをもとに戻すなど。今回は無し
    }

    @Test
    void 受講生詳細検索_リポジトリの処理が適切に呼び出せていること() {

        //事前準備
        String studentId = "999";
        String courseId= "2";
        Student student = new Student();
        student.setStudentId(studentId);
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseId(courseId);
        List<StudentCourse> studentCourseList = new ArrayList<>();
        ApplicationStatus applicationStatus = new ApplicationStatus();
        List<ApplicationStatus> applicationStatusList = new ArrayList<>();
        StudentDetail expected = new StudentDetail(student, new ArrayList<>(), new ArrayList<>());

        when(repository.searchStudent(studentId)).thenReturn(student);
        when(repository.searchStudentCourse(student.getStudentId())).thenReturn(studentCourseList);
//        when(repository.searchApplicationStatus(studentCourse.getCourseId())).thenReturn(applicationStatus);


        //実行
        StudentDetail actual = sut.searchStudent(studentId);

        //検証
        Assertions.assertEquals(expected.getStudent().getStudentId(), actual.getStudent().getStudentId());
        verify(repository, times(1)).searchStudent(studentId);
        verify(repository, times(1)).searchStudentCourse(studentId);
//        verify(repository, times(2)).searchApplicationStatus(courseId);

    }

    @Test
    void 受講生登録_リポジトリの処理が適切に呼び出せていること() {

        //事前準備
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        ApplicationStatus applicationStatus = new ApplicationStatus();
        List<StudentCourse> studentCourseList = List.of(studentCourse);
        List<ApplicationStatus> applicationStatusList = List.of(applicationStatus);
        StudentDetail studentDetail = new StudentDetail(student, studentCourseList, applicationStatusList);


        //実行
        sut.registerStudent(studentDetail);

        //検証
        verify(repository, times(1)).registerStudent(student);
        verify(repository, times(1)).registerStudentCourse(studentCourse);

    }

    @Test
    void 受講生更新_リポジトリの処理が適切に呼び出せていること() {

        //事前準備
        Student student = new Student();
        List<Student> studentList = List.of(student);
        StudentCourse studentCourse = new StudentCourse();
        List<StudentCourse> studentCourseList = List.of(studentCourse);
        ApplicationStatus applicationStatus = new ApplicationStatus();
        List<ApplicationStatus> applicationStatusList = List.of(applicationStatus);
        StudentDetail studentDetail = new StudentDetail(student, studentCourseList, applicationStatusList);


        //実行
        sut.updateStudent(studentDetail);

        //検証
        verify(repository, times(1)).updateStudent(student);
        verify(repository, times(1)).updateStudentCourse(studentCourse);

    }

    @Test
    void 受講生詳細登録_初期化処理が行われること() {

        //事前準備
        String studentId = "999";
        Student student = new Student();
        student.setStudentId(studentId);
        StudentCourse studentCourse = new StudentCourse();

        //実行
        sut.initStudentsCourse(studentCourse, student);

        //検証
        Assertions.assertEquals(studentId, student.getStudentId());
        Assertions.assertEquals(LocalDateTime.now().getHour(), studentCourse.getStartingDate().getHour());
        Assertions.assertEquals(LocalDateTime.now().plusYears(1).getYear(), studentCourse.getScheduledEndDate().getYear());

    }
}