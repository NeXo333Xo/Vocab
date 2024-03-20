package com.thowl.vocabulary.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
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

    

    public Card(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public Card(String front, String back, Deck deck, Users user) {
        this.front = front;
        this.back = back;
        this.deck = deck;
        this.user = user;
    }

    
}
