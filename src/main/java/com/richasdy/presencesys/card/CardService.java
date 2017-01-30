package com.richasdy.presencesys.card;

import org.springframework.stereotype.Service;

@Service
public interface CardService {
	
	Card save(Card entity);
	Card update(Card entity);
	Card findOne(int id);
	Iterable<Card> findAll();
	long count();
	void delete(int id);
	Card deleteSoft(int id);
}
