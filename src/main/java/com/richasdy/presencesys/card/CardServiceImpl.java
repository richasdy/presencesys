package com.richasdy.presencesys.card;

import java.util.Date;

import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

	CardRepository repository;
	
	@Autowired
	public CardServiceImpl(CardRepository repository){
		this.repository = repository;
	}

	@Override
	public Card save(Card entity) {
		entity.setCreatedAt(new Date());
		return repository.save(entity);
	}
	
	@Override
	public Card update(Card entity) {
		entity.setUpdatedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Card findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	public Iterable<Card> findAll() {
		return repository.findAll();
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public void delete(int id) {
		repository.delete(id);
	}
	
	@Override
	public Card deleteSoft(int id) {
		Card entity = repository.findOne(id);
		entity.setDeletedAt(new Date());
		return repository.save(entity);
	}
	
}
