package com.richasdy.presencesys.api;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richasdy.presencesys.domain.Account;
import com.richasdy.presencesys.service.AccountService;

@RestController
@RequestMapping("apiv1/account")
public class AccountEndpoint {

	// can implement bidingResult here?

	private AccountService accountService;
	// private final Log log = LogFactory.getLog(getClass());

	@Autowired
	public AccountEndpoint(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping()
	public ResponseEntity<Iterable<Account>> index() {
		// log.info("Fetching all Account");

		Iterable<Account> accounts = accountService.findAll();

		if (accounts.iterator().hasNext()) {
			return new ResponseEntity<Iterable<Account>>(accounts, HttpStatus.OK);
		} else {
			return new ResponseEntity<Iterable<Account>>(HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<Account> show(@PathVariable int id) {

		// log.info("Fetching Account with id " + id);

		Account account = accountService.findOne(id);

		if (account == null) {
			// log.info("Account with id " + id + " not found");
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		}

	}

	@PostMapping()
	public ResponseEntity<Account> save(@Valid @RequestBody Account account) {

		// perlu ditambahkan binding result?

		Account accountValid = accountService.save(account);
		// log.info("A Account with id " + account.getId() + " created");
		return new ResponseEntity<Account>(accountValid, HttpStatus.CREATED);

	}

	// untuk mengakomodasi partial update, tanpa validasi wajib
	// public ResponseEntity<Account> update(@PathVariable int id, @RequestBody
	// Account newAccount) {

	@PutMapping("/{id}")
	public ResponseEntity<Account> update(@PathVariable int id, @Valid @RequestBody Account newAccount) {

		// kalau pakai binding result, harus di set manual untuk error response
		// kalau g pakai binding result, error response dibikin oleh validator

		// VULNURABLE
		// log.info("Updating Account " + id);

		// ada kemungkinan dihack
		// merubah data account dengan id = id
		// tapi updatedAccount.getId() nye berbeda
		// pakai postman
		// terutama dalam bentuk ENDPOINT

		// SOLUSI
		// masukkan id sebagai field wajib
		// check PathVariable dan field id harus sama, baru proses update

		// DONE IN CONTROLLER

		// VULNURABLE
		// update data orang lain

		// SOLUSI
		// pakai session id, sehingga cuma bisa rubah data diri sendiri

		Account currentAccount = accountService.findOne(id);

		if (currentAccount == null) {

			// log.info("Account with id " + id + " not found");
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);

		} else {

			// copy changed field into object
			// get null value field
			final BeanWrapper src = new BeanWrapperImpl(newAccount);
			java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
			Set<String> emptyNames = new HashSet<String>();
			for (java.beans.PropertyDescriptor pd : pds) {
				Object srcValue = src.getPropertyValue(pd.getName());
				if (srcValue == null || srcValue.equals(0))
					emptyNames.add(pd.getName());
			}
			String[] result = new String[emptyNames.size()];

			// BeanUtils.copyProperties with ignore field
			BeanUtils.copyProperties(newAccount, currentAccount, emptyNames.toArray(result));

			// update data
			Account account = accountService.update(currentAccount);

			return new ResponseEntity<Account>(account, HttpStatus.OK);

		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Account> delete(@PathVariable int id) {

		// log.info("Fetching & Deleting User with id " + id);

		Account account = accountService.findOne(id);

		if (account == null) {
			// log.info("Unable to delete. Account with id " + id + " not
			// found");
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		} else {
			accountService.delete(id);
			return new ResponseEntity<Account>(HttpStatus.GONE);
		}

	}

}