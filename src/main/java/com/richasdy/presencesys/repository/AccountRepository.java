package com.richasdy.presencesys.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.domain.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

	Iterable<Account> findDistinctAccountByIdOrEmailOrPhoneOrUsernameOrNoteOrPermissions(int id, String email,
			String phone, String username, String note, String permissions);
	// Iterable<Account> findDistinctAccountByNoteOrEmailOrId(String note,
	// String email, int id);
	// id : int
	// email : String
	// phone : String
	// username : String
	// password : String
	// note : String
	// permissions : String
	// activated : Boolean
	// activationCode : String
	// activatedAt : Date
	// lastLogin : Date
	// persistCode : String
	// resetPasswordCode : String
	// createdAt : Date
	// updatedAt : Date
	// deletedAt : Date
}
