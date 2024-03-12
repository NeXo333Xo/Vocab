package com.thowl.vocabulary.entity;

import java.util.HashMap;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class Vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long VocabularyId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

    @lombok.NonNull
    private String name;
    

    HashMap voc;


    public Vocabulary(String name) {
        this.name = name;
    }

    

    // Constructors, getters, setters, etc.
    
}
