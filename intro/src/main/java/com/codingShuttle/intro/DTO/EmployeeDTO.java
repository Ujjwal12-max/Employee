package com.codingShuttle.intro.DTO;

import com.codingShuttle.intro.validators.ValidEmail;
import jakarta.validation.constraints.*;

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
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name must contain only letters and spaces")
    private String name;

    //@Email(message = "Please enter Valid email", regexp= "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @ValidEmail
    private String email;

    @Min(value = 16, message = "Minimum age is 16 for employeeDTO" )
    @Max(value = 60, message = "Maximum age is 60 for employeeDTO")
    private Integer age;

    @NotBlank(message = "Contact number cannot be blank" )
    @Pattern(regexp = "^[0-9]{10}+$", message = "Must contain only number and should contain 10 digits")
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
