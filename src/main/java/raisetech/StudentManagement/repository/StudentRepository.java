package raisetech.StudentManagement.repository;


import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.ApplicationStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧(全件)
   */
  List<Student> search();

  /**
   * 受講生の検索を行います。
   * @param studentId 受講生ID
   * @return　受講生
   */
  Student searchStudent(String studentId);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報(全件)
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生のコース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 申込状況の全件検索を行います。
   *
   * @return 申込状況(全件)
   */
  List<ApplicationStatus> searchApplicationStatusList();

  /**
   * コースIDに紐づく申込状況を検索します。
   *
   * @return コースIDに紐づく申込状況
   */
  ApplicationStatus searchApplicationStatus(String courseId);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行います。
   *
   * @param student 受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行います。
   * @param studentCourse 受講生コース情報
   */
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 申込状況を新規登録します。IDに関しては自動採番を行います。
   * @param applicationStatus 申込状況
   */
  void registerApplicationStatus(ApplicationStatus applicationStatus);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);

  /**
   * 申込状況を更新します。
   *
   * @param applicationStatus　申込状況
   */
  void updateApplicationStatus(ApplicationStatus applicationStatus);
}

