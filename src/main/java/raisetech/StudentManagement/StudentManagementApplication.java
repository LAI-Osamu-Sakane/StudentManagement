package raisetech.StudentManagement;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

	@Autowired
	private StudentRepository repository;

	private String name = "sakane osamu";
	private String age = "52";

	//（課題18）
	private Map<String, String> student;// = new HashMap<>();　//冒頭で初期化する場合

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}


//	@GetMapping("/studentInfo")	//20で変更
//	public String getStudentInfo() {	//20で変更
	@GetMapping("/student")
	public String getStudent(@RequestParam String name) {
		Student student = repository.searchByName(name);
		return student.getName() + ' ' + student.getAge() + '歳';
//		return name + ' ' + age + '歳';
	}

//	@PostMapping("/studentInfo")	//20で変更
//	public void setStudentInfo(String name, String age) {	//20で変更
@PostMapping("/student")
public void registerStudent(String name, int age) {
		repository.registerStudent(name, age);
	}

//	@PostMapping("/studentName")	//20で変更
//	public void updateStudentName(String name) {	//20で変更
//		this.name = name;	//20で変更
//	}	//20で変更
	@PatchMapping("/student")
	public void updateStudent(String name, int age) {
		repository.updateStudent(name, age);
	}

	//20で作成
	@DeleteMapping("/student")
	public void deleteStudent(String name) {
		repository.deleteStudent(name);
	}


//	@PostMapping("/studentAge")	//20で変更
//	public void updateStudentAge(String age) {	//20で変更
//		this.age = age;	//20で変更
//	}	//20で変更

	//課題18ここから
//	@GetMapping("/moreInfo")
//	public String getStudent() {
//
//		return "name：" + name + "\n" + "age：" + this.student.get(name);
//	}
//
//	@PostMapping("/moreInfo")
//	public void setMore(String name, String age) {
//		この中で初期化した場合
//		student = new HashMap<>();
//
//		student.put(name, age);
//		this.name = name;
//		this.age = age;
//	}
	//課題18ここまで

}
