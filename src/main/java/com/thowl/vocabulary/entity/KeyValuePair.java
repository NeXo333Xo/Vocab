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
public class KeyValuePair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long KeyValuePairId;

    private String keyString;

    private String valueString;


    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    @ManyToOne
    @JoinColumn(name = "vocabulary_id")
    Vocabulary vocabulary;
}
