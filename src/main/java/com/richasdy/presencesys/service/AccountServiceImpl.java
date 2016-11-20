package com.richasdy.presencesys.service;

import java.util.Date;

import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.domain.Account;
import com.richasdy.presencesys.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	AccountRepository repository;

	@Autowired
	public AccountServiceImpl(AccountRepository repository) {
		this.repository = repository;
	}

	@Override
	public Account save(Account entity) {
		entity.setCreatedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Account update(Account entity) {
		entity.setUpdatedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Account findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	public Iterable<Account> findAll() {
		return repository.findAll();
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public void delete(int id) {
		repository.delete(id);
	}

	@Override
	public Account deleteSoft(int id) {
		Account entity = repository.findOne(id);
		entity.setDeletedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Account login(int id) {
		Account entity = repository.findOne(id);
		entity.setLastLogin(new Date());
		return repository.save(entity);
	}

	@Override
	public Iterable<Account> search(String searchTerm) {
		// return repository.findDistinctAccountByNoteOrEmailOrId(searchTerm,
		// searchTerm, searchTerm);
		return repository.findDistinctAccountByIdOrEmailOrPhoneOrUsernameOrNoteOrPermissions(
				Integer.parseInt(searchTerm), searchTerm, searchTerm, searchTerm, searchTerm, searchTerm);
	}

}
