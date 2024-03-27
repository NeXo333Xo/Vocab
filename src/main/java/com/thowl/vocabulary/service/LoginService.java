package com.thowl.vocabulary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.exception.UserException;
import com.thowl.vocabulary.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class providing methods to manage user authentication and registration.
 */
@Service
@Slf4j
public class LoginService {

    @Autowired
    UsersRepository usersRepo;

    /**
     * Registers a new user and verifies if the provided email or username
     * are already associated with another user.
     *
     * @param email    The email of the user to be registered.
     * @param username The username of the user to be registered.
     * @param password The password of the user to be registered.
     * @return The newly registered user.
     * @throws UserException If the email or username already exist.
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
     * Validates the provided login credentials and logs in the user if the
     * credentials are correct.
     * 
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return The logged-in user.
     * @throws UserException If the login data is incorrect or the user is already
     *                       logged in from another device.
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
     * Logs out the user by updating the user's online attribute to false and saving
     * the changes in the database.
     * 
     * @param userId The ID of the user to log out.
     * @return The user after logging out.
     */
    public Users logoutUser(long userId) {
        log.info("Entering logoutUser");
        Users logoutUser = usersRepo.getReferenceById(userId);
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

    /**
     * Finds the user by his userId
     * 
     * @param userId
     * @return the found user
     */
    public Users findByUserId(long userId) {
        return usersRepo.findByUserId(userId);
    }

}
