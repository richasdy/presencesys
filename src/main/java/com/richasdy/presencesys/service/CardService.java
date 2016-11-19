package com.richasdy.presencesys.service;

import org.springframework.stereotype.Service;
import com.richasdy.presencesys.domain.Card;
import com.richasdy.presencesys.domain.Card;

@Service
public interface CardService {
	
	Card save(Card card);
	Card update(Card card);
	Card findOne(int id);
	Iterable<Card> findAll();
	long count();
	void delete(int id);
	Card deleteSoft(int id);
}
