package CRUDTEST.TESTCRUD;

import CRUDTEST.TESTCRUD.StudentRepository;
import CRUDTEST.TESTCRUD.entities.Student;
import CRUDTEST.TESTCRUD.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void testSaveStudent() {
        Student student = new Student();
        student.setName("Daniele");
        student.setLastName("de Oliveira");
        student.setIsWorking(true);

        Student savedStudent = studentService.saveStudent(student);

        assertNotNull(savedStudent);
        assertNotNull(savedStudent.getId());
        assertEquals("Daniele", savedStudent.getName());
        assertEquals("de Oliveira", savedStudent.getLastName());
    }

    @Test
    public void testGetAllStudents() {
        Student student1 = new Student();
        student1.setName("Daniele");
        student1.setLastName("de Oliveira");
        student1.setIsWorking(true);

        Student student2 = new Student();
        student2.setName("Daniela");
        student2.setLastName("de Oliveira");
        student2.setIsWorking(false);

        studentService.saveStudent(student1);
        studentService.saveStudent(student2);

        Iterable<Student> students = studentService.getAllStudents();
        assertNotNull(students);
        assertTrue(((Collection<?>) students).size() == 2);
    }

    @Test
    public void testGetStudentById() {
        Student student = new Student();
        student.setName("Daniele");
        student.setLastName("de Oliveira");
        student.setIsWorking(true);

        Student savedStudent = studentService.saveStudent(student);

        Student foundStudent = studentService.getStudentById(savedStudent.getId());

        assertNotNull(foundStudent);
        assertEquals(savedStudent.getId(), foundStudent.getId());
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student();
        student.setName("Daniele");
        student.setLastName("de Oliveira");
        student.setIsWorking(true);

        Student savedStudent = studentService.saveStudent(student);
        savedStudent.setName("Lorenzo");
        savedStudent.setLastName("de Oliveira");

        Student updatedStudent = studentService.updateStudent(savedStudent.getId(), savedStudent);

        assertNotNull(updatedStudent);
        assertEquals("Lorenzo", updatedStudent.getName());
        assertEquals("de Oliveira", updatedStudent.getLastName());
    }

    @Test
    public void testUpdateIsWorking() {
        Student student = new Student();
        student.setName("Daniele");
        student.setLastName("de Oliveira");
        student.setIsWorking(false);

        Student savedStudent = studentService.saveStudent(student);

        Student updatedStudent = studentService.updateIsWorking(savedStudent.getId(), true);

        assertNotNull(updatedStudent);
        assertTrue(updatedStudent.getIsWorking());
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student();
        student.setName("Daniele");
        student.setLastName("de Oliveira");
        student.setIsWorking(true);

        Student savedStudent = studentService.saveStudent(student);

        studentService.deleteStudent(savedStudent.getId());

        Optional<Student> deletedStudent = studentRepository.findById(savedStudent.getId());
        assertTrue(deletedStudent.isEmpty());
    }
}
