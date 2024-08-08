package com.Dolphin.SpringAngular.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;


public class Employee {
	
	
	private long emp_Id;
	private String name;
	private String emailId;
	private String designation;
	
	@JsonManagedReference
	private List<Address> addresses;    
	
	
	public Employee() {
		
	}


	public Employee(String name, String emailId, String designation, List<Address> addresses) {
	
		this.name = name;
		this.emailId = emailId;
		this.designation = designation;
		this.addresses = addresses;
	}


	public long getEmp_Id() {
		return emp_Id;
	}


	public void setEmp_Id(long emp_Id) {
		this.emp_Id = emp_Id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmailId() {
		return emailId;
	}


	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public List<Address> getAddresses() {
		return addresses;
	}


	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
		for (Address address : addresses) {
            address.setEmployee(this);
        }
	}

	
	

	
	
	

}
