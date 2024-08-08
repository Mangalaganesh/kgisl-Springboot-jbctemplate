package com.Dolphin.SpringAngular.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.Dolphin.SpringAngular.DAO.AddressDAO;
import com.Dolphin.SpringAngular.Model.Address;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class AddressDAOImpl implements AddressDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Address> findByEmployeeId(Long employeeId) {
		String sql = "SELECT * FROM address WHERE emp_Id = ?";
		return jdbcTemplate.query(sql, new Object[] { employeeId }, new AddressRowMapper());
	}

	@Override
	public void save(Address address) {
		String sql = "INSERT INTO address (street, city, state, emp_Id) VALUES (?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				System.out.println("employee id" + address.getEmployee().getEmp_Id());
				PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
				ps.setString(1, address.getStreet());
				ps.setString(2, address.getCity());
				ps.setString(3, address.getState());
				ps.setLong(4, address.getEmployee().getEmp_Id());

				return ps;
			}
		}, keyHolder);

		address.setId(keyHolder.getKey().longValue());
	}

	@Override
	public void deleteByEmployeeId(Long employeeId) {
		String sql = "DELETE FROM address WHERE emp_Id = ?";
		jdbcTemplate.update(sql, employeeId);
	}

	private static final class AddressRowMapper implements RowMapper<Address> {
		@Override
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			Address address = new Address();
			address.setId(rs.getLong("id"));
			address.setStreet(rs.getString("street"));
			address.setCity(rs.getString("city"));
			address.setState(rs.getString("state"));
			return address;
		}
	}

}
