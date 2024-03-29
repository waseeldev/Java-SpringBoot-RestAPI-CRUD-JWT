package com.example.restfulapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restfulapi.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
