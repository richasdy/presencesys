package com.richasdy.presencesys.card;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.card.Card;

@Service
public interface CardService {

	Card save(Card entity);

	Card update(Card entity);

	Card findOne(long id);

	long count();

	void delete(long id);

	Card deleteSoft(long id);
	
	Page<Card> searchBy(String searchTerm, Pageable pageable);

	Page<Card> findAll(Pageable pageable);
	
	Iterable<Card> findAll();
	
	Card findByCardNumber(String cardNumber);
}
