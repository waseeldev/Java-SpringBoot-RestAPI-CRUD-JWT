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

public class GetAllAddressesOfEmployeeTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllAddressesOfEmployee_Success() {
        Long employeeId = 1L;
        List<EmployeeAddress> addresses = new ArrayList<>();
        addresses.add(new EmployeeAddress());
        addresses.add(new EmployeeAddress());
        when(employeeService.getAllAddressesOfEmployee(employeeId)).thenReturn(addresses);

        ResponseEntity<List<EmployeeAddress>> response = employeeController.getAllAddressesOfEmployee(employeeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addresses, response.getBody());
    }

    @Test
    public void testGetAllAddressesOfEmployee_EmployeeNotExist() {
        Long employeeId = 1L;
        when(employeeService.getAllAddressesOfEmployee(employeeId)).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<List<EmployeeAddress>> response = employeeController.getAllAddressesOfEmployee(employeeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllAddressesOfEmployee_NullInput() {
        Long employeeId = null;

        ResponseEntity<List<EmployeeAddress>> response = employeeController.getAllAddressesOfEmployee(employeeId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllAddressesOfEmployee_InvalidAuthorization() {
        Long employeeId = 1L;

        ResponseEntity<List<EmployeeAddress>> response = employeeController.getAllAddressesOfEmployee(employeeId);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllAddressesOfEmployee_ServiceException() {
        Long employeeId = 1L;
        when(employeeService.getAllAddressesOfEmployee(employeeId)).thenThrow(RuntimeException.class);

        ResponseEntity<List<EmployeeAddress>> response = employeeController.getAllAddressesOfEmployee(employeeId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
