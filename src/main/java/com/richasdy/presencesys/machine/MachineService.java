package com.richasdy.presencesys.machine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MachineService {
	
	Machine save(Machine entity);

	Machine update(Machine entity);

	Machine findOne(long id);

	long count();

	void delete(long id);

	Machine deleteSoft(long id);

	Page<Machine> searchBy(String searchTerm, Pageable pageable);

	Page<Machine> findAll(Pageable pageable);

}
