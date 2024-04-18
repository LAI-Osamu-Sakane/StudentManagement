package raisetech.StudentManagement;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student WHERE name = #{name}")
  Student searchByName(String name);

  @Insert("INSERT INTO student values(#{name}, #{age})")
  void registerStudent(String name, int age);

  @Update("UPDATE student set age = #{age} WHERE name = #{name}")
  void updateStudent(String name, int age);

  @Delete("DELETE FROM student WHERE name = #{name}")
  void deleteStudent(String name);
}
