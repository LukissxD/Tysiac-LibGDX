package com.tysiac.game;

import com.google.gson.Gson;
import com.tysiac.game.defs.Card;
import com.tysiac.game.defs.Commons;
import com.tysiac.game.defs.Const;
import com.tysiac.game.defs.enums.CARD_LOCATION_TYPE;
import com.tysiac.game.defs.enums.CARD_TYPE;
import com.tysiac.game.defs.enums.COLOR;
import com.tysiac.game.defs.enums.GAME_MESSAGE_TYPE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;



public class GameSingleForm {


//    private GamePlay parentGamePlay;
    HashMap<CARD_LOCATION_TYPE, ArrayList<Card>> cards= new HashMap<>(7);
    Integer playerNr;
    private Integer bid;
    private MessageBroadcastInterface messageBroadcaster;
    GameFormGUIInterface gui;

    Boolean isMaster;
    // variables only for master
    private Integer actualStartingPlayer;
    private Integer actualBid;
    private Integer actualBidWinner;
    private Integer lastTrickWinner;
    private HashMap<Integer,Integer> playerBids;
    HashMap<Integer, Card> actualTrick;
    private HashMap<COLOR, Integer> actualTrumpReported;
    HashMap<Integer, COLOR> actualTrumpReportedInDeal;
    private HashMap<Integer,Integer> actualResults;
    private HashMap<Integer,Integer> actualDealResults;
    COLOR actualTrump;

    GameSingleForm( Integer playerNr, Boolean isMaster) {
//        this.parentGamePlay = parentGamePlay;
        this.playerNr = playerNr;
        this.isMaster = isMaster;
//        this.messageBroadcaster =new QueueMessageBroadcaster(parentGamePlay,playerNr);
        GameFormFromGUIExecutor executor=new GameFormFromGUIExecutor(this);
       // this.gui=new SWTGameFormGUI(display,executor);
    }

    void initializeBoardAndDispatcher(){
        gui.createBoard(playerNr);
        for(CARD_LOCATION_TYPE location: CARD_LOCATION_TYPE.values()){
            cards.put(location,new ArrayList<Card>());
        }

        MessagesDispatcher md=new MessagesDispatcher(this);
        md.start();
    }







    void initDeal(){
        if(isMaster){
            List<Integer> cardNumbers = shuffleCards();
            actualStartingPlayer=(++actualStartingPlayer)%3;
            actualBid= Const.INITIAL_VALUE;
            playerBids= new HashMap<>();
            messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.CARDS_SHUFFLED,new Gson().toJson(cardNumbers));
            messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.FIRST_BID,new Gson().toJson(((actualStartingPlayer+1)%3)));
        }
    }

    void initGame(){
        if(isMaster){
            List<Integer> cardNumbers = shuffleCards();
            actualStartingPlayer =1;
            actualResults=new HashMap<>();
            actualBid= Const.INITIAL_VALUE;
            playerBids= new HashMap<>();
            messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.CARDS_SHUFFLED,new Gson().toJson(cardNumbers));
            messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.FIRST_BID,new Gson().toJson(((actualStartingPlayer+1)%3)));
        }
    }

    void sortCardsByColorsAndTypes(ArrayList<Card> cardList){
        Collections.sort(cardList,new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                if (o1.getColor().compareTo(o2.getColor()) == 0) {
                    return o1.getCardType().compareTo(o2.getCardType());
                } else {
                    return o1.getColor().compareTo(o2.getColor());
                }
            }
        });


    }

    void convertNumbersToCards(List<Integer> cardNumbers){
        ArrayList<Card> cardList;
        for(int p=0;p<3;p++) {
            cardList= new ArrayList<>();
            for (int i = 7 * (p); i < 7 * (p+1); i++) {
                Card card = numberToCard(cardNumbers.get(i));
                cardList.add(card);
            }
            sortCardsByColorsAndTypes(cardList);
            cards.put(playerNrToLocation(p), cardList);
        }
        cardList = new ArrayList<>();
        for (int i = 21; i < 24; i++) {
            Card card = numberToCard(cardNumbers.get(i));
            cardList.add(card);
        }
        cards.put(CARD_LOCATION_TYPE.ADDITIONAL, cardList);
    }


    private List<Integer> shuffleCards() {
        List<Integer> cardNumbers=new ArrayList<Integer>();
        for(int i=0;i<24;i++){
            cardNumbers.add(i);
            /*=IntStream.range(0, 24)
                    .boxed()
                    .collect(Collectors.toList());*/
        }
        Collections.shuffle(cardNumbers);
        return cardNumbers;
    }

    private Card numberToCard(Integer i) {
        return new Card(i);
    }

    void returnBid(int amount) {
        HashMap<String,Integer> returnedBid= new HashMap<>();
        returnedBid.put(Const.NR,playerNr);
        returnedBid.put(Const.BID,amount);
        messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.BID_RETURNED,new Gson().toJson(returnedBid),false,this);
    }

    void enableActioButtonsForBid(Integer player, Integer actualBid) {
        bid=actualBid;
        if(actualBid< Const.INITIAL_VALUE){
            bid= Const.INITIAL_VALUE;
        }
        else{
            bid+= Const.INCREASE_VALUE;
        }

        if(player==playerNr){
            gui.enableActionButtonsForBid(bid);
        }
        else {
            gui.setActionButtonsEnabled(false);
        }
    }

    void actualizeBidding(Integer actualPlayer, Integer lastBid) {
        if (playerBids == null) playerBids = new HashMap<>(3);
        playerBids.put(actualPlayer, lastBid);
        long nrOfBiders=0;
        long nrOfSkippers=0;
        for(Integer vv:playerBids.values()){
            if(vv !=null){
                nrOfBiders++;
            }
        }
        for(Integer vv:playerBids.values()){
            if(vv !=null && vv.equals(Const.SKIP_VALUE)){
                nrOfSkippers++;
            }
        }
        if (nrOfSkippers == 2) {
            endBidding(nrOfBiders == 2);
        } else {
            if (lastBid > actualBid) {
                actualBid = lastBid;
            }
            nextBid(actualPlayer, actualBid);
        }
    }

    private void endBidding(boolean noOffers) {
        HashMap<String,Integer> returnedBiddingResult= new HashMap<>();
        if(noOffers) {
            returnedBiddingResult.put(Const.NR, actualStartingPlayer);
            returnedBiddingResult.put(Const.BID, Const.INITIAL_VALUE);
        }
        else{
            long winner=-1;
            for(Integer vv:playerBids.keySet()){
                if(vv !=null && !playerBids.get(vv).equals(Const.SKIP_VALUE)){
                    winner=vv;
                }
            }
            returnedBiddingResult.put(Const.NR, new Long(winner).intValue());
            returnedBiddingResult.put(Const.BID, actualBid);
        }
        messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.LAST_BID,new Gson().toJson(returnedBiddingResult));
    }

    private void nextBid(Integer actualPlayer, Integer actualBid) {
        HashMap<String,Integer> returnedBid= new HashMap<>();
        returnedBid.put(Const.NR,actualPlayer);
        returnedBid.put(Const.BID,actualBid);
        messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.BID,new Gson().toJson(returnedBid),false,this);
    }



    void checkBomb(Integer player) {
        if(player.equals(playerNr)){
            gui.enableActionButtonsForBomb();
        }
        else {
            gui.setActionButtonsEnabled(false);
        }
    }

    void doBomb() {
        messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.DO_BOMB,new Gson().toJson(this.playerNr));

    }

    void startExchange() {
        messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.START_EXCHANGE,new Gson().toJson(this.playerNr));

    }

    void exchangeCards(Integer actualPlayer) {
        if(actualPlayer.equals(playerNr)){
            gui.setActionButtonsVisible(true);
            gui.setActionButtonsEnabled(true);
            gui.EnableCardsAndButtonsForExchange(playerNr);
        }
        else {
            gui.setActionButtonsVisible(false);
        }
    }

    void exchangeWith(int toPlayer, CARD_LOCATION_TYPE toPlayerLocation, Card selectedCard, Integer buttonNr) {
        if(selectedCard!=null) {
            cards.get(CARD_LOCATION_TYPE.MY_PLAYER).remove(selectedCard);
            cards.get(toPlayerLocation).add(selectedCard);
            gui.displayCards(cards,false);
            ArrayList<Object> al=new ArrayList<>();
            al.add(toPlayer);
            al.add(selectedCard.toInt());
            al.add(cards.get(CARD_LOCATION_TYPE.MY_PLAYER).size()==8);
            al.add(playerNr);
            messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.SEND_EXCHANGED_CARD,new Gson().toJson(al));
            gui.setActionButtonEnabled(buttonNr, false);
            gui.setMyCardsEnabled(true);
        }
    }


    void getAdditionalCardsForPlayer(int player) {
            CARD_LOCATION_TYPE gettingLocation = playerNrToLocation(player);
            ArrayList<Card> currentCards = cards.get(gettingLocation);
            currentCards.addAll(cards.get(CARD_LOCATION_TYPE.ADDITIONAL));
            sortCardsByColorsAndTypes(currentCards);
            cards.put(gettingLocation, currentCards);
//            cards.put(CARD_LOCATION_TYPE.ADDITIONAL, new ArrayList<>());

    }

    void putCardOntable(Boolean firstTrick) {
        gui.enableCards(firstTrick);
    }

    void cardMovedOntoTable(Card card) {
        ArrayList <Integer> al= new ArrayList<>();
        al.add(playerNr);
        al.add(card.toInt());
        if(card.getCardType()== CARD_TYPE.QUEEN ||card.getCardType()== CARD_TYPE.KING){
            Card marriage = new Card(card.getColor(), card.getCardType() == CARD_TYPE.QUEEN ? CARD_TYPE.KING: CARD_TYPE.QUEEN);
            if(cards.get(CARD_LOCATION_TYPE.MY_PLAYER).contains(marriage)) {
                al.add(card.getColor().ordinal());
            }
        }
        messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.CARD_ADDED,new Gson().toJson(al));
    }

    void moveCardOntoTable(Card card){
        //Zoptymalizować
        ArrayList<Card> actualCards = cards.get(CARD_LOCATION_TYPE.MY_PLAYER);
        actualCards.remove(card);
        cards.put(CARD_LOCATION_TYPE.MY_PLAYER,actualCards);
        cards.get(CARD_LOCATION_TYPE.ON_TABLE_MY).add(card);
    }

    CARD_LOCATION_TYPE playerNrToLocation(int i){
//        return CARD_LOCATION_TYPE.values()[(playerNr-i+3)%3];
        int toRet=(i+(3-playerNr)%3)%3;
        System.out.println(" "+playerNr+","+i+"  =  "+toRet );
        return CARD_LOCATION_TYPE.values()[toRet];
    }

    void initTrick(Integer lastBid, Integer actualPlayer, Boolean firstTrick) {
        actualBid = lastBid;
        lastTrickWinner = actualPlayer;
        actualTrick = new HashMap<>();
        actualTrumpReportedInDeal = new HashMap<>();
        if (firstTrick) {
            actualBidWinner = actualPlayer;
            actualTrump = null;
            actualTrumpReported = new HashMap<>();
            actualDealResults = new HashMap<>();
        }
    }

    void finishTrick() {
        ArrayList<Integer> al=new ArrayList<>();
        COLOR newTrump= actualTrumpReportedInDeal.get(lastTrickWinner);
        if(newTrump!=null) {
            int v=Commons.Nz(actualDealResults.get(lastTrickWinner));
            v+= newTrump.reportedValue();
            actualDealResults.put(lastTrickWinner, v);
            actualTrumpReported.put(newTrump,lastTrickWinner);
            actualTrump=newTrump;
        }
        ArrayList<Integer> winnerAl=new ArrayList<>(actualTrick.keySet());
        COLOR winningColor = actualTrump == null ? actualTrick.get(lastTrickWinner).getColor() : actualTrump;
        Integer bestCardValue=-1;
        Integer winner=-1;
        Integer trickSum=0;
        for(Integer k:actualTrick.keySet()){
            trickSum+=actualTrick.get(k).getCardType().valueOf();
            Integer actualCardValue=(actualTrick.get(k).getColor() == winningColor ? 12 : 0) + actualTrick.get(k).getCardType().valueOf();
            if(actualCardValue>bestCardValue){
                bestCardValue=actualCardValue;
                winner=k;
            }
        }
        actualDealResults.put(winner,trickSum);
        al.add(winner);
        if(newTrump!=null) {
            al.add(newTrump.ordinal());
        }
        if(cards.get(CARD_LOCATION_TYPE.MY_PLAYER).size()==0){
            for(int i :actualDealResults.keySet()){
                if(i!=actualBidWinner){
                    int res = Commons.Nz(actualResults.get(i));
                    actualResults.put(i,res+Commons.Nz(actualDealResults.get(i)));
                }else{
                    int res = Commons.Nz(actualResults.get(i));
                    int dealRes=Commons.Nz(actualDealResults.get(i));
                    if(dealRes>=actualBid){
                        actualResults.put(i,res+dealRes);
                    }
                    else{
                        actualResults.put(i,res-dealRes);
                    }
                }
            }
            messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.DISPLAY_RESULTS, Const.ACTUAL_RESULTS +new Gson().toJson(actualResults));
            initDeal();
        }
        else {
            messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.DISPLAY_RESULTS, Const.ACTUAL_DEAL_RESULTS +new Gson().toJson(actualDealResults));
            messageBroadcaster.sendMessage(GAME_MESSAGE_TYPE.START_TRICK, new Gson().toJson(al));
        }

    }



    MessageBroadcastInterface getMessageBroadcaster() {
        return messageBroadcaster;
    }
}

