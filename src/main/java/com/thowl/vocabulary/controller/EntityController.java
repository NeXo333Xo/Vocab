package com.thowl.vocabulary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.entity.Vocabulary;
import com.thowl.vocabulary.repository.UsersRepository;
import com.thowl.vocabulary.repository.VocabularyRepository;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EntityController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    VocabularyRepository vocabularyRepository;

    @GetMapping("")
    public String Index() {
        log.info("Entering showIndex");
        return "index";
    }

    @GetMapping("/register")
    public String registrationPage() {
        log.info("entering registrationPage");
        return "register";
    }

    @PostMapping("/register")
    public String doRegistration(Users user, Model model, HttpSession session) {
        log.info("entering doRegistration");
        if (usersRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username already exists, choose another one");
            return "register";
        } else if (usersRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "There is already an existing user under this email");
            return "register";
        } else {
            // no error
            usersRepository.save(user);
            session.setAttribute("username", user.getUsername());
            model.addAttribute("username", user.getUsername());
            return "redirect:/home";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        log.info("Entering loginPage");
        return "login";
    }

    // TODO funktioniert nicht da jeder darauf zugreifen kann!!!
    // Spring security könnte man nutzen oder ich implementiere selber etwas

    @PostMapping("/login")
    public String doLogin(Users user, Model model, HttpSession session) {
        log.info("Entering doLogin");
        Users storedUser = usersRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (storedUser == null) {
            model.addAttribute("error", "Invalid username or password. Please try again.");
            return "login";
        }

        // Redirect to the home page method with the authenticated user
        session.setAttribute("username", user.getUsername());
        model.addAttribute("username", user.getUsername());
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        String username = session.getAttribute("username").toString();
        log.info("Entering home with user : {}", username);
        Users user = usersRepository.findByUsername(username);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "home";
    }


@GetMapping("/newDeck")
public String newDeck() {
    log.info("Entering newDeck");
    return "newDeck";
}

@PostMapping("/newDeck")
public String createNewDeck(Vocabulary vocabulary,Model model, HttpSession session) {
    String username = session.getAttribute("username").toString();
    log.info("Entering createNewDeck with user : {}", username);
    Users user = usersRepository.findByUsername(username);
    
    if (vocabulary.getName() != null) {
        vocabulary.setUser(user);
    } 
    vocabularyRepository.save(vocabulary);
    return "home";
}



}


    // TODO: Logout and dont forget about session ending
