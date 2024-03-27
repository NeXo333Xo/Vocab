package com.thowl.vocabulary.controller;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.thowl.vocabulary.entity.Card;
import com.thowl.vocabulary.entity.Deck;
import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.exception.UserException;
import com.thowl.vocabulary.service.DeckAndCardService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller class responsible for handling requests related to decks and cards
 * for a specific user's home page.
 */
@Controller
@RequestMapping("/api/users/{userId}/home") // Base URL for all mappings in this controller
@Slf4j
public class DeckAndCardController {

    @Autowired
    DeckAndCardService deckCardSvc;

    /**
     * Handles a GET request for the home page, showing the user's decks.
     * 
     * @param userId The ID of the user whose decks are to be displayed.
     * @param model  The model to add attributes for the view.
     * @return home (decks and users options to study, show cards, adding cards,
     *         editing deck..)
     */
    @GetMapping("")
    public String homePage(
            @PathVariable("userId") Long userId,
            Model model) {
        log.info("Entering homePage");
        Users user = deckCardSvc.findByUserId(userId);
        List<Deck> decks = deckCardSvc.showUsersDecks(user);
        model.addAttribute("decks", decks);
        return "home";
    }

    /**
     * Handles a GET request for creating a new deck, showing the form for creating
     * a new deck.
     * 
     * @return The view for the new deck form.
     */
    @GetMapping("/newDeck")
    public String newDeckPage() {
        log.info("Entering newDeck");
        return "newDeck";
    }

    /**
     * Handles a POST request for creating a new deck.
     * 
     * @param deckName The name of the new deck to be created.
     * @param userId   The ID of the user creating the deck.
     * @param model    The model to add attributes for the view.
     * @return if error occurs then just return the page again with added error
     *         message
     *         and otherwise redirect to the newcard page
     */
    @PostMapping("/newDeck")
    public String createNewDeck(
            @RequestParam("deckName") String deckName,
            @PathVariable("userId") Long userId,
            Model model) {
        log.info("Entering createNewDeck");
        if (deckName.isEmpty()) {
            model.addAttribute("error", "No deck name given");
            return "newDeck";
        }
        try {
            Users user = deckCardSvc.findByUserId(userId);
            Deck deck = deckCardSvc.saveDeck(deckName, user);
            // after saving the new created deck: Continue with adding cards for it!
            return "redirect:newCard/decks/" + deck.getDeckId();
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "newDeck";
        }
    }

    /**
     * Handles a GET request for displaying the page to add a new card to a specific
     * deck.
     * 
     * @param deckId The ID of the deck to which the new card will be added.
     * @param model  The model to add attributes for the view.
     * @return The view name for the page to add a new card.
     */
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

    /**
     * Handles a POST request to create a new card for a specific deck.
     * 
     * @param userId The ID of the user creating the card.
     * @param deckId The ID of the deck for which the card is being created.
     * @param front  The front side content of the card.
     * @param back   The back side content of the card.
     * @param model  The model to add attributes for the view.
     * @return The view name for displaying the page to add a new card, either with
     *         an error message or after successfully creating the card.
     */
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
            return "redirect:../../newCard/decks/" + deckId;// reloading the current page
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "newCard";
    }

    /**
     * Handles a GET request to display the cards of a specific deck.
     * 
     * @param deckId The ID of the deck for which the cards are being displayed.
     * @param model  The model to add attributes for the view.
     * @return shows all cards inside the deck
     */
    @GetMapping("/showCards/decks/{deckId}")
    public String showDeck(
            @PathVariable Long deckId,
            Model model) {
        // Retrieve the selected deck
        Deck deck = deckCardSvc.findByDeckId(deckId);
        // Fetch cards associated with the deck
        List<Card> cards = deckCardSvc.showDecksCards(deck);
        // Pass deck and cards to the view
        model.addAttribute("deck", deck);
        model.addAttribute("cards", cards);
        return "showCards";
    }

    /**
     * Handles a GET request to display the cards of a specific deck for studying.
     * 
     * @param deckId The ID of the deck for which the cards are being displayed.
     * @param model  The model to add attributes for the view.
     * @return all cards in the deck in a mixed order for the user to learn
     */
    @GetMapping("/study/decks/{deckId}")
    public String studyDeck(
            @PathVariable Long deckId,
            Model model) {
        // Retrieve the selected deck
        Deck deck = deckCardSvc.findByDeckId(deckId);
        // Fetch cards associated with the deck
        List<Card> cards = deckCardSvc.showDecksCards(deck);
        Collections.shuffle(cards);
        // Pass deck and cards to the view
        model.addAttribute("deck", deck);
        model.addAttribute("cards", cards);
        return "study";
    }

    /**
     * Handles a GET request to edit a specific deck.
     * 
     * @param userId The ID of the user who owns the deck.
     * @param deckId The ID of the deck to be edited.
     * @param model  The model to add attributes for the view.
     * @return the editdeck
     */
    @GetMapping("/editDeck/decks/{deckId}")
    public String editDeck(
            @PathVariable("deckId") Long deckId,
            Model model) {
        log.info("Entering editDeck");
        Deck deck = deckCardSvc.findByDeckId(deckId);
        model.addAttribute("deck", deck);
        return "editDeck";
    }

    // function to edit the card and at to database not implemeted!
    /**
     * Handles a GET request to search for a card in a specific deck.
     * 
     * @param userId The ID of the user who owns the deck.
     * @param deckId The ID of the deck to search within.
     * @param query  The query string used to search for the card.
     * @param model  The model to add attributes for the view.
     * @return The view name for editing the deck, either with the searched card or
     *         an error message.
     */
    @GetMapping("editDeck/decks/{deckId}/search")
    public String searchCard(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @RequestParam("query") String query,
            Model model) {
        log.info("Entering searchCard");
        // searhes for the card that starts with the front
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

    // doesnt functions properly
    /**
     * Handles a DELETE request to delete a card from a specific deck.
     * 
     * @param deckId The ID of the deck from which to delete the card.
     * @param cardId The ID of the card to be deleted.
     * @param model  The model to add attributes for the view.
     */
    @DeleteMapping("editDeck/decks/{deckId}/search/cards/{cardId}/delete")
    public void deleteCard(
            @PathVariable Long deckId,
            @PathVariable Long cardId,
            Model model) {
        log.info("Entering deleteCard with card: {}", cardId);
        deckCardSvc.removeByCardId(cardId);

        Deck deck = deckCardSvc.findByDeckId(deckId);
        model.addAttribute("deck", deck);
        model.addAttribute("deleted", cardId);
    }
}