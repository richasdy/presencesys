package com.richasdy.presencesys.service;

import java.util.Date;

import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.domain.Account;
import com.richasdy.presencesys.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	AccountRepository accountRepository;
	
	@Autowired
	public AccountServiceImpl(AccountRepository accountRepository){
		this.accountRepository = accountRepository;
	}

	@Override
	public Account save(Account account) {
		account.setCreatedAt(new Date());
		return accountRepository.save(account);
	}
	
	@Override
	public Account update(Account account) {
		account.setUpdatedAt(new Date());
		return accountRepository.save(account);
	}

	@Override
	public Account findOne(int id) {
		return accountRepository.findOne(id);
	}

	@Override
	public Iterable<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public long count() {
		return accountRepository.count();
	}

	@Override
	public void delete(int id) {
		accountRepository.delete(id);
	}
	
	@Override
	public Account deleteSoft(int id) {
		Account account = accountRepository.findOne(id);
		account.setDeletedAt(new Date());
		return accountRepository.save(account);
	}
	
	@Override
	public Account login(int id) {
		Account account = accountRepository.findOne(id);
		account.setLastLogin(new Date());
		return accountRepository.save(account);
	}

}
