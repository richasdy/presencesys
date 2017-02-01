package com.richasdy.presencesys.user;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

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
	
	User findUserByIdCard(long idCard);

}
