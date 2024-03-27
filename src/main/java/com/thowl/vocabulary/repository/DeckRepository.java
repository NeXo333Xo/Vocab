package com.thowl.vocabulary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.entity.Deck;
import java.util.List;




public interface DeckRepository extends JpaRepository<Deck, Long> {
    Deck findByName(String name);
    Deck findByDeckId(long deckId);
    Deck findByNameAndUser(String name, Users user);
    List<Deck> findByUser(Users user);
    boolean existsByNameAndUser(String name, Users user);
}
