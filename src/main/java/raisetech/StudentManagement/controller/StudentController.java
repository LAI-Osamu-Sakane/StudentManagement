package raisetech.StudentManagement.controller;


import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.domain.StudentDetail;
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
    @GetMapping("/student/{studentId}")
    public StudentDetail getStudent(@PathVariable @Size(min = 1, max = 6, message = "ID must have at least 1 and no more than 6 characters") String studentId) {

        return service.searchStudent(studentId);
    }

    /**
     * 受講生詳細の登録を行います。
     *
     * @param studentDetail　受講生詳細
     * @return 実行結果
     */
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {

        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }

    /**
     * 受講生詳細の更新を行います。 キャンセルフラグの更新もここで行います（論理削除）。
     *
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @PutMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {

        service.updateStudent(studentDetail);
        return ResponseEntity.ok("更新処理が成功しました");
    }
}
