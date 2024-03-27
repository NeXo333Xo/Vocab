package com.thowl.vocabulary.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.thowl.vocabulary.entity.Card;
import com.thowl.vocabulary.entity.Deck;
import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.repository.CardRepository;
import com.thowl.vocabulary.repository.DeckRepository;
import com.thowl.vocabulary.repository.UsersRepository;

public class DeckAndCardServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private DeckAndCardService deckAndCardService;

    private Users user;
    private Deck deck;
    private Card card;

    @Test
    public void setup() {
        user = new Users();
        user.setUserId(1L);
        user.setUsername("testUser");

        deck = new Deck();
        deck.setDeckId(1L);
        deck.setName("Test Deck");
        deck.setUser(user);

        card = new Card("Test Front", "Test Back", deck, user);
    }
}