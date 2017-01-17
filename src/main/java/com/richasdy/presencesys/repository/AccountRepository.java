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

	// NOTE :
	// untuk int, date, kurang between, lessthan, greaterthan

	// find by string type
	// sementara nanti di dihapus
	Iterable<Account> findDistinctAccountByEmailOrPhoneOrUsernameOrNoteOrPermissionsOrActivationCodeOrPersistCodeOrResetPasswordCode(
			String email, String phone, String username, String note, String permissions, String activationCode,
			String persistCode, String resetPasswordCode);

	// integer Equal
	Iterable<Account> findDistinctAccountById(int id);

	// Boolean equal
	Iterable<Account> findDistinctAccountByActivated(Boolean activated);

	// Date
	Iterable<Account> findDistinctAccountByActivatedAtBetween(Date start, Date end);

	Iterable<Account> findDistinctAccountByCreatedAtBetween(Date start, Date end);

	Iterable<Account> findDistinctAccountByLastLoginBetween(Date start, Date end);

	Iterable<Account> findDistinctAccountByUpdatedAtBetween(Date start, Date end);

	Iterable<Account> findDistinctAccountByDeletedAtBetween(Date start, Date end);

	// String Equal
	Iterable<Account> findDistinctAccountByEmail(String email);

	Iterable<Account> findDistinctAccountByPhone(String phone);

	Iterable<Account> findDistinctAccountByUsername(String username);

	Iterable<Account> findDistinctAccountByNote(String note);

	Iterable<Account> findDistinctAccountByPermissions(String permissions);

	// String Containing
	Iterable<Account> findDistinctAccountByEmailContaining(String email);

	Iterable<Account> findDistinctAccountByPhoneContaining(String phone);

	Iterable<Account> findDistinctAccountByUsernameContaining(String username);

	Iterable<Account> findDistinctAccountByNoteContaining(String note);

	Iterable<Account> findDistinctAccountByPermissionsContaining(String permissions);

}
