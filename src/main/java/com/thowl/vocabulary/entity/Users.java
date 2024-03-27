package com.thowl.vocabulary.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a user in the system.
 */
@Entity
@Data
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    
    private String email;

    private String username;

    private String password;

    private boolean Admin = false;

    private boolean Online = false;



    /**
     * Constructs a new Users object with the provided email, username, and password.
     * 
     * @param email    The email address of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public Users(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * Clears the password of the user.
     */
    public void clearPassword() {
        this.password = null;
    }
}
