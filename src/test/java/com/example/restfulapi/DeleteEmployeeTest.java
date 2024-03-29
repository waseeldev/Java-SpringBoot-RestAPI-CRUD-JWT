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
import com.example.restfulapi.exception.ResourceNotFoundException;
import com.example.restfulapi.service.EmployeeService;

public class DeleteEmployeeTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteEmployee_Success() {
        Long employeeId = 1L;

        ResponseEntity<String> response = employeeController.deleteEmployee(employeeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("deleted successfully"));
    }

    @Test
    public void testDeleteEmployee_NotFound() {
        Long employeeId = 1L;

        doThrow(ResourceNotFoundException.class).when(employeeService).deleteEmployee(employeeId);

        ResponseEntity<String> response = employeeController.deleteEmployee(employeeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("not found"));
    }

    @Test
    public void testDeleteEmployee_NullInput() {
        ResponseEntity<String> response = employeeController.deleteEmployee(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteEmployee_InvalidAuthorization() {
        ResponseEntity<String> response = employeeController.deleteEmployee(null);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteEmployee_ServiceException() {
        Long employeeId = 1L;

        doThrow(new RuntimeException()).when(employeeService).deleteEmployee(employeeId);

        ResponseEntity<String> response = employeeController.deleteEmployee(employeeId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("An error occurred"));
    }

}
