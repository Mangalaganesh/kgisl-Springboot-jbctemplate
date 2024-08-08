package com.Dolphin.SpringAngular.DAO;

import java.util.List;

import com.Dolphin.SpringAngular.Model.Address;

public interface AddressDAO {
	
	List<Address> findByEmployeeId(Long employeeId);
    void save(Address address);
    void deleteByEmployeeId(Long employeeId);

}
