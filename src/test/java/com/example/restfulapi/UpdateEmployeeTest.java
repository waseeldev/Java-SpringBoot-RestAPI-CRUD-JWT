package com.example.restfulapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

public class UpdateEmployeeTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateEmployee_Success() {
        Long employeeId = 1L;
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(employeeId);
        when(employeeService.updateEmployee(eq(employeeId), any(Employee.class))).thenReturn(updatedEmployee);

        ResponseEntity<Employee> response = employeeController.updateEmployee(employeeId, new Employee());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEmployee, response.getBody());
    }

    @Test
    public void testUpdateEmployee_NullInput() {
        ResponseEntity<Employee> response = employeeController.updateEmployee(1L, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateEmployee_InvalidInput() {
        ResponseEntity<Employee> response = employeeController.updateEmployee(1L, new Employee());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateEmployee_NotFound() {
        Long employeeId = 1L;
        when(employeeService.updateEmployee(eq(employeeId), any(Employee.class))).thenReturn(null);

        ResponseEntity<Employee> response = employeeController.updateEmployee(employeeId, new Employee());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateEmployee_InvalidAuthorization() {
        ResponseEntity<Employee> response = employeeController.updateEmployee(1L, new Employee());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateEmployee_ServiceException() {
        Long employeeId = 1L;
        when(employeeService.updateEmployee(eq(employeeId), any(Employee.class))).thenThrow(RuntimeException.class);

        ResponseEntity<Employee> response = employeeController.updateEmployee(employeeId, new Employee());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
