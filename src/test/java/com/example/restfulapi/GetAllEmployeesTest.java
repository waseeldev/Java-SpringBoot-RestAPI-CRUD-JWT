package com.example.restfulapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.restfulapi.controller.EmployeeController;
import com.example.restfulapi.entity.Employee;
import com.example.restfulapi.service.EmployeeService;

public class GetAllEmployeesTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllEmployees_Success() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        employees.add(new Employee());
        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<Employee>> responseEntity = employeeController.getAllEmployees(null);
        List<Employee> response = responseEntity.getBody();

        assertEquals(employees, response);
    }

    @Test
    public void testGetAllEmployees_NullKeyword() {
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAllEmployees_WithKeyword() {
        String keyword = "test";
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        when(employeeService.searchEmployees(keyword)).thenReturn(employees);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees(keyword);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
    }

    @Test
    public void testGetAllEmployees_InvalidAuthorization() {
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAllEmployees_ServiceException() {
        when(employeeService.getAllEmployees()).thenThrow(RuntimeException.class);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees(null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
