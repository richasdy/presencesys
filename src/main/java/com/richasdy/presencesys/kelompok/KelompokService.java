package com.richasdy.presencesys.kelompok;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface KelompokService {
	
	Kelompok save(Kelompok entity);

	Kelompok update(Kelompok entity);

	Kelompok findOne(long id);

	long count();

	void delete(long id);

	Kelompok deleteSoft(long id);

	Page<Kelompok> searchBy(String searchTerm, Pageable pageable);

	Page<Kelompok> findAll(Pageable pageable);

}
