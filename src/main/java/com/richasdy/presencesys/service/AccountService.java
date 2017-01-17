package com.richasdy.presencesys.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.richasdy.presencesys.domain.Account;

@Service
public interface AccountService {
	
	Account save(Account entity);
	Account update(Account entity);
	Account findOne(int id);
	Iterable<Account> findAll();
	long count();
	void delete(int id);
	Account deleteSoft(int id);
	Account login(int id);
	Iterable<Account> search(String searchTerm);
	Iterable<Account> searchBy(String searchTerm);
	
	Page<Account> findAllPageAndSort(Pageable pageable);
	
}
