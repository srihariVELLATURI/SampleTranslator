package com.se.model.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import com.se.validation.annotation.NullOrNotBlank;

@ApiModel(value = "Registration Request", description = "The registration request payload")
public class RegistrationRequest {

    @NullOrNotBlank(message = "Registration username can be null but not blank")
    @ApiModelProperty(value = "A valid username", allowableValues = "NonEmpty String")
    private String username;
    
    @NullOrNotBlank(message = "Registration firstname can be null but not blank")
    @ApiModelProperty(value = "A valid firstname", allowableValues = "NonEmpty String")
    private String firstname;
    
    @NullOrNotBlank(message = "Registration phone can be null but not blank")
    @ApiModelProperty(value = "A valid phone number", allowableValues = "NonEmpty String")
    private String phone;
    
    @NullOrNotBlank(message = "Registration lastname can be null but not blank")
    @ApiModelProperty(value = "A valid lastname", allowableValues = "NonEmpty String")
    private String lastname;

    @NullOrNotBlank(message = "Registration email can be null but not blank")
    @ApiModelProperty(value = "A valid email", required = true, allowableValues = "NonEmpty String")
    private String email;

    @NotNull(message = "Registration password cannot be null")
    @ApiModelProperty(value = "A valid password string", required = true, allowableValues = "NonEmpty String")
    private String password;
    
    @NotNull(message = "role cannot be null")
    @ApiModelProperty(value = "A valid role", required = true, allowableValues = "NonEmpty String")
    private String role;

//    @NotNull(message = "Specify whether the user has to be registered as an admin or not")
//    @ApiModelProperty(value = "Flag denoting whether the user is an admin or not", required = true,
//            dataType = "boolean", allowableValues = "true, false")
//    private Boolean registerAsAdmin;

	

	public RegistrationRequest() {
    }

    public RegistrationRequest(String username, String firstname, String phone, String lastname, String email,
		@NotNull(message = "Registration password cannot be null") String password,
		@NotNull(message = "role cannot be null") String role) {
	super();
	this.username = username;
	this.firstname = firstname;
	this.phone = phone;
	this.lastname = lastname;
	this.email = email;
	this.password = password;
	this.role = role;
}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Boolean getRegisterAsAdmin() {
//        return registerAsAdmin;
//    }
//
//    public void setRegisterAsAdmin(Boolean registerAsAdmin) {
//        this.registerAsAdmin = registerAsAdmin;
//    }

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
    
	
    
}
