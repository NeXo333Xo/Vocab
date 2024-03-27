package com.thowl.vocabulary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.thowl.vocabulary.entity.Card;
import com.thowl.vocabulary.entity.Deck;
import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.exception.UserException;
import com.thowl.vocabulary.service.DeckAndCardService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/users/{userId}/home") // Base URL for all mappings in this controller
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
    @GetMapping("")
    public String homePage(
        @PathVariable("userId") Long userId,
        Model model) {

        Users user = deckCardSvc.findByUserId(userId);
        log.info("Entering home with user : {}", user.getUsername());

        List<Deck> decks = deckCardSvc.showUsersDecks(user);
        model.addAttribute("decks", decks);
        return "home";
    }

    /**
     * Handles GET request for newDeck. Shows form for creating a new deck
     * 
     * @return 
     */
    @GetMapping("/newDeck")
    public String newDeckPage() {
        log.info("Entering newDeck");
        return "newDeck";
    }

    @PostMapping("/newDeck")
    public String createNewDeck(
        @RequestParam("deckName") String deckName,
        @PathVariable("userId") Long userId,
        Model model) {
            log.info("Entering createNewDeck");
        if (deckName == null) {
            model.addAttribute("error", "No deck name given");
            return "newDeck";
        }
        try {
            Users user = deckCardSvc.findByUserId(userId);
            Deck deck = deckCardSvc.saveDeck(deckName, user);
            log.info("saveDeck");
            return "redirect:newCard/decks/" + deck.getDeckId(); // add decks/deckId
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "newDeck";
        }
    }


    @GetMapping("/newCard/decks/{deckId}")
    public String newCardPage(
        @PathVariable("deckId") Long deckId,
        Model model) {
            log.info("Entering newCardPage");
            model.addAttribute("deckid", deckId);

            Deck deck = deckCardSvc.findByDeckId(deckId);
            long cardCounter = deckCardSvc.countCards(deck);
            model.addAttribute("cardCounter", cardCounter);
            return "newCard";
    }

    @PostMapping("/newCard/decks/{deckId}")
    public String createNewCard(
        @PathVariable("userId") Long userId,
        @PathVariable("deckId") Long deckId,
        @RequestParam("front") String front,
        @RequestParam("back") String back,
        Model model) {
        log.info("Entering createNewCard");
        if (front == null | back == null) {
            model.addAttribute("error", "Empty card");
            return "newCard";
        }
        try {
            Users user = deckCardSvc.findByUserId(userId);
            Deck deck = deckCardSvc.findByDeckId(deckId);
            deckCardSvc.saveCard(front, back, deck, user);
            return "redirect:../../newCard/decks/" + deck.getDeckId();// reloading the current page
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "newCard";
    }


    @GetMapping("/showCards/decks/{deckId}")
    public String studyDeckPage(
        @PathVariable Long deckId, Model model) {
        // Retrieve the selected deck
        Deck deck = deckCardSvc.findByDeckId(deckId);
        
        // Fetch cards associated with the deck
        List<Card> cards = deckCardSvc.showDecksCards(deck);
        // Pass deck and cards to the view
        model.addAttribute("deck", deck);
        model.addAttribute("cards", cards);
        return "showCards";
    }

    @GetMapping("/study/decks/{deckId}")
    public String testDeckPage(
        @PathVariable Long deckId, Model model) {
        // Retrieve the selected deck
        Deck deck = deckCardSvc.findByDeckId(deckId);
     
        // Fetch cards associated with the deck
        List<Card> cards = deckCardSvc.showDecksCards(deck);
        // Pass deck and cards to the view
        model.addAttribute("deck", deck);
        model.addAttribute("cards", cards);
        return "study";
    }





    @GetMapping("/editDeck/decks/{deckId}")
    public String editDeck(
    @PathVariable("userId") Long userId,
    @PathVariable("deckId") Long deckId,
    Model model) {
        log.info("Entering editDeck");
        Users user = deckCardSvc.findByUserId(userId);
        Deck deck = deckCardSvc.findByDeckId(deckId);
        model.addAttribute("deck", deck);
        return "editDeck";
    }

    
    @GetMapping("editDeck/decks/{deckId}/search")
public String searchCard(
    @PathVariable Long userId,
    @PathVariable Long deckId,
    @RequestParam("query") String query,
    Model model) {
    log.info("Entering searchCard");
        Card searchedCard = deckCardSvc.findByFrontStartsWith(query);
        if (searchedCard != null) {
            model.addAttribute("searchedCard", searchedCard);

            Deck deck = deckCardSvc.findByDeckId(deckId);
            model.addAttribute("deck", deck);
            return "editDeck";
        } // card not found
        model.addAttribute("error", "card not found");
        return "editDeck";
    }



    @GetMapping("/home/test/decks/{deckId}") 
    public String test(
    @PathVariable("userId") Long userId,
    @PathVariable("deckId") Long deckId,
    Model model) {
        Users user = deckCardSvc.findByUserId(userId);
        Deck deck = deckCardSvc.findByDeckId(deckId);
        model.addAttribute("user", user);
        model.addAttribute("deck", deck);
        return "test";
    }

    /*
     * @GetMapping("/editDeck")
     * public String editDeckPage(
     * 
     * @RequestParam(name="deckId", required=true) Long deckId,
     * Model model,
     * HttpSession session) {
     * log.info("Entering: editCard");
     * // sets deck session
     * if (deckId != null) {
     * Deck currentDeck = deckCardSvc.findByDeckId(deckId);
     * session.setAttribute("deck", currentDeck);
     * model.addAttribute("deck", currentDeck);
     * 
     * }
     * 
     * return "editDeck";
     * }
     * 
     * 
     * @PostMapping("/editDeck")
     * public String editDeck(
     * 
     * @RequestParam(name="deckId") Long deckId,
     * 
     * @ModelAttribute("search") String search,
     * Model model,
     * HttpSession session) {
     * Deck deck = (Deck) session.getAttribute("deck");
     * List<Card> cards = deckCardSvc.showDecksCards(deck);
     * for (int i = 0; i < cards.size(); i++) {
     * if (cards.get(i).getFront().contains(search)) {
     * Card editCard = cards.get(i);
     * log.info("editCard: {}", editCard);
     * session.setAttribute("editCard", editCard);
     * model.addAttribute("editCard", editCard);
     * return "editDeck";
     * }
     * }
     * model.addAttribute("error", "Card not found");
     * return "editDeck";
     * }
     * 
     */
}