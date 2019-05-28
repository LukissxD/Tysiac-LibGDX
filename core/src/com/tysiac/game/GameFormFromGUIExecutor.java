package com.tysiac.game;

import com.tysiac.game.defs.Card;
import com.tysiac.game.defs.enums.CARD_LOCATION_TYPE;

public class GameFormFromGUIExecutor {

    private GameSingleForm gameForm;

    public GameFormFromGUIExecutor(GameSingleForm gameSingleForm) {
        this.gameForm =gameSingleForm;
    }


    protected void actionButtonsForBidExecuteSelect(Integer amount){
        returnBid(amount );
    }

    private void returnBid(int amount) {
        gameForm.returnBid(amount);
    }


    protected void actionButtonsForBombExecuteSelect(Integer decision){
        if(decision==0) {
            startExchange();
        }
        if(decision==1) {
            doBomb();
        }
    }


    private void doBomb() {
        gameForm.doBomb();
    }

    private void startExchange() {
        gameForm.startExchange();
    }

    protected void actionButtonsForCardExchangeExecuteSelect(Integer player, CARD_LOCATION_TYPE location, Card selectedCard, Integer buttonNr) {
        if (selectedCard != null) {
            exchangeWith(player, location, selectedCard,buttonNr);
        }
    }

    protected void exchangeWith(Integer player, CARD_LOCATION_TYPE location, Card selectedCard, Integer buttonNr) {
        gameForm.exchangeWith(player,location,selectedCard,buttonNr);
    }

    protected void cardsDoubleClicked(Card card) {
        moveCardOntoTable(card);
        gameForm.gui.displayCards(gameForm.cards);
        gameForm.gui.setMyCardsEnabled(false);
        cardMovedOntoTable(card);
    }


    public void cardMovedOntoTable(Card card) {
        gameForm.cardMovedOntoTable(card);
    }

    public void moveCardOntoTable(Card card) {
        gameForm.moveCardOntoTable(card);
    }


}
