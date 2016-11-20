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

	private AccountService service;
	// private final Log log = LogFactory.getLog(getClass());

	@Autowired
	public AccountEndpoint(AccountService service) {
		this.service = service;
	}

	@GetMapping()
	public ResponseEntity<Iterable<Account>> index() {
		// log.info("Fetching all Account");

		Iterable<Account> iterableEntity = service.findAll();

		if (iterableEntity.iterator().hasNext()) {
			return new ResponseEntity<Iterable<Account>>(iterableEntity, HttpStatus.OK);
		} else {
			return new ResponseEntity<Iterable<Account>>(HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<Account> show(@PathVariable int id) {

		// log.info("Fetching Account with id " + id);

		Account entity = service.findOne(id);

		if (entity == null) {
			// log.info("Account with id " + id + " not found");
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Account>(entity, HttpStatus.OK);
		}

	}

	@PostMapping()
	public ResponseEntity<Account> save(@Valid @RequestBody Account entity) {

		Account entityValid = service.save(entity);
		// log.info("A Account with id " + account.getId() + " created");
		return new ResponseEntity<Account>(entityValid, HttpStatus.CREATED);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Account> update(@PathVariable int id, @Valid @RequestBody Account newEntity) {

		Account currentEntity = service.findOne(id);

		if (currentEntity == null) {

			// log.info("Account with id " + id + " not found");
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);

		} else {

			// copy changed field into object
			// get null value field
			final BeanWrapper src = new BeanWrapperImpl(newEntity);
			java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
			Set<String> emptyNames = new HashSet<String>();
			for (java.beans.PropertyDescriptor pd : pds) {
				Object srcValue = src.getPropertyValue(pd.getName());
				if (srcValue == null || srcValue.equals(0))
					emptyNames.add(pd.getName());
			}
			String[] result = new String[emptyNames.size()];

			// BeanUtils.copyProperties with ignore field
			BeanUtils.copyProperties(newEntity, currentEntity, emptyNames.toArray(result));

			// update data
			Account entity = service.update(currentEntity);

			return new ResponseEntity<Account>(entity, HttpStatus.OK);

		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Account> delete(@PathVariable int id) {

		// log.info("Fetching & Deleting User with id " + id);

		Account entity = service.findOne(id);

		if (entity == null) {
			// log.info("Unable to delete. Account with id " + id + " not
			// found");
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		} else {
			service.delete(id);
			return new ResponseEntity<Account>(HttpStatus.GONE);
		}

	}
	
	@GetMapping("/search/{searchTerm}")
	public ResponseEntity<Iterable<Account>> search(@PathVariable String searchTerm) {

		// log.info("Fetching Account with id " + id);

		Iterable<Account> iterableEntity = service.search(searchTerm);

		if (iterableEntity.iterator().hasNext()) {
			return new ResponseEntity<Iterable<Account>>(iterableEntity, HttpStatus.OK);
		} else {
			return new ResponseEntity<Iterable<Account>>(HttpStatus.NO_CONTENT);
		}

	}

}