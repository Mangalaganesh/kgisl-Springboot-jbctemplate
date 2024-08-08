package com.Dolphin.SpringAngular.DAO;

import java.util.List;

import com.Dolphin.SpringAngular.Model.Employee;

public interface EmployeeDAO {
	
	    List<Employee> findAll();
	    Employee findById(Long id);
	    Employee save(Employee employee);
	    void update(Employee employee);
	    void deleteById(Long id);

}
