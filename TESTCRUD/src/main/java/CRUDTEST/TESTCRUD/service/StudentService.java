package CRUDTEST.TESTCRUD.service;


import CRUDTEST.TESTCRUD.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Iterable<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Integer id, Student student) {
        if (studentRepository.existsById(id)) {
            student.setId(id);
            return studentRepository.save(student);
        }
        return null;
    }

    public Student updateIsWorking(Integer id, Boolean isWorking) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            student.setIsWorking(isWorking);
            return studentRepository.save(student);
        }
        return null;
    }

    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }
}
