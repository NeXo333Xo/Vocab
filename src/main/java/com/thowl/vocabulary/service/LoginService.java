package com.thowl.vocabulary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.exception.UserException;
import com.thowl.vocabulary.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class LoginService {

    @Autowired
    UsersRepository usersRepo;

    /**
     * Registers an user and tests if the email or username have already been
     * assigned to another user.
     *
     * @param email
     * @param username
     * @param password
     * @return the new User
     * @throws UserException if email or username already exist
     */
    public Users registerUser(String email, String username, String password) 
    throws UserException {
        log.info("Entering registerUser");
        if (usersRepo.existsByUsername(username)) {
            throw new UserException(
                "Username already exists, choose another one");
        } else if (usersRepo.existsByEmail(email)) {
            throw new UserException(
                "The email is already connected to a User");
        } 
        // good case
        Users user = new Users(email, username, password);
        user.setOnline(true);
        return usersRepo.save(user);
    }

    /**
     * Checks login data and verifies if the credentials are valid
     * 
     * @param username
     * @param password
     * @return the user 
     * @throws UserException
     */
    public Users loginUser(String username, String password) 
    throws UserException {
        log.info("Entering loginUser");
        Users user = usersRepo.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new UserException("Wrong login data");
        } 
        if (user.isOnline()) {
            throw new UserException(
                "You are already connected with another device");
        }
        user.setOnline(true);
        // save the user with online status
        return usersRepo.save(user);
    }

    /**
     * Updates the users online attribut to false and updates it in the DB
     * 
     * @param user
     * @return the exited user
     */
    public Users logoutUser(Users user) {
        log.info("Entering logoutUser");
        Users logoutUser = usersRepo.getReferenceById(user.getUserId());
        logoutUser.setOnline(false);
        return usersRepo.save(logoutUser);
    }

    /**
     * Saves an user to the DB
     * 
     * @param user
     * @return the saved user
     */
    public Users save(Users user) {
        return usersRepo.save(user); 
    }

}
