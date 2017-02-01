package com.richasdy.presencesys.card;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.account.Account;
import com.richasdy.presencesys.user.User;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer>{
	
	Page<User> findDistinctUserById(long id, Pageable pageable);

	Page<User> findDistinctUserByIdCard(long idCard, Pageable pageable);

	// Date
	Page<User> findDistinctUserByCreatedAtBetween(Date start, Date end, Pageable pageable);

	Page<User> findDistinctUserByUpdatedAtBetween(Date start, Date end, Pageable pageable);

	Page<User> findDistinctUserByDeletedAtBetween(Date start, Date end, Pageable pageable);

	// String Containing
	Page<User> findDistinctUserByUserNumberContaining(String userNumber, Pageable pageable);
	
	Page<User> findDistinctUserByNamaContaining(String nama, Pageable pageable);

	Page<User> findDistinctUserByNoteContaining(String note, Pageable pageable);
	
}
