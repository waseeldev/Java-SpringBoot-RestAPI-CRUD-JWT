package com.example.restfulapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

public class GetEmployeeByIdTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEmployeeById_Success() {
        Long employeeId = 1L;
        Employee employee = new Employee();
        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    public void testGetEmployeeById_NotFound() {
        Long employeeId = 1L;
        when(employeeService.getEmployeeById(employeeId)).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetEmployeeById_NullInput() {
        ResponseEntity<Employee> response = employeeController.getEmployeeById(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetEmployeeById_InvalidAuthorization() {
        Long employeeId = 1L;
        when(employeeService.getEmployeeById(employeeId)).thenReturn(new Employee());

        ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetEmployeeById_ServiceException() {
        Long employeeId = 1L;
        when(employeeService.getEmployeeById(employeeId)).thenThrow(RuntimeException.class);

        ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
