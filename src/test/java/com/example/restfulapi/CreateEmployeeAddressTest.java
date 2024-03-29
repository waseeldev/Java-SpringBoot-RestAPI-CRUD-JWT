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
import com.example.restfulapi.entity.EmployeeAddress;
import com.example.restfulapi.exception.ResourceNotFoundException;
import com.example.restfulapi.service.EmployeeService;

public class CreateEmployeeAddressTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateEmployeeAddress_Success() {
        Long employeeId = 1L;
        EmployeeAddress address = new EmployeeAddress();
        when(employeeService.createEmployeeAddress(employeeId, address)).thenReturn(address);

        ResponseEntity<EmployeeAddress> response = employeeController.createEmployeeAddress(employeeId, address);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(address, response.getBody());
    }

    @Test
    public void testCreateEmployeeAddress_NullInput() {
        Long employeeId = 1L;
        EmployeeAddress address = null;

        ResponseEntity<EmployeeAddress> response = employeeController.createEmployeeAddress(employeeId, address);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateEmployeeAddress_InvalidInputData() {
        Long employeeId = 1L;
        EmployeeAddress address = new EmployeeAddress(); 

        ResponseEntity<EmployeeAddress> response = employeeController.createEmployeeAddress(employeeId, address);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateEmployeeAddress_EmployeeNotExist() {
        Long employeeId = 1L;
        EmployeeAddress address = new EmployeeAddress();
        when(employeeService.createEmployeeAddress(employeeId, address)).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<EmployeeAddress> response = employeeController.createEmployeeAddress(employeeId, address);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateEmployeeAddress_InvalidAuthorization() {
        Long employeeId = 1L;
        EmployeeAddress address = new EmployeeAddress();

        ResponseEntity<EmployeeAddress> response = employeeController.createEmployeeAddress(employeeId, address);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateEmployeeAddress_ServiceException() {
        Long employeeId = 1L;
        EmployeeAddress address = new EmployeeAddress();
        when(employeeService.createEmployeeAddress(employeeId, address)).thenThrow(RuntimeException.class);

        ResponseEntity<EmployeeAddress> response = employeeController.createEmployeeAddress(employeeId, address);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
