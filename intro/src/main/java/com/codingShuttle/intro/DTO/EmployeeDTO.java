package com.codingShuttle.intro.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class EmployeeDTO {


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private Long id;

    @NotBlank(message = " name cannot be blank")
    private String name;

    @Email(message = "Please enter Valid email")
    private String email;

    @Min(value = 16, message = "Minimum age is 16 for employeeDTO" )
    @Max(value = 60, message = "Maximum age is 60 for employeeDTO")
    private Integer age;

    @NotBlank(message = "Contact number cannot be blank" )
    private String contactNo;
    private Boolean admin;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getContactNo() {
        return contactNo;
    }

}
