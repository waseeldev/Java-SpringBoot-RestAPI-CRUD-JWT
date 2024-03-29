package com.example.restfulapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restfulapi.entity.EmployeeAddress;
import java.util.List;

public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress, Long> {
    List<EmployeeAddress> findByEmployeeId(Long employeeId);
}
