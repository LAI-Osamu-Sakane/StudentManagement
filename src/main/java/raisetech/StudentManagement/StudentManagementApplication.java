package raisetech.StudentManagement;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	private String name = "sakane osamu";
	private String age = "52";

	//（課題18）
	private Map<String, String> student;// = new HashMap<>();　//冒頭で初期化する場合


	@GetMapping("/studentInfo")
	public String getStudentInfo() {

		return name + ' ' + age + '歳';
	}

	@PostMapping("/studentInfo")
	public void setStudentInfo(String name, String age) {
		this.name = name;
		this.age = age;
	}

	@PostMapping("/studentName")
	public void updateStudentName(String name) {
		this.name = name;
	}

	@PostMapping("/studentAge")
	public void updateStudentAge(String age) {
		this.age = age;
	}

	//課題18ここから
	@GetMapping("/moreInfo")
	public String getStudent() {

		return "name：" + name + "\n" + "age：" + this.student.get(name);
	}

	@PostMapping("/moreInfo")
	public void setMore(String name, String age) {
//		この中で初期化した場合
		student = new HashMap<>();

		student.put(name, age);
		this.name = name;
		this.age = age;
	}
	//課題18ここまで

}
