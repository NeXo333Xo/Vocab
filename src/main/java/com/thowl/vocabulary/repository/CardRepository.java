package com.thowl.vocabulary.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thowl.vocabulary.entity.Card;
import com.thowl.vocabulary.entity.Deck;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {             
    Card findByFront(String front);
    Card findByCardId(Long cardId);
    List<Card> findByDeck(Deck deck);

    Boolean existsByDeckAndFront(Deck deck, String front);
    Card findByFrontAndDeck(String front, Deck deck);
    Card findByFrontStartsWith(String front);


    @Query("SELECT COUNT(c) FROM Card c WHERE c.deck.id = :deckId")
    long countCards(@Param("deckId") Long deckId);



    
}