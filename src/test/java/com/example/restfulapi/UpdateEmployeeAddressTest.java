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
import com.example.restfulapi.entity.EmployeeAddress;
import com.example.restfulapi.exception.ResourceNotFoundException;
import com.example.restfulapi.service.EmployeeService;

public class UpdateEmployeeAddressTest {
    
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateEmployeeAddress_Success() {
        Long employeeId = 1L;
        Long addressId = 1L;
        EmployeeAddress addressDetails = new EmployeeAddress();
        
        EmployeeAddress updatedAddress = new EmployeeAddress();
        when(employeeService.updateEmployeeAddress(employeeId, addressId, addressDetails)).thenReturn(updatedAddress);
        
        ResponseEntity<EmployeeAddress> response = employeeController.updateEmployeeAddress(employeeId, addressId, addressDetails);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAddress, response.getBody());
    }

    @Test
    public void testUpdateEmployeeAddress_NullInput() {
        ResponseEntity<EmployeeAddress> response = employeeController.updateEmployeeAddress(null, null, null);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateEmployeeAddress_AddressNotFound() {
        Long employeeId = 1L;
        Long addressId = 1L;
        EmployeeAddress addressDetails = new EmployeeAddress();
        
        when(employeeService.updateEmployeeAddress(employeeId, addressId, addressDetails)).thenThrow(ResourceNotFoundException.class);
        
        ResponseEntity<EmployeeAddress> response = employeeController.updateEmployeeAddress(employeeId, addressId, addressDetails);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateEmployeeAddress_InvalidAuthorization() {
        ResponseEntity<EmployeeAddress> response = employeeController.updateEmployeeAddress(null, null, null);
        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateEmployeeAddress_ServiceException() {
        Long employeeId = 1L;
        Long addressId = 1L;
        EmployeeAddress addressDetails = new EmployeeAddress();
        
        when(employeeService.updateEmployeeAddress(employeeId, addressId, addressDetails)).thenThrow(RuntimeException.class);
        
        ResponseEntity<EmployeeAddress> response = employeeController.updateEmployeeAddress(employeeId, addressId, addressDetails);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
