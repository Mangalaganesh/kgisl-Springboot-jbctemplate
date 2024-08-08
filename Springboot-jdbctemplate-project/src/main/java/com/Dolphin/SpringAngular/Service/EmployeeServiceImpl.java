package com.Dolphin.SpringAngular.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dolphin.SpringAngular.DAO.AddressDAO;
import com.Dolphin.SpringAngular.DAO.EmployeeDAO;
import com.Dolphin.SpringAngular.Model.Address;
import com.Dolphin.SpringAngular.Model.Employee;
import com.Dolphin.SpringAngular.exception.ResourceNotFoundException;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
    private EmployeeDAO employeeDAO;
	
	@Autowired
    private AddressDAO addressDAO;
	
	@Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeDAO.findById(id);
    }


    
    @Override
    public Employee createEmployee(Employee employee) {
        Employee savedEmployee = employeeDAO.save(employee);
        for (Address address : employee.getAddresses()) {
            address.setEmployee(savedEmployee);
            addressDAO.save(address);
        }
        return savedEmployee;
    }
    
    @Override
    public void updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employeeDAO.findById(id);
        if (existingEmployee == null) {
            throw new ResourceNotFoundException("Employee not exist with id :" + id);
        }
        employee.setEmp_Id(id);
        employeeDAO.update(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeDAO.deleteById(id);
    }
	
	
	
}
