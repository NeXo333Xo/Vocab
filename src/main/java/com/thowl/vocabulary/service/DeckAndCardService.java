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

    // TODO: Write documentation
    
    public List<Deck> showDecks(Users user) {
        log.info("DeckAndCardService: Entering showDecks");
        return deckRepo.findByUser(user);
    }

    public Deck saveDeck(String deckName, Users user) throws UserException {   
        log.info("DeckAndCardService: Entering saveDeck");
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

    public Card saveCard(
        String front, 
        String back,
        Deck deck, 
        Users user) throws Exception {
            log.info("DeckAndCardService: Entering saveCard");
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

    public long countCards(Deck deck) {
        return cardRepo.countCards(deck.getDeckId());
    }
}




