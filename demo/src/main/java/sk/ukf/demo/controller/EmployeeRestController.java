
        package sk.ukf.demo.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import sk.ukf.demo.entity.Employee;
import sk.ukf.demo.service.EmployeeService;
import sk.ukf.demo.response.ResponseFactory;
import sk.ukf.demo.response.ResponseWrapper;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;
    private final ResponseFactory response;

    public EmployeeRestController(
            EmployeeService employeeService,
            ResponseFactory response
    ) {
        this.employeeService = employeeService;
        this.response = response;
    }

    // GET /api/employees
    @GetMapping("/employees")
    public ResponseEntity<ResponseWrapper<List<Employee>>> getAllEmployees() {
        List<Employee> employees = employeeService.findAll();

        return response.ok("Employees retrieved successfully", employees);
    }

    // GET /api/employees/{id}
    @GetMapping("/employees/{id}")
    public ResponseEntity<ResponseWrapper<Employee>> getEmployeeById(
            @PathVariable Long id
    ) {
        Employee employee = employeeService.findById(id);

        if (employee == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found"
            );
        }

        return response.ok("Employee retrieved successfully", employee);
    }

    // POST /api/employees
    @PostMapping("/employees")
    public ResponseEntity<ResponseWrapper<Employee>> addEmployee(
            @Valid @RequestBody Employee employee
    ) {
        employee.setId(null);

        Employee savedEmployee = employeeService.save(employee);

        return response.created("Employee created successfully", savedEmployee);
    }

    // PUT /api/employees/{id}
    @PutMapping("/employees/{id}")
    public ResponseEntity<ResponseWrapper<Employee>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee
    ) {
        Employee existingEmployee = employeeService.findById(id);

        if (existingEmployee == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found with id: " + id
            );
        }

        employee.setId(id);

        Employee updatedEmployee = employeeService.save(employee);

        return response.ok("Employee updated successfully", updatedEmployee);
    }

    // DELETE /api/employees/{id}
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteEmployee(
            @PathVariable Long id
    ) {
        Employee employee = employeeService.findById(id);

        if (employee == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found with id: " + id
            );
        }

        employeeService.deleteById(id);

        return response.ok("Employee deleted successfully");
    }
}

