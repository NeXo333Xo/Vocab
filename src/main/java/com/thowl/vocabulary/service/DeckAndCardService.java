package com.thowl.vocabulary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thowl.vocabulary.entity.Card;
import com.thowl.vocabulary.entity.Deck;
import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.exception.UserException;
import com.thowl.vocabulary.repository.CardRepository;
import com.thowl.vocabulary.repository.DeckRepository;
import com.thowl.vocabulary.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class providing methods to manage decks and cards.
 */
@Service
@Slf4j
public class DeckAndCardService {

    @Autowired
    UsersRepository usersRepo;

    @Autowired
    DeckRepository deckRepo;

    @Autowired
    CardRepository cardRepo;

    /**
     * Removes a card from the database by its ID.
     *
     * @param cardId The ID of the card to be removed.
     */
    public void removeByCardId(Long cardId) {
        cardRepo.removeByCardId(cardId);
    }

    /**
     * Finds a card in the database by the starting characters of its front content.
     *
     * @param front The starting characters of the front content to search for.
     * @return The card whose front content starts with the specified characters, or
     *         null if not found.
     */
    public Card findByFrontStartsWith(String front) {
        return cardRepo.findByFrontStartsWith(front);
    }

    /**
     * Finds a card in the database by its front content and associated deck.
     *
     * @param front The front content of the card to search for.
     * @param deck  The deck associated with the card.
     * @return The card with the specified front content and deck, or null if not
     *         found.
     */
    public Card findByFrontAndDeck(String front, Deck deck) {
        return cardRepo.findByFrontAndDeck(front, deck);
    }

    /**
     * Finds a card in the database by its front content.
     *
     * @param front The front content of the card to search for.
     * @return The card with the specified front content, or null if not found.
     */
    public Card findByFront(String front) {
        return cardRepo.findByFront(front);
    }

    /**
     * Finds a user in the database by their user ID.
     *
     * @param userId The ID of the user to search for.
     * @return The user with the specified user ID, or null if not found.
     */
    public Users findByUserId(long userId) {
        return usersRepo.findByUserId(userId);
    }

    /**
     * Counts the cards corresponding to specific deck
     * 
     * @param deck
     * @return
     */
    public long countCards(Deck deck) {
        return cardRepo.countCards(deck.getDeckId());
    }

    /**
     * Gets all the decks a user has created
     * 
     * @param user
     * @return list of deck objects
     */
    public List<Deck> showUsersDecks(Users user) {
        log.info("Entering showUsersDecks");
        return deckRepo.findByUser(user);
    }

    /**
     * Gets all cards a deck contains
     *
     * @param deck
     * @return list of card objects
     */
    public List<Card> showDecksCards(Deck deck) {
        log.info("Entering showDecksCards");
        return cardRepo.findByDeck(deck);
    }

    /**
     * Gets the user by his userId
     * 
     * @param deckId
     * @return the user
     */
    public Deck findByDeckId(long deckId) {
        log.info("Entering findByDeckId");
        return deckRepo.findByDeckId(deckId);
    }

    /**
     * Saves and returns a deck
     * 
     * @param deckName
     * @param user
     * @return the deck
     * @throws UserException if the deck already exists
     */
    public Deck saveDeck(String deckName, Users user) throws UserException {
        log.info("Entering saveDeck");
        if (deckRepo.existsByNameAndUser(deckName, user) == false) {
            Deck deck = new Deck();
            deck.setName(deckName);
            deck.setUser(user);
            return deckRepo.save(deck);
        } else {
            throw new UserException("U already have a deck with the " +
                    "same name, choose another one.");
        }
    }

    /**
     * Saves and returns a card
     * 
     * @param front
     * @param back
     * @param deck
     * @param user
     * @return the saved card
     * @throws Exception
     */
    public Card saveCard(
            String front,
            String back,
            Deck deck,
            Users user) throws Exception {
        log.info("Entering saveCard");
        if (deck == null | user == null) {
            throw new Exception("ERROR: Deck or user couldnt be found");
        } else if (cardRepo.existsByDeckAndFront(deck, front)) {
            throw new UserException("Card could not be added. " +
                    "You have a card within this deck with the same front.");
        }
        // good case
        Card card = new Card(front, back, deck, user);
        return cardRepo.save(card);
    }
}
