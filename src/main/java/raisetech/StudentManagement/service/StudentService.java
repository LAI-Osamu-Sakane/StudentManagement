package raisetech.StudentManagement.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.ApplicationStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。 受講生の検索や登録、更新などを行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository,StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行うので、受験指定は行いません。
   *
   * @return 受講生詳細一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    List<ApplicationStatus> applicationStatusList = repository.searchApplicationStatusList();
    return converter.convertStudentDetails(studentList, studentCourseList, applicationStatusList);
  }

  /**
   * 受講生詳細検索です。 IDに紐づく受個性情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param studentId 受講生ID
   * @return 受講生詳細
   */
  public StudentDetail searchStudent(String studentId) {
    Student student = repository.searchStudent(studentId);
    List<StudentCourse> studentCourseList = repository.searchStudentCourse(student.getStudentId());
    List<ApplicationStatus> applicationStatusList = new ArrayList<>();
    for(StudentCourse studentCourse : studentCourseList) {
      ApplicationStatus applicationStatus = repository.searchApplicationStatus(studentCourse.getCourseId());
      applicationStatusList.add(applicationStatus);
    }
    return new StudentDetail(student, studentCourseList,applicationStatusList);
  }

  /**
   * コースIDに紐づく申込状況を検索
   *
   * @return 申込状況
   */
  public ApplicationStatus searchApplicationStatus(String courseId) {

    ApplicationStatus applicationStatus = repository.searchApplicationStatus(courseId);

    return applicationStatus;
  }

  /**
   * 受講生詳細の登録を行います。 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値と、コース開始日、コース終了日を設定します。
   *
   * @param studentDetail　受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {

    Student student = studentDetail.getStudent();

    repository.registerStudent(student);
      for (StudentCourse studentCourse : studentDetail.getStudentsCourseList()) {
          initStudentsCourse(studentCourse, student);
          repository.registerStudentCourse(studentCourse);
        for (ApplicationStatus applicationStatus : studentDetail.getApplicationStatusList()) {
          initApplicationStatus(applicationStatus, studentCourse);
          repository.registerApplicationStatus(applicationStatus);
        }
      }

      return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する
   *
   * @param studentCourse 受講生コース
   * @param student 受講生
   */
  void initStudentsCourse(StudentCourse studentCourse, Student student) {

    LocalDateTime now = LocalDateTime.now();

    studentCourse.setStudentId(student.getStudentId());
    studentCourse.setStartingDate(now);
    studentCourse.setScheduledEndDate(now.plusYears(1));
  }

  /**
   * 申込状況を登録する際の初期情報を設定する
   *
   * @param applicationStatus 申込状況
   * @param studentCourse 受講生コース情報
   */
  void initApplicationStatus(ApplicationStatus applicationStatus, StudentCourse studentCourse) {
    LocalDateTime now = LocalDateTime.now();
    applicationStatus.setCourseId(studentCourse.getCourseId());
    applicationStatus.setRegistrationDate(now);
  }
  /**
   * 受講生詳細の更新を行います。 受講生と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
      for (StudentCourse studentCourse : studentDetail.getStudentsCourseList()) {
          repository.updateStudentCourse(studentCourse);
        for (ApplicationStatus applicationStatus : studentDetail.getApplicationStatusList()) {
          repository.updateApplicationStatus(applicationStatus);
        }
      }


  }

}

