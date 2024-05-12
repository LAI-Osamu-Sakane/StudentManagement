package raisetech.StudentManagement.data;


import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  @NotBlank
  @Pattern(regexp = "^\\d*$")
  @Schema(title = "コースID", description = "自動採番が設定されています。")
  private String courseId;

  @NotBlank
  @Pattern(regexp = "^\\d*$")
  @Schema(title = "受講生ID", description = "外部キー")
  private String studentId;

  @NotBlank
  private String courseName;

  @NotBlank
  private LocalDateTime startingDate;

  @NotBlank
  private LocalDateTime scheduledEndDate;
}
