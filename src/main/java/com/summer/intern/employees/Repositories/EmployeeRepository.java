package com.summer.intern.employees.Repositories;

import com.summer.intern.employees.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
}
