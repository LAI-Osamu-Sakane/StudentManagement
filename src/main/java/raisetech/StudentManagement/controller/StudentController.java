package raisetech.StudentManagement.controller;


import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.data.ApplicationStatus;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentController {

    private StudentService service;


    /**
     * コンストラクタ
     * @param service　受講生サービス
     */
    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * 受講生詳細一覧検索です。 全件検索を行うので、受験指定は行いません。
     *
     * @return 受講生詳細一覧（全件）
     */
    @Operation(summary = "一覧検索", description = "受講生一覧を検索します。")
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        return service.searchStudentList();
    }

    /**
     * 受講生詳細検索です。 IDに紐づく任意の受講生詳細情報を取得します。
     *
     * @param studentId　受講生ID
     * @return 受講生
     */
    @Operation(summary = "受講生検索", description = "受講生を検索します。")
    @GetMapping("/student/{studentId}")
    public StudentDetail getStudent(@PathVariable @NotBlank @Pattern(regexp = "^\\d*$") String studentId) {

        return service.searchStudent(studentId);
    }

    /**
     * コースIDに紐づく申込状況を取得します。
     * @param courseId コースID
     * @return 申込状況
     */
    @GetMapping("/application/{courseId}")
    public ApplicationStatus getApplicationStatus(@PathVariable @NotBlank @Pattern(regexp = "^\\d*$") String courseId) {

        return service.searchApplicationStatus(courseId);
    }

    /**
     * 受講生詳細の登録を行います。
     *
     * @param studentDetail　受講生詳細
     * @return 実行結果
     */
    @Operation(summary = "受講生登録", description = "受講生を登録します。")
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {

        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }

    /**
     * 受講生詳細の更新を行います。 キャンセルフラグの更新もここで行います（論理削除）。
     *
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @Operation(summary = "受講生詳細更新", description = "受講生詳細を登録します。")
    @PutMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {

        service.updateStudent(studentDetail);
        return ResponseEntity.ok("更新処理が成功しました");
    }

    /**
     * 例外処理確認用
     *
     * @return
     * @throws TestException
     */
    @Operation(summary = "例外処理確認用", description = "例外処理確認をします。")
    @GetMapping("/studentLists")
    public List<StudentDetail> getStudentLists() throws TestException {
        throw new TestException("現在このAPIは使用できません。URLは【studentList】ではなく【students】を利用してください。");
    }

//    @ExceptionHandler(TestException.class)
//    public ResponseEntity<String> handleTestException(TestException ex) {
//        System.out.println(ex.getStackTrace());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//    }
}
