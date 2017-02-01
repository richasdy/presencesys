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
import com.richasdy.presencesys.card.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{
	
	Page<Card> findDistinctCardById(long id, Pageable pageable);

	// Date
	Page<Card> findDistinctCardByCreatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Card> findDistinctCardByUpdatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Card> findDistinctCardByDeletedAtBetween(Date start, Date end, Pageable pageable);

	// String Containing
	Page<Card> findDistinctCardByCardNumberContaining(String cardNumber, Pageable pageable);
	
	Page<Card> findDistinctCardByNoteContaining(String note, Pageable pageable);
	
	//Boolean
	Page<Card> findDistinctCardByActivated(Boolean activated, Pageable pageable);
	
	
	Card findCardByCardNumberContaining(String cardNumber);
	
}
