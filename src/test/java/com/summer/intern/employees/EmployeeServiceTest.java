package com.summer.intern.employees;

import com.summer.intern.employees.Models.Employee;
import com.summer.intern.employees.Repositories.EmployeeRepository;
import com.summer.intern.employees.Services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class EmployeeServiceTest {

    private EmployeeRepository employeeRepository=Mockito.mock(EmployeeRepository.class);
    private EmployeeService employeeService=new EmployeeService(employeeRepository);


    @Test
    public void getEmployees(){
        List<Employee> providedList= Arrays.asList(new Employee("aaa","ion",22), new Employee("bbb","ana",24));
        List<Employee> expectedList= Arrays.asList(new Employee("aaa","ion",22), new Employee("bbb","ana",24));
        when(employeeRepository.findAll()).thenReturn(providedList);
        List<Employee> result=employeeService.getEmployees();
        Mockito.verify(employeeRepository,Mockito.times(1)).findAll();
        assertEquals(expectedList,result);
    }

    @Test
    public void exist_happy(){
        Employee providedemployee= new Employee("aaa","aaa",31);
        when(employeeRepository.existsById(providedemployee.getId())).thenReturn(true);
        boolean result=employeeService.exist(providedemployee.getId());
        Mockito.verify(employeeRepository,Mockito.times(1)).existsById(providedemployee.getId());
        assertEquals(true,result);
    }
    @Test
    public void exist_sad(){
        Employee providedemployee= new Employee("aaa","aaa",31);
        when(employeeRepository.existsById(providedemployee.getId())).thenReturn(false);
        boolean result=employeeService.exist(providedemployee.getId());
        Mockito.verify(employeeRepository,Mockito.times(1)).existsById(providedemployee.getId());
        assertEquals(false,result);
    }
    @Test
    public void getEmployee_happy(){
        Employee providedemployee= new Employee("aaa","aaa",31);
        when(employeeRepository.findById(providedemployee.getId())).thenReturn(Optional.of(providedemployee));
        Employee result=employeeService.getEmployee(providedemployee.getId());
        Mockito.verify(employeeRepository,Mockito.times(1)).findById(providedemployee.getId());
        assertEquals(providedemployee,result);
    }
    @Test
    public void addEmployee_happy(){
        Employee providedemployee= new Employee("aaa","aaa",31);
        employeeService.addEmployee(providedemployee);
        Mockito.verify(employeeRepository,Mockito.times(1)).save(providedemployee);
    }

    @Test
    public void updateEmployee_happy(){
        Employee providedemployee= new Employee("aaa","aaa",31);
        employeeService.updateEmployee(providedemployee);
        Mockito.verify(employeeRepository,Mockito.times(1)).save(providedemployee);
    }
    @Test
    public void deleteEmployee_happy(){
        Employee providedemployee= new Employee("aaa","aaa",31);
        employeeService.deleteEmployee(providedemployee.getId());
        Mockito.verify(employeeRepository,Mockito.times(1)).deleteById(providedemployee.getId());
    }

}
