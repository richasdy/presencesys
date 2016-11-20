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

	// find by boolean
	Iterable<Account> findDistinctAccountByActivated(boolean activated);

	// find by Date

	Iterable<Account> findDistinctAccountByCreatedAtBetween(Date start, Date end);

	// Iterable<Account>
	// findDistinctAccountByActivatedAtOrLastLoginOrCreatedAtOrUpdatedAtOrDeletedAt(Date
	// activatedAt,
	// Date lastLogin, Date createdAt, Date updatedAt, Date deletedAt);

}
