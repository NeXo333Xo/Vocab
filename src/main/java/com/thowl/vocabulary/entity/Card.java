package com.thowl.vocabulary.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a flashcard.
 */
@Entity
@Data
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    private String front;

    private String back;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    Deck deck;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    

    /**
     * Constructs a new Card object with the provided front and back content.
     * 
     * @param front The content for the front side of the flashcard.
     * @param back  The content for the back side of the flashcard.
     */
    public Card(String front, String back) {
        this.front = front;
        this.back = back;
    }

    /**
     * Constructs a new Card object with the provided front and back content, associated
     * with a specific deck and user.
     * 
     * @param front The content for the front side of the flashcard.
     * @param back  The content for the back side of the flashcard.
     * @param deck  The deck to which the flashcard belongs.
     * @param user  The user who owns the flashcard.
     */
    public Card(String front, String back, Deck deck, Users user) {
        this.front = front;
        this.back = back;
        this.deck = deck;
        this.user = user;
    }
}
