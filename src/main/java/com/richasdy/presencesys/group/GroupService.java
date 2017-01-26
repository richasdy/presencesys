package com.richasdy.presencesys.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface GroupService {
	
	Group save(Group entity);

	Group update(Group entity);

	Group findOne(long id);

	long count();

	void delete(long id);

	Group deleteSoft(long id);

	Page<Group> searchBy(String searchTerm, Pageable pageable);

	Page<Group> findAll(Pageable pageable);

}
