package raisetech.StudentManagement.repository;


import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
   * @return 受講生一覧(全研)
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  /**
   * 受講生の検索を行います。
   * @param studentId 受講生ID
   * @return　受講生
   */
  @Select("SELECT * FROM students where studentId = #{studentId}")
  Student searchStudent(String studentId);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報(全件)
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生のコース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE studentId = #{studentId}")
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行います。
   *
   * @param student 受講生
   */
  @Insert("INSERT INTO students (name, kana, nickname, mail, area, age, sex, remark, is_deleted)"
          + " VALUE(#{name}, #{kana}, #{nickname}, #{mail}, #{area}, #{age}, #{sex}, #{remark}, false)")
  //MyBatis用の設定、studentIdについて
  @Options(useGeneratedKeys = true,keyProperty = "studentId")
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行います。
   * @param studentCourse 受講生コース情報
   */
  @Insert("INSERT INTO students_courses (studentId, course_name, starting_date, scheduled_end_date)"
          + " VALUE(#{studentId}, #{courseName}, #{startingDate}, #{scheduledEndDate})")
  @Options(useGeneratedKeys = true,keyProperty = "courseId")
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  @Update("UPDATE students Set name = #{name}, kana = #{kana}, nickname = #{nickname},"
          + " mail = #{mail}, area = #{area}, age = #{age}, sex = #{sex}, remark = #{remark}, is_deleted = #{deleted} WHERE studentId = #{studentId}")
  //MyBatis用の設定、studentIdについて
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
  @Update("UPDATE students_courses set course_name = #{courseName} WHERE courseId = #{courseId} ")
  void updateStudentCourse(StudentCourse studentCourse);

}

