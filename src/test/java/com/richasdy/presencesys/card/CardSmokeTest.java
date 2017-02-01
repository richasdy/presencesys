package com.richasdy.presencesys.card;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.richasdy.presencesys.card.CardController;
import com.richasdy.presencesys.card.CardRepository;
import com.richasdy.presencesys.card.CardService;
import com.richasdy.presencesys.card.CardServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CardSmokeTest {
	
	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private CardController cardController;

	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardServiceImpl cardServiceImpl;
	
	
	
	
	@Test
	public void contexLoadsCardRepository() throws Exception {
		assertThat(cardRepository).isNotNull();
	}

	@Test
	public void contexLoadsCardController() throws Exception {
		assertThat(cardController).isNotNull();
	}

	@Test
	public void contexLoadsCardService() throws Exception {
		assertThat(cardService).isNotNull();
	}
	
	@Test
	public void contexLoadsCardServiceImpl() throws Exception {
		assertThat(cardServiceImpl).isNotNull();
	}

}
