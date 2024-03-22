package com.thowl.vocabulary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.thowl.vocabulary.entity.Card;
import com.thowl.vocabulary.entity.Deck;
import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.exception.UserException;
import com.thowl.vocabulary.repository.CardRepository;
import com.thowl.vocabulary.repository.DeckRepository;
import com.thowl.vocabulary.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SessionAttributes("{user, deck}")
public class DeckAndCardService {

    @Autowired
    UsersRepository usersRepo;

    @Autowired
    DeckRepository deckRepo;

    @Autowired
    CardRepository cardRepo;


    public Card findByFront(String front) {
        return cardRepo.findByFront(front);
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
     * Saves and returns a  deck
     * 
     * @param deckName
     * @param user
     * @return the deck 
     * @throws UserException 
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
     * @return
     * @throws Exception
     */
    public Card saveCard(
        String front, 
        String back,
        Deck deck, 
        Users user) throws Exception {
            log.info("Entering saveCard");
            if (deck == null) {
                throw new Exception("ERROR: Deck couldnt be found");
            }else if (cardRepo.existsByDeckAndFront(deck, front)) {
                throw new UserException("Card could not be added. " + 
                "You have a card within this deck with the same front.");
            } 
            // good case
            Card card = new Card(front, back, deck, user);
            return cardRepo.save(card);
    }

    /**
     * Counts the cards corresponding to specific deck
     * @param deck
     * @return
     */
    public long countCards(Deck deck) {
        return cardRepo.countCards(deck.getDeckId());
    }
}




