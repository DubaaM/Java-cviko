package sk.ukf.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.ukf.demo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
