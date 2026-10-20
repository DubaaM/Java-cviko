package sk.ukf.demo.dao;

import sk.ukf.demo.entity.Student;

import java.util.List;

public interface StudentDAO {

    List<Student> findAll();
    Student findById(Integer id);
    Student save(Student student);
    void deleteById(Integer id);


}
