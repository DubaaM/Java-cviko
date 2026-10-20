package sk.ukf.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.ukf.demo.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByFirstNameAndLastName(String firstName, String lastName);
    List<Student> findAllByOrderByLastNameAsc();
}
