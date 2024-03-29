package com.example.restfulapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restfulapi.entity.Employee;
import com.example.restfulapi.entity.EmployeeAddress;
import com.example.restfulapi.exception.ResourceNotFoundException;
import com.example.restfulapi.repository.EmployeeAddressRepository;
import com.example.restfulapi.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeAddressRepository employeeAddressRepository;

    public Employee createEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        List<EmployeeAddress> addresses = employee.getAddresses();
        if (addresses != null && !addresses.isEmpty()) {
            for (EmployeeAddress address : addresses) {
                address.setEmployee(savedEmployee);
            }
            employeeAddressRepository.saveAll(addresses);
        }
        return savedEmployee;
    }

    public EmployeeAddress createEmployeeAddress(Long employeeId, EmployeeAddress address) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        address.setEmployee(employee);
        return employeeAddressRepository.save(address);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        existingEmployee.setName(employeeDetails.getName());
        return employeeRepository.save(existingEmployee);
    }

    public EmployeeAddress updateEmployeeAddress(Long employeeId, Long addressId, EmployeeAddress addressDetails) {
        EmployeeAddress existingAddress = employeeAddressRepository.findById(addressId)
        .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));
        existingAddress.setAddress(addressDetails.getAddress());
        return employeeAddressRepository.save(existingAddress);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> searchEmployees(String keyword) {
        // Implement search logic here
        return null;
    }

    public List<EmployeeAddress> getAllAddressesOfEmployee(Long employeeId) {
        return employeeAddressRepository.findByEmployeeId(employeeId);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

}
