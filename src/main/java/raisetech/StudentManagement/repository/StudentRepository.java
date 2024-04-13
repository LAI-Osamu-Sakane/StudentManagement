package raisetech.StudentManagement.repository;


import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ
 *
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

    /**
     * 全件検索
     *
     * @return 全件検索した受講生情報の一覧
     */
    @Select("SELECT * FROM students")
    List<Student> search();

    @Select("SELECT * FROM students where studentId = #{studentId}")
    Student searchStudent(String studentId);

    @Select("SELECT * FROM students_courses")
    List<StudentsCourses> searchStudentsCoursesList();

    @Select("SELECT * FROM students_courses WHERE studentId = #{studentId}")
    List<StudentsCourses> searchStudentCourse(String studentId);

    // INSERT文の書き方注意
    @Insert("INSERT INTO students (name, kana, nickname, mail, area, age, sex, remark, is_deleted)"
            + " VALUE(#{name}, #{kana}, #{nickname}, #{mail}, #{area}, #{age}, #{sex}, #{remark}, false)")
    //MyBatis用の設定、studentIdについて
    @Options(useGeneratedKeys = true,keyProperty = "studentId")
    void registerStudent(Student student);

    @Insert("INSERT INTO students_courses (studentId, course_name, starting_date, scheduled_end_date)"
            + " VALUE(#{studentId}, #{courseName}, #{startingDate}, #{scheduledEndDate})")
    @Options(useGeneratedKeys = true,keyProperty = "courseId")
    void registerStudentCourses(StudentsCourses studentsCourse);

    // INSERT文の書き方注意
    @Update("UPDATE students Set name = #{name}, kana = #{kana}, nickname = #{nickname},"
            + " mail = #{mail}, area = #{area}, age = #{age}, sex = #{sex}, remark = #{remark}, is_deleted = #{deleted} WHERE studentId = #{studentId}")
    //MyBatis用の設定、studentIdについて
    void updateStudent(Student student);

    @Update("UPDATE students_courses set course_name = #{courseName} WHERE courseId = #{courseId} ")
    void updateStudentCourses(StudentsCourses studentsCourse);

}

