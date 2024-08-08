package com.Dolphin.SpringAngular.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.Dolphin.SpringAngular.DAO.AddressDAO;
import com.Dolphin.SpringAngular.DAO.EmployeeDAO;
import com.Dolphin.SpringAngular.Model.Address;
import com.Dolphin.SpringAngular.Model.Employee;
import com.Dolphin.SpringAngular.exception.ResourceNotFoundException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AddressDAO addressDAO;
    
    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        List<Employee> employees = jdbcTemplate.query(sql, new EmployeeRowMapper());
        for (Employee employee : employees) {
            List<Address> addresses = addressDAO.findByEmployeeId(employee.getEmp_Id());
            employee.setAddresses(addresses);
        }
        return employees;
    }
    

    
    @Override
    public Employee findById(Long id) {
        String sql = "SELECT * FROM employees WHERE emp_Id = ?";
        try {
            Employee employee = jdbcTemplate.queryForObject(sql, new Object[]{id}, new EmployeeRowMapper());
            List<Address> addresses = addressDAO.findByEmployeeId(employee.getEmp_Id());
            employee.setAddresses(addresses);
            return employee;
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Employee not exist with id: " + id);
        }
    }
    

    
    @Override
    public Employee save(Employee employee) {
        String sql = "INSERT INTO employees (Emp_Name, Email_id, Designation) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"emp_Id"});
                ps.setString(1, employee.getName());
                ps.setString(2, employee.getEmailId());
                ps.setString(3, employee.getDesignation());
                return ps;
            }
        }, keyHolder);

        employee.setEmp_Id(keyHolder.getKey().longValue());
        return employee;
    }
    
    
    @Override
    public void update(Employee employee) {
        String sql = "UPDATE employees SET Emp_Name = ?, Email_id = ?, Designation = ? WHERE emp_Id = ?";
        jdbcTemplate.update(sql, employee.getName(), employee.getEmailId(), employee.getDesignation(), employee.getEmp_Id());
        addressDAO.deleteByEmployeeId(employee.getEmp_Id());
        for (Address address : employee.getAddresses()) {
            address.setEmployee(employee);
            addressDAO.save(address);
        }
    }
    
    @Override
    public void deleteById(Long id) {
        addressDAO.deleteByEmployeeId(id);
        String sql = "DELETE FROM employees WHERE emp_Id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    private static final class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setEmp_Id(rs.getLong("emp_Id"));
            employee.setName(rs.getString("Emp_Name"));
            employee.setEmailId(rs.getString("Email_id"));
            employee.setDesignation(rs.getString("Designation"));
            return employee;
        }
    }

}
