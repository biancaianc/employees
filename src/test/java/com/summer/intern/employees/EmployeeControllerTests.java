package com.summer.intern.employees;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.summer.intern.employees.Models.Employee;
import com.summer.intern.employees.Repositories.EmployeeRepository;
import com.summer.intern.employees.Services.EmployeeService;
import com.summer.intern.employees.util.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = EmployeesApplication.class)
@AutoConfigureMockMvc
public class EmployeeControllerTests {
    @MockBean
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeService employeeService;


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getEmployees() throws Exception {
        List<Employee> providedList= Arrays.asList(new Employee("aaa","ion",22), new Employee("bbb","ana",24));
        List<Employee> expectedList= Arrays.asList(new Employee("aaa","ion",22), new Employee("bbb","ana",24));
        when(employeeRepository.findAll()).thenReturn(providedList);
        mockMvc.perform(MockMvcRequestBuilders.get("/getEmployees"))
                .andExpect(status().isOk())
                .andExpect(content().string(StringUtils.read("src/test/resources/getEmployeesResponse.json")));
        Mockito.verify(employeeRepository,Mockito.times(1)).findAll();
    }

   @Test
    public void getEmployee_happy() throws Exception {
        Employee providedEmployee= new Employee("aaa","ana",22);
        when(employeeRepository.existsById(providedEmployee.getId())).thenReturn(true);
        when(employeeRepository.findById(providedEmployee.getId())).thenReturn(Optional.of(providedEmployee));
        mockMvc.perform(MockMvcRequestBuilders.get("/getEmployee/{id}",providedEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(StringUtils.read("src/test/resources/getEmployeeResponse.json")));
       Mockito.verify(employeeRepository,Mockito.times(1)).existsById(providedEmployee.getId());
       Mockito.verify(employeeRepository,Mockito.times(1)).findById(providedEmployee.getId());
    }
    @Test

    public void getEmployee_sad() throws Exception {
        Employee providedEmployee= new Employee("aaa","ana",22);
        when(employeeRepository.existsById(providedEmployee.getId())).thenReturn(false);
        when(employeeRepository.findById(providedEmployee.getId())).thenReturn(Optional.of(providedEmployee));
        mockMvc.perform(MockMvcRequestBuilders.get("/getEmployee/{id}",providedEmployee.getId()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee with id "+providedEmployee.getId()+" does not exist"));
        Mockito.verify(employeeRepository,Mockito.times(1)).existsById(providedEmployee.getId());
        Mockito.verify(employeeRepository,Mockito.times(0)).findById(providedEmployee.getId());
    }

    @Test
    public void addEmployee_happy() throws Exception {
        Employee providedEmployee= new Employee("aaa","ana",22);
        when(employeeRepository.existsById(providedEmployee.getId())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/addEmployee")
                .content(asJsonString(providedEmployee))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Employee "+providedEmployee+" was created"));
        Mockito.verify(employeeRepository,Mockito.times(1)).existsById(providedEmployee.getId());
        Mockito.verify(employeeRepository,Mockito.times(1)).save(providedEmployee);
    }
    @Test
    public void addEmployee_sad() throws Exception {
        Employee providedEmployee= new Employee("aaa","ana",22);
        when(employeeRepository.existsById(providedEmployee.getId())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/addEmployee")
                .content(asJsonString(providedEmployee))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string("Employee with id "+providedEmployee.getId()+" already exists, use /updateEmployee"));
        Mockito.verify(employeeRepository,Mockito.times(1)).existsById(providedEmployee.getId());
        Mockito.verify(employeeRepository,Mockito.times(0)).save(providedEmployee);
    }
    @Test
    public void updateEmployee_happy() throws Exception {
        Employee providedEmployee= new Employee("aaa","ana",22);
        when(employeeRepository.existsById(providedEmployee.getId())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.put("/updateEmployee")
                .content(asJsonString(providedEmployee))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Employee "+providedEmployee+" was updated"));
        Mockito.verify(employeeRepository,Mockito.times(1)).existsById(providedEmployee.getId());
        Mockito.verify(employeeRepository,Mockito.times(1)).save(providedEmployee);
    }
    @Test
    public void updateEmployee_sad() throws Exception {
        Employee providedEmployee= new Employee("aaa","ana",22);
        when(employeeRepository.existsById(providedEmployee.getId())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.put("/updateEmployee")
                .content(asJsonString(providedEmployee))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string("Employee with id "+providedEmployee.getId()+" does not exist, use /addEmployee"));
        Mockito.verify(employeeRepository,Mockito.times(1)).existsById(providedEmployee.getId());
        Mockito.verify(employeeRepository,Mockito.times(0)).save(providedEmployee);
    }

    @Test
    public void deleteEmployee_happy() throws Exception {
        Employee providedEmployee= new Employee("aaa","ana",22);
        when(employeeRepository.existsById(providedEmployee.getId())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteEmployee/{id}",providedEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee with id "+providedEmployee.getId()+" was deleted."));
        Mockito.verify(employeeRepository,Mockito.times(1)).existsById(providedEmployee.getId());
        Mockito.verify(employeeRepository,Mockito.times(1)).deleteById(providedEmployee.getId());
    }

    @Test
    public void deleteEmployee_sad() throws Exception {
        Employee providedEmployee= new Employee("aaa","ana",22);
        when(employeeRepository.existsById(providedEmployee.getId())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteEmployee/{id}",providedEmployee.getId()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee with id "+providedEmployee.getId()+" does not exist."));
        Mockito.verify(employeeRepository,Mockito.times(1)).existsById(providedEmployee.getId());
        Mockito.verify(employeeRepository,Mockito.times(0)).deleteById(providedEmployee.getId());
    }
}
