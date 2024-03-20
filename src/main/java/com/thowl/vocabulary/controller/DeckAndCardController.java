package com.thowl.vocabulary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.thowl.vocabulary.entity.Deck;
import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.exception.UserException;
import com.thowl.vocabulary.service.DeckAndCardService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@SessionAttributes({"user", "deck"})
public class DeckAndCardController {

    @Autowired
    DeckAndCardService deckCardSvc;

    /**
     * Handles GET request for home. Shows username and his decks
     * 
     * @param model
     * @param session 
     */
    @GetMapping("/home")
    public String homePageold(Model model, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        log.info("DeckAndCardController: Entering home with user : {}", user.getUsername());
        model.addAttribute("decks", deckCardSvc.showDecks(user));
        return "home";
    }


    /**
     * Handles GET request for newDeck. Shows form for creating a new deck
     * @return
     */
    @GetMapping("/newDeck")
    public String newDeckPage() {
        log.info("DeckAndCardController: Entering newDeck");
        return "newDeck";
    }

    /**
     * Handles POST request for newDeck. Accepts argument for a new Deck name
     * 
     * @param deckName
     * @param model
     * @param session
     * @return redirect/newCard else newDeck
     * @throws Exception
     */
    @PostMapping("/newDeck")
    public String createNewDeck(
            @RequestParam("deckName") String deckName,
            Model model,
            HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        log.info("Entering createNewDeck with user : {}", user.getUsername());
        try {
            if (deckName.length() <= 0) {
                throw new UserException("It was no deck name provided");
            } 
            Deck deck = deckCardSvc.saveDeck(deckName, user); // stores in DB

            session.setAttribute("deck", deck);
            log.info("DeckName: {}", deck.getName());
            return "redirect:/newCard";
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "newDeck";
        }
    }



    // TODO: /newCard get and for both documentation

    /**
     * Handles GET request for newCard. 
     * Displays current Number of cards in the deck.
     * 
     * @param model
     * @param session
     * @return newCard
     */
    @GetMapping("/newCard")
    public String newCardPage(Model model, HttpSession session) {
        log.info("Entering newCardPage");
        try {
            Deck deck = (Deck) session.getAttribute("deck");
            if (deck == null) {
                throw new Exception("No deck found");
            }
            log.info("DeckName: {}", deck.getName());
            // maybe add this to the session of deck
            model.addAttribute("countCards", deckCardSvc.countCards(deck));
            return "newCard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "newCard";
        }
    }

    // look into composite key and maybe connect front and back with each other
    /**
     * Handles POST request for newCard.
     * Shows input fields front and back for adding new vocabulary cards
     * 
     * @param front
     * @param back
     * @param model
     * @param session
     * @return newCard. Either with new Card added or with error
     */
    @PostMapping("/newCard")
    public String createNewCard(
            @ModelAttribute("front") String front,
            @ModelAttribute("back") String back,
            Model model,
            HttpSession session) {
        log.info("DeckAndCardController: Entering newCard");
        try {
            if (front == null || back == null) {
                throw new Exception("It was no card info provided");
            }
            // get user and vocabulary through session
            Users user = (Users) session.getAttribute("user");
            Deck deck = (Deck) session.getAttribute("deck");
            deckCardSvc.saveCard(front, back, deck, user);
                return "redirect:/newCard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "newCard";
        }
    }
}
