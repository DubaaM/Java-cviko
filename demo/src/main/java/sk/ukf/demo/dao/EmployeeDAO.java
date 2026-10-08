package sk.ukf.demo.dao;

import sk.ukf.demo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> findAll();

}