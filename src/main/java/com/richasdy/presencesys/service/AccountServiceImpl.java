package com.richasdy.presencesys.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.domain.Account;
import com.richasdy.presencesys.repository.AccountRepository;
import com.richasdy.presencesys.util.Util;

import org.apache.commons.lang3.StringUtils;

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

		if (searchTerm == "") {

			// if null
			return repository.findAll();

		} else if (searchTerm.equals("false") || searchTerm.equals("true")) {

			// if boolean
			return repository.findDistinctAccountByActivated(Boolean.parseBoolean(searchTerm));

		} else if (Util.isValidDate(searchTerm)) {

			// if date
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			Date firstTime = Util.stringToDate(searchTerm);
			Date lastTime = Util.stringToDate(searchTerm);

			System.out.println("@serviceSearchDate : " + firstTime);
			System.out.println("@serviceSearchDate : " + lastTime);

			firstTime.setHours(00);
			firstTime.setMinutes(00);
			firstTime.setSeconds(00);

			lastTime.setHours(23);
			lastTime.setMinutes(59);
			lastTime.setSeconds(59);

			return repository.findDistinctAccountByCreatedAtBetween(firstTime, lastTime);

			// return
			// repository.findDistinctAccountByActivatedAtOrLastLoginOrCreatedAtOrUpdatedAtOrDeletedAt(
			// searchTermDate, searchTermDate, searchTermDate, searchTermDate,
			// searchTermDate);

		} else {

			// if string
			return repository
					.findDistinctAccountByEmailOrPhoneOrUsernameOrNoteOrPermissionsOrActivationCodeOrPersistCodeOrResetPasswordCode(
							searchTerm, searchTerm, searchTerm, searchTerm, searchTerm, searchTerm, searchTerm,
							searchTerm);

		}

		// tidak dipakai karena beririsan dengan phone
		// bisa diconvert jadi integer
		// if numeric
		// else if (StringUtils.isNumeric(searchTerm)) {
		// return
		// repository.findDistinctAccountById(Integer.decode(searchTerm));
		// }
	}

}
