package com.thowl.vocabulary.entity;

import org.springframework.beans.factory.annotation.Autowired;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @lombok.NonNull
    private String username;
    @lombok.NonNull
    private String password;

    // need to change that later
    @OneToOne
    private Vocabulary vocabulary;
}
