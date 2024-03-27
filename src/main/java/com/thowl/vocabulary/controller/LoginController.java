package com.thowl.vocabulary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.exception.UserException;
import com.thowl.vocabulary.service.LoginService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller class responsible for handling authentication-related requests,
 * such as user registration, login, and logout.
 */
@Controller
@RequestMapping("/api/auth")
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * Handles a GET request for the index page.
     * 
     * @return index (login or register?)
     */
    @GetMapping("")
    public String indexPage() {
        log.info("Entering showIndex");
        return "index";
    }

    /**
     * Handles GET request for register
     * 
     * @return register
     */
    @GetMapping("/register")
    public String registrationPage() {
        log.info("Entering registrationPage");
        return "register";
    }

    /**
     * Handles a POST request for user registration.
     * 
     * @param email    The email address of the user to register.
     * @param username The username of the user to register.
     * @param password The password of the user to register.
     * @param model    The model to add attributes for the view.
     * @return A redirect to the home page if registration is successful, otherwise
     *         the registration page.
     */
    @PostMapping("/register")
    public String doRegistration(
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        log.info("Entering doRegistration");
        try {
            if (email == null || username == null || password == null) {
                throw new UserException("Not all the necessary data was provided");
            } // check credentials
            Users user = loginService.registerUser(email, username, password);

            log.info("Registered new User: {}", username);
            return "redirect:/api/users/" + user.getUserId() + "/home";
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    /**
     * Handles GET request for login
     * 
     * @return login
     */
    @GetMapping("/login")
    public String loginPage() {
        log.info("Entering loginPage");
        return "login";
    }

    /**
     * Handles a POST request for user login.
     * 
     * @param username The username of the user attempting to login.
     * @param password The password of the user attempting to login.
     * @param model    The model to add attributes for the view.
     * @return A redirect to the home page if login is successful, otherwise the
     *         login page.
     */
    @PostMapping("/login")
    public String doLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        log.info("Entering doLogin");
        try {
            if (username == null || password == null) {
                throw new UserException("Not all the necessary data was provided");
            }
            // check credentials and makes user status: online true
            Users user = loginService.loginUser(username, password);

            log.info("Login in with User: {}", username);
            return "redirect:/api/users/" + user.getUserId() + "/home";
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    /**
     * Handles a GET request for user logout.
     * 
     * @param userId The ID of the user to logout.
     * @return A redirect to the login page.
     */
    @GetMapping("/logout/users/{userId}")
    public String logout(
            @PathVariable("userId") long userId) {
        log.info("Entering logout");
        loginService.logoutUser(userId);
        return "redirect:/api/auth/login";
    }
}
