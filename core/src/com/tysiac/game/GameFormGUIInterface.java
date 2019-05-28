package com.tysiac.game;

import com.tysiac.game.defs.Card;
import com.tysiac.game.defs.enums.CARD_LOCATION_TYPE;
import com.tysiac.game.defs.enums.COLOR;

import java.util.ArrayList;
import java.util.HashMap;

public interface GameFormGUIInterface {
    void createBoard(Integer playerNr);

    // Wyświetlenie kart w już istniejących obiektach (chyba ze libgdx będzie wymagało tworzenia ich za kazdym razem)
    void displayCards(HashMap<CARD_LOCATION_TYPE, ArrayList<Card>> cards);

    void displayCards(HashMap<CARD_LOCATION_TYPE, ArrayList<Card>> cards, Boolean withAdditional);

    // Pokazanie aktualnego koloru - atu (trumfa)
    void showTrump(COLOR trump);

    // Pokazanie / schowanie przyciskow akcji (do licytacji, oddawania, kar, dawania po 60 itp)
    void setActionButtonsVisible(boolean visible);

    // Zablokowanie/ odbblokowanie przyciskow akcji
    void setActionButtonsEnabled(boolean enabled);

    // Zablokowanie/ odbblokowanie przycisku akcji o podanym numerze
    void setActionButtonEnabled(Integer buttonNr, boolean enabled);

    // Zablokowanie/ odbblokowanie kart gracza
    void setMyCardsEnabled(boolean enabled);

    //Wyświetlenie komunikatu w odpowiednim miejscu
    void displayMessage(String s);

    //Wyświetlenie rezultatow w odpowiednim miejscu, być może powinna przekazywana inna struktura
    void displayResults(String s);

    // Metoda rysująca/generująca przyciski do aktualnej licitacji, po naciśnięciu na przycisk musi wywolywac się metoda actionButtonsForBidExecuteSelect
    void enableActionButtonsForBid(Integer bid);

    // Metoda rysująca/generująca przyciski do decyzji, czy dawac po 60 , po naciśnięciu na przycisk musi wywolywac się metoda actionButtonsForBombExecuteSelect
    void enableActionButtonsForBomb();

    // Metoda rysująca/generująca przyciski do decyzji, czy dawac po 60 , po naciśnięciu na przycisk musi wywolywac się metoda executor.actionButtonsForCardExchange, po dwukliku na kartę musi się ustawiac odpowiednia karta jako selectedCard
    void EnableCardsAndButtonsForExchange(Integer playerNr);

    // Metoda odblokowująca  karty w celu połozenia ich na stole, po dwukliknięciu (albo innym sposobie wyboru) nalezy wywołać executor.cardsDoubleClicked(card);
    void enableCards(Boolean firstTrick);

}
