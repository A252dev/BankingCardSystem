package com.cards.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CardsApplicationTests {

	private int value = 1;

	@Test
	void addition() {
		assertEquals(2, ++value);
	}

	@Test
	void contextLoads() {
	}

}
