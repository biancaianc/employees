package com.summer.intern.employees.Controllers;

import com.summer.intern.employees.Models.Employee;
import com.summer.intern.employees.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getEmployees")
    public ResponseEntity<?> getEmployees(){
        return new ResponseEntity<>(employeeService.getEmployees(), HttpStatus.OK);
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable String id){
        if(employeeService.exist(id)) {
            return new ResponseEntity<>(employeeService.getEmployee(id), HttpStatus.OK);
        }
        else
        return new ResponseEntity<>("Employee with id "+id+" does not exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
        if(!employeeService.exist(employee.getId())) {
            employeeService.addEmployee(employee);
            return new ResponseEntity<>("Employee "+employee+" was created", HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>("Employee with id "+employee.getId()+" already exists, use /updateEmployee", HttpStatus.METHOD_NOT_ALLOWED);
    }
    @PutMapping("/updateEmployee")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee){
        if(employeeService.exist(employee.getId())) {
            employeeService.updateEmployee(employee);
            return new ResponseEntity<>("Employee "+employee+" was updated", HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>("Employee with id "+employee.getId()+" does not exist, use /addEmployee", HttpStatus.METHOD_NOT_ALLOWED);
    }
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id){
        if(employeeService.exist(id)) {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>("Employee with id "+id+" was deleted.", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Employee with id "+id+" does not exist.", HttpStatus.NOT_FOUND);
    }
}
