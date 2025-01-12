package com.codingShuttle.intro.controllers;


import com.codingShuttle.intro.Entitities.EmployeeEntity;
import com.codingShuttle.intro.services.EmployeeService;
import jakarta.validation.Valid;
import org.apache.juli.logging.Log;
import org.springframework.data.domain.Sort;
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
 ResponseEntity<String> respond = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/posts/1","", String.class);

    @GetMapping (path = "/")
    public String homePage(){
        return "Welcome to the page for online registration of employees";
    }

    @GetMapping( path = "/{EmpId}")
    public EmployeeDTO getEmployeeDetail (@PathVariable(name = "EmpId") long id){
        return new EmployeeDTO();
    }
    @PostMapping( path = "/CreateEmployee")
    public EmployeeDTO CreateEmployee (/*@RequestParam(required = true, name = "name" ) String name,
                                       @RequestParam(required = true, name = "emailId" ) String email,
                                       @RequestParam(required = true, name = "Contact Number" ) String contact*/
                                       @RequestBody @Valid EmployeeDTO inputEmployee){
        //ModelMapper.map(inputEmployee,EmployeeDTO.class)
        return employeeService.createNewEmployee(inputEmployee);
    }

    @GetMapping(path ="/getEmployeeById/{EmpId}")
    public Optional<EmployeeDTO> getEmpById (@Valid @PathVariable (required = true, name = "EmpId") Long id)  {
        return employeeService.getEmpByIdService(id);
    }

    @GetMapping(path ="/getAllEmployees")
    public Optional<List<EmployeeDTO>> getAllEmp ()  {
        return Optional.ofNullable(employeeService.getAllEmp());
    }

    @PutMapping(path ="/updateEmployeeById/{EmpId}")
    public Optional<EmployeeDTO> updateEmployeeById(@PathVariable(name = "EmpId")Long id, @RequestBody @Valid EmployeeDTO employeeDTO){
      return employeeService.updateEmployeeByIdService(id, employeeDTO);
    }
    @DeleteMapping(path = "employee/deleteEmployeeById/{EmpId}")
    public String deleteEmployeeById(@PathVariable(name = "id") Long id){
        employeeService.deleteEmployeeByIdService(id);
        return"uj";
    }


}
