package com.richasdy.presencesys.repository.rest;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.richasdy.presencesys.domain.Account;

@Component
public class AccountRestRepository extends BaseRestRepository {

	// jumlah method sesuai dengan endpoint
	// harusnya implement AccountService supaya bisa menggantikan CrudRepository
	// tapi menjadi tidak sesuai konteks
	
	private RestTemplate restTemplate = new RestTemplate();
	private final Log log = LogFactory.getLog(getClass());

	public Account save(Account account) {
		
		String uri = ACCOUNT_URI;

		ResponseEntity<Account> response = restTemplate.postForEntity(uri, account, Account.class);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			return null;
		}

	}

	public Account update(Account account) {
		
		String uri = ACCOUNT_URI+"/"+account.getId()+"/update";

		HttpEntity<Account> request = new HttpEntity<Account>(account);
		ResponseEntity<Account> response = restTemplate.exchange(uri, HttpMethod.PUT, request, Account.class);

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			return null;
		}

	}

	public Account findOne(int id) {

		String uri = ACCOUNT_URI+"/"+id;

		ResponseEntity<Account> response = restTemplate.getForEntity(uri, Account.class);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			return null;
		}
	}

	public Iterable<Account> findAll() {

		String uri = ACCOUNT_URI;

		ResponseEntity<Account[]> response = restTemplate.getForEntity(uri, Account[].class);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			Iterable<Account> iterable = Arrays.asList(response.getBody());
			return iterable;
		} else {
			return null;
		}
	}

	public long count() {

		String uri = ACCOUNT_URI;
		
		ResponseEntity<Account[]> response = restTemplate.getForEntity(uri, Account[].class);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody().length;
		} else {
			return 0;
		}

	}

	public void delete(int id) {
		
		String uri = ACCOUNT_URI+"/"+id+"/delete";

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);

		if (response.getStatusCode().equals(HttpStatus.GONE)) {
			log.info("Account dengan id "+ id +" telah di hapus");
		} else {
			log.info("Account dengan id "+ id +" tidak terhapus di hapus");
		}
		
		
	}

}
