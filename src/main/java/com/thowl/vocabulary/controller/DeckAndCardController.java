package com.thowl.vocabulary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.thowl.vocabulary.entity.Card;
import com.thowl.vocabulary.entity.Deck;
import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.exception.UserException;
import com.thowl.vocabulary.service.DeckAndCardService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@SessionAttributes({"user", "deck"})
@Slf4j
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
    public String homePage(Model model, HttpSession session) {
        session.removeAttribute("deck");
        Users user = (Users) session.getAttribute("user");
        log.info("Entering home with user : {}", user.getUsername());
        log.info("deck: {}", session.getAttribute("deck"));
        List<Deck> decks = deckCardSvc.showUsersDecks(user);
        model.addAttribute("decks", decks);
        return "home";
    }

    /*
    @PostMapping("/home") 
    public String chooseDeck()
    }
    */

    /**
     * Handles GET request for newDeck. Shows form for creating a new deck
     * @return
     */
    @GetMapping("/newDeck")
    public String newDeckPage() {
        log.info("Entering newDeck");
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
        log.info("deck: {}", session.getAttribute("deck"));
        try {
            if (deckName.length() <= 0) {
                throw new UserException("It was no deck name provided");
            } 
            Deck deck = deckCardSvc.saveDeck(deckName, user); // stores in DB
            model.addAttribute("deck", deck);
            session.setAttribute("deck", deck);
            return "redirect:/newCard";
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "newDeck";
        }
    }

    /**
     * Handles GET request for newCard. 
     * Displays current Number of cards in the deck.
     * 
     * @param model
     * @param session
     * @return newCard
     */
    @GetMapping("/newCard")
    public String newCardPage(
        @RequestParam(name="deckId", required = false) Long deckId, // wrapper 
        Model model, 
        HttpSession session) {
        log.info("Entering newCardPage");
        try {
            // set deck seession if person comes from home.html
            if (deckId != null) {
                Deck currentDeck = deckCardSvc.findByDeckId(deckId);
                session.setAttribute("deck", currentDeck); 
            } 

            Deck deck = (Deck) session.getAttribute("deck");
            if (deck == null) {
                throw new UserException("No deck found");
            }
            // error because deck session is added in add new deck
            model.addAttribute("deck", deck);
            model.addAttribute("countCards", deckCardSvc.countCards(deck));
            return "newCard";
        } catch (UserException e) {
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
        log.info("Entering createNewCard");
        log.info("deck: {}", session.getAttribute("deck"));
        try {
            if (front == null || back == null) {
                throw new UserException("It was no card info provided");
            }
            // get user and vocabulary through session
            Users user = (Users) session.getAttribute("user");
            Deck deck = (Deck) session.getAttribute("deck");
            deckCardSvc.saveCard(front, back, deck, user);
                return "redirect:/newCard";
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "newCard";
        } catch (Exception e) {
            model.addAttribute("unexpectedError", 
            e .getMessage());
            model.addAttribute("stackTrace", e.getStackTrace());
            return "error";
        }
    }

    @GetMapping("/showCards/{deckId}")
    public String studyDeckPage(@PathVariable Long deckId, Model model, HttpSession session) {
        // Retrieve the selected deck
        Deck deck = deckCardSvc.findByDeckId(deckId);
        // Check if the deck belongs to the current user 
        Users user = (Users) session.getAttribute("user");
        if (deck.getUser().getUserId() != user.getUserId()) {
            model.addAttribute("unexpectedError", "The chosen deck and user dont match");
            return "error"; // You can create an error page to handle this case
        }
        // Fetch cards associated with the deck
        List<Card> cards = deckCardSvc.showDecksCards(deck);
        // Pass deck and cards to the view
        model.addAttribute("deck", deck);
        model.addAttribute("cards", cards);
        session.setAttribute("deck", deck);
        return "showCards";
    }

    @GetMapping("/study/{deckId}")
    public String testDeckPage(@PathVariable Long deckId, Model model, HttpSession session) {
        // Retrieve the selected deck
        Deck deck = deckCardSvc.findByDeckId(deckId);
        // Check if the deck belongs to the current user 
        Users user = (Users) session.getAttribute("user");
        if (deck.getUser().getUserId() != user.getUserId()) {
            model.addAttribute("unexpectedError", "The chosen deck and user dont match");
            return "error"; // You can create an error page to handle this case
        }
        // Fetch cards associated with the deck
        List<Card> cards = deckCardSvc.showDecksCards(deck);
        // Pass deck and cards to the view
        model.addAttribute("deck", deck);
        model.addAttribute("cards", cards);
        session.setAttribute("deck", deck);
        return "study";
    }


    /*
    @GetMapping("/editDeck")
    public String editDeckPage(
        @RequestParam(name="deckId", required=true) Long deckId, 
        Model model,
        HttpSession session) {
            log.info("Entering: editCard");
            // sets deck session 
            if (deckId != null) {
                Deck currentDeck = deckCardSvc.findByDeckId(deckId);
                session.setAttribute("deck", currentDeck); 
                model.addAttribute("deck", currentDeck);
                
            } 
            
            return "editDeck";
    }


    @PostMapping("/editDeck") 
    public String editDeck(
        @RequestParam(name="deckId") Long deckId,
        @ModelAttribute("search") String search,
        Model model,
        HttpSession session) {
            Deck deck = (Deck) session.getAttribute("deck");
            List<Card> cards = deckCardSvc.showDecksCards(deck);
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).getFront().contains(search)) {
                    Card editCard = cards.get(i);
                    log.info("editCard: {}", editCard);
                    session.setAttribute("editCard", editCard);
                    model.addAttribute("editCard", editCard);
                    return "editDeck";
                }
            }
            model.addAttribute("error", "Card not found");
            return "editDeck";
        }
    
        */
}
