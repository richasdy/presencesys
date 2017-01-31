package com.richasdy.presencesys.tap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TapService {
	
	Tap save(Tap entity);

	Tap update(Tap entity);

	Tap findOne(long id);

	long count();

	void delete(long id);

	Tap deleteSoft(long id);

	Page<Tap> searchBy(String searchTerm, Pageable pageable);

	Page<Tap> findAll(Pageable pageable);

}
