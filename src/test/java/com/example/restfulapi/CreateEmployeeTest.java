package com.example.restfulapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.restfulapi.controller.EmployeeController;
import com.example.restfulapi.entity.Employee;
import com.example.restfulapi.exception.ResourceNotFoundException;
import com.example.restfulapi.service.EmployeeService;

public class CreateEmployeeTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateEmployee_Success() {
        Employee employee = new Employee();
        when(employeeService.createEmployee(employee)).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.createEmployee(employee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    public void testCreateEmployee_NullInput() {
        ResponseEntity<Employee> response = employeeController.createEmployee(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateEmployee_InvalidInput() {
        Employee employee = new Employee();
        ResponseEntity<Employee> response = employeeController.createEmployee(employee);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateEmployee_DuplicateData() {
        Employee employee = new Employee();
        when(employeeService.createEmployee(employee)).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<Employee> response = employeeController.createEmployee(employee);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateEmployee_InvalidAuthorization() {
        Employee employee = new Employee();
        ResponseEntity<Employee> response = employeeController.createEmployee(employee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    public void testCreateEmployee_ServiceException() {
        Employee employee = new Employee();
        when(employeeService.createEmployee(employee)).thenThrow(RuntimeException.class);

        ResponseEntity<Employee> response = employeeController.createEmployee(employee);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
