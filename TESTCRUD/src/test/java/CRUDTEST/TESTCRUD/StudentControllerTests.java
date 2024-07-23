package CRUDTEST.TESTCRUD;

import CRUDTEST.TESTCRUD.controller.StudentController;
import CRUDTEST.TESTCRUD.entities.Student;
import CRUDTEST.TESTCRUD.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService studentService;

	@Test
	public void testCreateStudent() throws Exception {
		Student student = new Student();
		student.setName("Daniele");
		student.setLastName("de Oliveira");
		student.setIsWorking(true);

		given(studentService.saveStudent(student)).willReturn(student);

		mockMvc.perform(post("/students")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"Daniele\",\"lastName\":\"de Oliveira\",\"isWorking\":true}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Daniele"))
				.andExpect(jsonPath("$.lastName").value("de Oliveira"))
				.andExpect(jsonPath("$.isWorking").value(true));
	}
}
