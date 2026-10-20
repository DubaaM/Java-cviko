package sk.ukf.demo.service;

import java.util.List;
import sk.ukf.demo.entity.Student;

public interface StudentService {
    List<Student> findAll();

    Student findById(Integer id);

    Student save(Student student);

    void deleteById(Integer id);

    List<Student> findByFirstNameAndLastName(String firstName, String lastName);

    List<Student> getSortedStudents();
}