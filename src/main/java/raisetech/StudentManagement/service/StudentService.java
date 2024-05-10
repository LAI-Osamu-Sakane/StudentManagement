package raisetech.StudentManagement.service;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public StudentDetail searchStudent(String studentId) {
    Student student = repository.searchStudent(studentId);
    List<StudentsCourses> studentsCourses = repository.searchStudentCourse(student.getStudentId());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);
    return studentDetail;
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCoursesList();
  }

  //  一連の登録情報がうまくいったときのみ有効とするためのTransactional
  @Transactional
  public void registerStudent(StudentDetail studentDetail) {

    repository.registerStudent(studentDetail.getStudent());
    //todo:コース情報の登録
    for(StudentsCourses studentCourse : studentDetail.getStudentsCourses()) {
      studentCourse.setStudentId(studentDetail.getStudent().getStudentId());
      studentCourse.setStartingDate(LocalDateTime.now());
      studentCourse.setScheduledEndDate(LocalDateTime.now().plusYears(1));

    }
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for(StudentsCourses studentCourse : studentDetail.getStudentsCourses()) {
      studentCourse.setStudentId(studentDetail.getStudent().getStudentId());
      repository.updateStudentCourses(studentCourse);
    }

  }

}
