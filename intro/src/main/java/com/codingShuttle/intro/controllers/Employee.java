package com.codingShuttle.intro.controllers;

import com.codingShuttle.intro.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.codingShuttle.intro.DTO.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employee")
public class Employee {
    private final EmployeeService employeeService;
    public Employee(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    RestTemplate restTemplate = new RestTemplate();
    @GetMapping (path = "/")
    public String homePage(){
        return "Welcome to the page for online registration of employees";
    }

    @PostMapping( path = "/CreateEmployee")
    public ResponseEntity<EmployeeDTO> CreateEmployee (/*@RequestParam(required = true, name = "name" ) String name,
                                       @RequestParam(required = true, name = "emailId" ) String email,
                                       @RequestParam(required = true, name = "Contact Number" ) String contact*/
                                       @RequestBody @Valid EmployeeDTO inputEmployee){
        //ModelMapper.map(inputEmployee,EmployeeDTO.class)
        EmployeeDTO employeeCreated = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<EmployeeDTO>(employeeCreated, HttpStatus.CREATED);
    }

    @GetMapping(path ="/getEmployeeById/{EmpId}")
    public ResponseEntity<EmployeeDTO> getEmpById (@Valid @PathVariable (required = true, name = "EmpId") Long id)  {
        Optional<EmployeeDTO> Employee =  employeeService.getEmpByIdService(id);
        return Employee
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path ="/getAllEmployees")
    public Optional<List<EmployeeDTO>> getAllEmp ()  {
        return Optional.ofNullable(employeeService.getAllEmp());
    }

    @PutMapping(path ="/updateEmployeeById/{EmpId}")
    public Optional<EmployeeDTO> updateEmployeeById(@PathVariable(name = "EmpId")Long id, @RequestBody @Valid EmployeeDTO employeeDTO){
      return employeeService.updateEmployeeByIdService(id, employeeDTO);
    }

    @DeleteMapping(path = "/deleteEmployeeById/{EmpId}")
    public String deleteEmployeeById(@PathVariable(name = "EmpId") Long id){
        try {
            employeeService.deleteEmployeeByIdService(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return"Deleted";
    }


}
