package raisetech.StudentManagement.data;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {

//  @NotBlank
  @Pattern(regexp = "^\\d*$", message = "数値のみ設定してください。")
  @Schema(title = "受講生ID", description = "自動採番が設定されています。")
  private String studentId;

  @NotBlank
  @Schema(title = "受講生名", description = "フルネーム、苗字と名前の間は半角スペース")
  private String name;

  @NotBlank
  @Schema(title = "受講生名（カナ）", description = "フルネーム、苗字と名前の間は半角スペース")
  private String kana;

  @Schema(title = "ニックネーム", description = "あだ名")
  private String nickname;

  @NotBlank
  @Email
  private String mail;
  private String area;
  private int age;
  private String sex;
  @Schema(title = "備考", description = "備考欄")
  private String remark;
  @Schema(title = "論理削除用", description = "Trueの場合は論理的に削除する")
  private boolean isDeleted;
}

