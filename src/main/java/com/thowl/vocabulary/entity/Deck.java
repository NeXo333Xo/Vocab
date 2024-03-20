package com.thowl.vocabulary.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deckId;

    @lombok.NonNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private Users user;

    
    
    public Deck(String name) {
        this.name = name;
    }


    // Constructors, getters, setters, etc.
    
}
