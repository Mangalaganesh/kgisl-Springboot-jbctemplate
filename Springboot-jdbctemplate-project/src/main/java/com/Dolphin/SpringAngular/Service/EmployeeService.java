package com.Dolphin.SpringAngular.Service;

import java.util.List;

import com.Dolphin.SpringAngular.Model.Employee;

public interface EmployeeService {

	    List<Employee> getAllEmployees();
	    Employee getEmployeeById(Long id);
	    Employee createEmployee(Employee employee);
	    void updateEmployee(Long id, Employee employee);
	    void deleteEmployee(Long id);
	
}
