package com.codingShuttle.intro.services;

import com.codingShuttle.intro.DTO.EmployeeDTO;
import com.codingShuttle.intro.Entitities.EmployeeEntity;
import com.codingShuttle.intro.repo.EmployeeRepo;
import jakarta.validation.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepo employeeRepo, ModelMapper modelMapper) {
        this.employeeRepo = employeeRepo;
        this.modelMapper = modelMapper;
    }
    public EmployeeDTO createNewEmployee(EmployeeDTO addEmployee){
        EmployeeEntity toSave = modelMapper.map(addEmployee, EmployeeEntity.class);
        EmployeeEntity savedEntity =employeeRepo.save(toSave);
        return modelMapper.map(savedEntity, EmployeeDTO.class);
    }

    public Optional<EmployeeDTO> getEmpByIdService(Long id) {
        return employeeRepo.findById(id).map(employeeEnt -> modelMapper.map(employeeEnt, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmp() {
         List <EmployeeEntity> employeeEntities = employeeRepo.findAll();
        return employeeEntities
                .stream()
                .map(employeeEnt -> modelMapper.map(employeeEnt, EmployeeDTO.class))
                .collect(Collectors.toList());
    }
    public Optional<EmployeeDTO> updateEmployeeByIdService(Long id, EmployeeDTO employee) {
        try {
            isExistsByEmployeeId(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        employee.setId(id);
        EmployeeEntity employeeEntity = modelMapper.map(employee, EmployeeEntity.class);
        employeeRepo.save(employeeEntity);
        return Optional.of(employee) ;
    }

    public void isExistsByEmployeeId(Long employeeId) throws Exception {
        boolean exists = employeeRepo.existsById(employeeId);
        if (!exists) throw new Exception("id cannot be blank NotBlank");
        //ResourceNotFoundException("Employee not found with id: "+employeeId);
    }

    public Boolean deleteEmployeeByIdService(Long id) throws Exception {
        try {
            isExistsByEmployeeId(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        employeeRepo.deleteById(id);
     return true;
    }

}