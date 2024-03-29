package com.example.restfulapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.restfulapi.entity.EmployeeAddress;
import com.example.restfulapi.repository.EmployeeAddressRepository;

@Service
public class EmployeeAddressService {
 @Autowired
    private EmployeeAddressRepository employeeAddressRepository;

    

    public List<EmployeeAddress> getAddressesByEmployeeId(Long employeeId) {
        return employeeAddressRepository.findByEmployeeId(employeeId);
    }
}
