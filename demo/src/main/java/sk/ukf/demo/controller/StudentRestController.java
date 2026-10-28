package sk.ukf.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sk.ukf.demo.entity.Student;
import sk.ukf.demo.response.ResponseFactory;
import sk.ukf.demo.response.ResponseWrapper;
import sk.ukf.demo.service.StudentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private final StudentService studentService;
    private final ResponseFactory response;

    @Autowired
    public StudentRestController(StudentService studentService, ResponseFactory response) {
        this.studentService = studentService;
        this.response = response;
    }


    @GetMapping("/students")
    public ResponseEntity<ResponseWrapper<List<Student>>> getAllStudents() {
        List<Student> students = studentService.findAll();
        return response.ok("Úspešne načítané", students);
    }


    @GetMapping("/students/{id}")
    public ResponseEntity<ResponseWrapper<Student>> getStudentById(@PathVariable Integer id) {

        Student student = studentService.findById(id);

        if (student == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Student id not found - " + id
            );
        }

        return response.ok("Študent úspešne načítaný", student);
    }

    @PostMapping("/students")
    public ResponseEntity<ResponseWrapper<Student>> createStudent(@Valid @RequestBody Student student) {
        student.setId(null);
        Student savedStudent = studentService.save(student);
        return response.created("Študent úspešne vytvorený", savedStudent);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<ResponseWrapper<Student>> updateStudent(@PathVariable Integer id, @Valid @RequestBody Student student) {

        studentService.findById(id);

        student.setId(id);
        Student updatedStudent = studentService.save(student);
        return response.ok("Študent úspešne aktualizovaný", updatedStudent);
    }


    @DeleteMapping("/students/{id}")
    public ResponseEntity<ResponseWrapper<String>> deleteStudent(@PathVariable Integer id) {

        Student student = studentService.findById(id);

        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student id not found - " + id);
        }

        studentService.deleteById(id);

        return response.ok("Študent úspešne odstránený", "Deleted student id - " + id);
    }




    @GetMapping("/students/search")
    public List<Student> findByFirstNameAndLastName(
            @RequestParam String firstName,
            @RequestParam String lastName) {

        return studentService.findByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/students/sorted")
    public List<Student> getSortedStudents() {
        return studentService.getSortedStudents();
    }

}
