package com.richasdy.presencesys.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	
	User save(User entity);

	User update(User entity);

	User findOne(long id);

	long count();

	void delete(long id);

	User deleteSoft(long id);

	Page<User> searchBy(String searchTerm, Pageable pageable);

	Page<User> findAll(Pageable pageable);
	
	User findByIdCard(long idCard);

}
