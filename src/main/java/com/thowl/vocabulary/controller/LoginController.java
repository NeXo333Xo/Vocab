package com.thowl.vocabulary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.exception.UserException;
import com.thowl.vocabulary.service.LoginService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@SessionAttributes("user")
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * Handles GET request for index
     */
    @GetMapping("")
    public String indexPage() {
        log.info("LoginController: Entering showIndex");
        return "index";
    }


    /**
     * Handles GET request for register
     */
    @GetMapping("/register")
    public String registrationPage() {
        log.info("LoginController: Entering registrationPage");
        return "register";
    }

    /**
     * Handles POST request for register
     * 
     * @param email
     * @param username
     * @param password
     * @param model
     * @param session
     * @return redirect:/home else register 
     */
    @PostMapping("/register")
    public String doRegistration(
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {
        log.info("LoginController: Entering doRegistration");
        try {
            if (email == null || username == null || password == null) {
                throw new UserException("Not all the necessary data was provided");
            } // check credentials
            Users user = loginService.registerUser(email, username, password);
            user.clearPassword();

            session.setAttribute("user", user);
            log.info("LoginController: Registered new User: {}", username);
            return "redirect:/home";
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
       
    }


    /**
     * Handles GET request for login
     */
    @GetMapping("/login")
    public String loginPage() {
        log.info("LoginController: Entering loginPage");
        return "login";
    }

    /**
     * 
     * @param username
     * @param password
     * @param model
     * @param session
     * @return redirect:/home else login
     */
    @PostMapping("/login")
    public String doLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {
        log.info("Entering doLogin");
        try {
            if (username == null || password == null) {
                throw new UserException("Not all the necessary data was provided");
            }
            // check credentials and makes user status: online true
            Users user = loginService.loginUser(username, password);
            user.clearPassword();

            session.setAttribute("user", user);
            log.info("Login in with User: {}", username);
            return "redirect:/home";
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }


    // add java script timer
    /**
     * Handles GET request for logout
     * 
     * @param session
     * @return
     */
    @GetMapping("/logout") 
    public String logout(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        log.info("LoginController: Entering logout with user {}", 
        user.getUsername());
        loginService.logoutUser(user); // loged out + user status: online false;
        session.invalidate();
        return "redirect:/login";
     } 
        
    
}
