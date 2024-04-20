package raisetech.StudentManagement.data;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {  //ファイル自体課題23で作成

  private String courseId;
  private String studentsId;
  private String courseName;
  private LocalDateTime startingDate;
  private LocalDateTime scheduledEndDate;

}
