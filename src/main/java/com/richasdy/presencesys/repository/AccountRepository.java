package com.richasdy.presencesys.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.domain.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

	// find by string type
	Iterable<Account> findDistinctAccountByEmailOrPhoneOrUsernameOrNoteOrPermissionsOrActivationCodeOrPersistCodeOrResetPasswordCode(
			String email, String phone, String username, String note, String permissions, String activationCode,
			String persistCode, String resetPasswordCode);

	// find by integer
	Iterable<Account> findDistinctAccountById(int id);

	Iterable<Account> findDistinctAccountByEmail(String email);

	Iterable<Account> findDistinctAccountByPhone(String phone);

	Iterable<Account> findDistinctAccountByUsername(String username);

	Iterable<Account> findDistinctAccountByNote(String note);

	Iterable<Account> findDistinctAccountByPermissions(String permissions);

	Iterable<Account> findDistinctAccountByActivated(Boolean activated);

	// kurang between, lessthan, greaterthan

	// find by boolean
	// Iterable<Account> findDistinctAccountByActivated(boolean activated);

	// find by Date

	Iterable<Account> findDistinctAccountByActivatedAtBetween(Date start, Date end);

	Iterable<Account> findDistinctAccountByCreatedAtBetween(Date start, Date end);

	Iterable<Account> findDistinctAccountByLastLoginBetween(Date start, Date end);

	Iterable<Account> findDistinctAccountByUpdatedAtBetween(Date start, Date end);

	Iterable<Account> findDistinctAccountByDeletedAtBetween(Date start, Date end);

	// Iterable<Account>
	// findDistinctAccountByActivatedAtOrLastLoginOrCreatedAtOrUpdatedAtOrDeletedAt(Date
	// activatedAt,
	// Date lastLogin, Date createdAt, Date updatedAt, Date deletedAt);

}
