package com.richasdy.presencesys.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.domain.Account;

@Repository
public interface AccountPageAndSortRepository extends PagingAndSortingRepository<Account, Integer> {

	// NOTE :
	// untuk int, date, kurang between, lessthan, greaterthan

	// find by string type
	// sementara nanti di dihapus
	// Page<Account>
	// findDistinctAccountByEmailOrPhoneOrUsernameOrNoteOrPermissionsOrActivationCodeOrPersistCodeOrResetPasswordCode(
	// String email, String phone, String username, String note, String
	// permissions, String activationCode,
	// String persistCode, String resetPasswordCode);

	// integer Equal
	Page<Account> findDistinctAccountById(int id, Pageable pageable);

	// Boolean equal
	Page<Account> findDistinctAccountByActivated(Boolean activated, Pageable pageable);

	// Date
	Page<Account> findDistinctAccountByActivatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Account> findDistinctAccountByCreatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Account> findDistinctAccountByLastLoginBetween(Date start, Date end, Pageable pageable);

	Page<Account> findDistinctAccountByUpdatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Account> findDistinctAccountByDeletedAtBetween(Date start, Date end, Pageable pageable);

	// String Equal
	Page<Account> findDistinctAccountByEmail(String email, Pageable pageable);

	Page<Account> findDistinctAccountByPhone(String phone, Pageable pageable);

	Page<Account> findDistinctAccountByUsername(String username, Pageable pageable);

	Page<Account> findDistinctAccountByNote(String note, Pageable pageable);

	Page<Account> findDistinctAccountByPermissions(String permissions, Pageable pageable);

	// String Containing
	Page<Account> findDistinctAccountByEmailContaining(String email, Pageable pageable);

	Page<Account> findDistinctAccountByPhoneContaining(String phone, Pageable pageable);

	Page<Account> findDistinctAccountByUsernameContaining(String username, Pageable pageable);

	Page<Account> findDistinctAccountByNoteContaining(String note, Pageable pageable);

	Page<Account> findDistinctAccountByPermissionsContaining(String permissions, Pageable pageable);

}
