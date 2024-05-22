package raisetech.StudentManagement.data;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "申込状況")
@Getter
@Setter
public class ApplicationStatus {

    @Pattern(regexp = "^\\d*$")
    @Schema(title = "申込状況ID", description = "自動採番が設定されています。")
    private String statusId;

    @Pattern(regexp = "^\\d*$")
    @Schema(title = "コースID", description = "外部キー。")
    private String courseId;

    @NotBlank
    private String status;

//    @NotBlank
    private LocalDateTime registrationDate;
}
