package com.example.restfulapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restfulapi.entity.Employee;
import com.example.restfulapi.entity.EmployeeAddress;
import com.example.restfulapi.exception.ResourceNotFoundException;
import com.example.restfulapi.service.EmployeeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    
    @Tag(name = "Employee", description = "Endpoint for adding employee data")
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @Tag(name = "Employee Addresses", description = "Endpoint for adding employee addresses")
    @PostMapping("/add/{employeeId}/addresses")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<EmployeeAddress> createEmployeeAddress(@PathVariable Long employeeId,
                                                                 @Valid @RequestBody EmployeeAddress address) {
        EmployeeAddress createdAddress = employeeService.createEmployeeAddress(employeeId, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @Tag(name = "Employees", description = "Endpoint for fetching updating data by employeeID")
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                                    @Valid @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    @Tag(name = "Employee Addresses", description = "Endpoint for updating employee addresses")
    @PutMapping("/update/{employeeId}/addresses/{addressId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<EmployeeAddress> updateEmployeeAddress(@PathVariable Long employeeId,
                                                                  @PathVariable Long addressId,
                                                                  @Valid @RequestBody EmployeeAddress addressDetails) {
        EmployeeAddress updatedAddress = employeeService.updateEmployeeAddress(employeeId, addressId, addressDetails);
        return ResponseEntity.ok(updatedAddress);
    }

    @Tag(name = "Employee", description = "Endpoint for fetching employee data by EmployeeID")
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @Tag(name = "Employee", description = "Endpoint for fetching all employees data")
    @GetMapping("/get/all")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String keyword) {
        List<Employee> employees;
        if (keyword != null) {
            employees = employeeService.searchEmployees(keyword);
        } else {
            employees = employeeService.getAllEmployees();
        }
        return ResponseEntity.ok(employees);
    }

    @Tag(name = "Employee Addresses", description = "Endpoint for fetching employee addresses by EmployeeID")
    @GetMapping("/get/{employeeId}/addresses")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<EmployeeAddress>> getAllAddressesOfEmployee(@PathVariable Long employeeId) {
        List<EmployeeAddress> addresses = employeeService.getAllAddressesOfEmployee(employeeId);
        return ResponseEntity.ok(addresses);
    }

    @Tag(name = "Employees", description = "Endpoint for deleting employee data by EmployeeID")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
    try {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().body("Employee with ID " + id + " has been deleted successfully.");
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with ID: " + id);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the employee with ID: " + id);
    }
}

}
