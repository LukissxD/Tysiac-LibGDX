package com.tysiac.game;//package com.tysiac.game;
//
//import com.tysiac.game.defs.Card;
//import com.tysiac.game.defs.Const;
//import com.tysiac.game.defs.enums.CARD_LOCATION_TYPE;
//import com.tysiac.game.defs.enums.COLOR;
////import org.eclipse.swt.SWT;
////import org.eclipse.swt.events.MouseAdapter;
////import org.eclipse.swt.events.MouseEvent;
////import org.eclipse.swt.events.SelectionAdapter;
////import org.eclipse.swt.events.SelectionEvent;
////import org.eclipse.swt.graphics.Font;
////import org.eclipse.swt.layout.GridData;
////import org.eclipse.swt.layout.GridLayout;
////import org.eclipse.swt.widgets.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Random;
//
//public class SWTGameFormGUI implements  GameFormGUIInterface,GameFormGUIInterface{
//
//    private ArrayList<Button>  actionButtons= new ArrayList<>(2);
//    private ArrayList<Button>  labelButtons=new ArrayList<>(2);;
//    private HashMap<CARD_LOCATION_TYPE, ArrayList<Button>> cardButtons= new HashMap<CARD_LOCATION_TYPE, ArrayList<Button>>(7);
//    private HashMap<Button,Card> buttonToCardsMapping;
//    private Card selectedCard;
//
//    GameFormFromGUIExecutor executor;
//
//    private Shell shell;
//    private Display display;
//
//
//
//    public SWTGameFormGUI(Display display, GameFormFromGUIExecutor executor) {
//        this.display = display;
//        this.executor=executor;
//    }
//
//
//    // Metoda robocza przy SWT - pewnie nie bedzie Wam potrzebna
//    public boolean checkIfDisplayCards(CARD_LOCATION_TYPE location,int i, int j){
//        switch(location){
//            case MY_PLAYER:return (j==6 && i>0 && i<11);
//            case PLAYER_B:return ((j>1 && j<5) && (i<3)) || (j==5 && i==0);
//            case PLAYER_C:return ((j>1 && j<5) && (i>8)) || (j==5 && i==11);
//            case ON_TABLE_MY:return (j==4 && i==5)  ;
//            case ON_TABLE_B:return (j==2 && i==4)   ;
//            case ON_TABLE_C:return (j==3 && i==6)  ;
//            case ADDITIONAL:return (j==0 && i>=4 && i<=6);
//            default:return false;
//        }
//    }
//
//    //Stworzenie obiektów do wyświetlania kart
//    @Override
//    public void createBoard(Integer playerNr){
//        display= Display.getDefault();
//        shell = new Shell(display);
//        shell.setText("Player "+playerNr);
//        GridLayout gl=new org.eclipse.swt.layout.GridLayout(12,true);
//        shell.setLayout(gl);
//
//        Button resultsLabelButton = new Button(shell, SWT.BORDER);
//        GridData gdresults = new GridData();
//        gdresults.horizontalAlignment=SWT.FILL;
//        gdresults.grabExcessHorizontalSpace=true;
//        gdresults.horizontalSpan=12;
//        resultsLabelButton.setLayoutData(gdresults);
//        resultsLabelButton.setText("RESULTS");
//        resultsLabelButton.setAlignment(SWT.CENTER);
//        resultsLabelButton.setEnabled(false);
//        labelButtons.add(resultsLabelButton);
//
//        Button messagesLabelButton =new Button(shell, SWT.BORDER);
//        GridData gdMessages = new GridData();
//        gdMessages.horizontalAlignment=SWT.FILL;
//        gdMessages.grabExcessHorizontalSpace=true;
//        gdMessages.horizontalSpan=12;
//        messagesLabelButton.setLayoutData(gdMessages);
//        messagesLabelButton.setText("Bidding continues");
//        messagesLabelButton.setAlignment(SWT.CENTER);
//        messagesLabelButton.setEnabled(false);
//        labelButtons.add(messagesLabelButton);
//
//        GridData cardButtonsGridData = new GridData();
//        cardButtonsGridData.heightHint=40;
//        cardButtonsGridData.widthHint=60;
//        for(int j=0;j<7;j++) {
//            for (int i = 0; i < 12; i++) {
//                boolean locationFound=false;
//                for(CARD_LOCATION_TYPE location:CARD_LOCATION_TYPE.values()){
//                    if(checkIfDisplayCards(location,i,j)){
//                        ArrayList<Button> cardButtonsForLocation = cardButtons.get(location);
//                        if(cardButtonsForLocation==null){
//                            cardButtonsForLocation=new ArrayList<Button>();
//                        }
//                        Button b=new Button(shell,SWT.BORDER);
//                        b.setFont(new Font(display,"MS Mincho", 14, SWT.BOLD));
//                        b.setEnabled(false);
//                        if(location==CARD_LOCATION_TYPE.ON_TABLE_MY || location==CARD_LOCATION_TYPE.ON_TABLE_B  || location==CARD_LOCATION_TYPE.ON_TABLE_C){
//                            b.setVisible(false);
//                        }
//                        b.setLayoutData(cardButtonsGridData);
//                        cardButtonsForLocation.add(b);
//                        cardButtons.put(location,cardButtonsForLocation);
//                        locationFound=true;
//                        break;
//                    }
//                }
//                if(j==0 && i>7){
//                    locationFound=true;
//                    if (i==8 || i==10) {
//                        Button b = new Button(shell, SWT.BORDER);
//                        b.setFont(new Font(display,"MS Mincho", 11, SWT.BOLD));
//                        GridData actionGridData = new GridData();
//                        actionGridData.horizontalSpan=2;
//                        actionGridData.horizontalAlignment=SWT.FILL;
//                        actionGridData.verticalAlignment=SWT.FILL;
//                        b.setLayoutData(actionGridData);
//                        b.setAlignment(SWT.CENTER);
//                        actionButtons.add(b);
//                    }
//                }
//                if(!locationFound) {
//                    Label l = new Label(shell, SWT.NONE);
//                    l.setLayoutData(cardButtonsGridData);
//                }
//            }
//        }
//    }
//
//    // Wyświetlenie kart w już istniejących obiektach (chyba ze libgdx będzie wymagało tworzenia ich za kazdym razem)
//    @Override
//    public void displayCards(HashMap<CARD_LOCATION_TYPE, ArrayList<Card>> cards){
//        displayCards( cards,false);
//    }
//
//    @Override
//    public void displayCards(HashMap<CARD_LOCATION_TYPE, ArrayList<Card>> cards, Boolean withAdditional){
//
//        display.syncExec(new Runnable() {
//            @Override
//            public void run() {
//                buttonToCardsMapping= new HashMap<Button,Card>(24);
//                for(CARD_LOCATION_TYPE location:CARD_LOCATION_TYPE.values()){
//                    ArrayList<Card> currentCards = cards.get(location);
//                    ArrayList<Button> currentButtons = cardButtons.get(location);
//                    int start=(currentButtons.size()-currentButtons.size())/2;
//                    for(int i=0;i<start;i++){
//                        currentButtons.get(i).setVisible(false);
//                    }
//                    for(int i=start;i<start+currentCards.size();i++){
//                        currentButtons.get(i).setVisible(true);
//                        if(location==CARD_LOCATION_TYPE.MY_PLAYER  || location==CARD_LOCATION_TYPE.ON_TABLE_MY ||
//                                location==CARD_LOCATION_TYPE.ON_TABLE_B|| location==CARD_LOCATION_TYPE.ON_TABLE_C ||
//                                (location==CARD_LOCATION_TYPE.ADDITIONAL && withAdditional)){
//                            currentButtons.get(i+start).setText(currentCards.get(i).getColor().shortCut()+ " "+currentCards.get(i).getCardType().shortCut());
//                        }
//                        else{
//                            currentButtons.get(i+start).setText("\u25C9\u25C9\u25C9");
//                        }
//                        buttonToCardsMapping.put(currentButtons.get(i+start),currentCards.get(i));
//                    }
//                    for(int i=start+currentCards.size();i<currentButtons.size();i++){
//                        currentButtons.get(i).setVisible(false);
//                    }
//                }
//            }
//        });
//
//    }
//
//
//    // Pokazanie aktualnego koloru - atu (trumfa)
//    @Override
//    public void showTrump(COLOR trump) {
//        if(display!=null) {
//            display.syncExec(new Runnable() {
//                @Override
//                public void run() {
//                    actionButtons.get(1).setBackground(display.getSystemColor(SWT.COLOR_DARK_MAGENTA));
//                    actionButtons.get(1).setForeground(display.getSystemColor(SWT.COLOR_DARK_MAGENTA));
//                    actionButtons.get(1).setVisible(true);
//                    actionButtons.get(1).setEnabled(false);
//                    actionButtons.get(1).setText(trump.shortCut());
//                }
//            });
//        }
//    }
//
//    // Pokazanie / schowanie przyciskow akcji (do licytacji, oddawania, kar, dawania po 60 itp)
//    @Override
//    public void setActionButtonsVisible(boolean visible) {
//        display.syncExec(new Runnable() {
//            @Override
//            public void run() {
//                actionButtons.forEach(b -> b.setVisible(visible));
//            }
//        });
//
//    }
//
//
//    // Zablokowanie/ odbblokowanie przyciskow akcji
//    @Override
//    public void setActionButtonsEnabled(boolean enabled) {
//        display.syncExec(new Runnable() {
//            @Override
//            public void run() {
//                actionButtons.forEach(b -> b.setVisible(enabled));
//            }
//        });
//    }
//
//    // Zablokowanie/ odbblokowanie przycisku akcji o podanym numerze
//    @Override
//    public void setActionButtonEnabled(Integer buttonNr, boolean enabled){
//        display.syncExec(new Runnable() {
//            @Override
//            public void run() {
//                actionButtons.get(buttonNr).setEnabled(enabled);
//            }
//        });
//    }
//
//
//    @Override
//    // Zablokowanie/ odbblokowanie kart gracza
//    public void setMyCardsEnabled(boolean enabled) {
//        display.syncExec(new Runnable() {
//            @Override
//            public void run() {
//                cardButtons.get(CARD_LOCATION_TYPE.MY_PLAYER).forEach(b -> b.setEnabled(enabled));
//            }
//        });
//    }
//
//    @Override
//    //Wyświetlenie komunikatu w odpowiednim miejscu
//    public void displayMessage(String s) {
//        display.syncExec(new Runnable() {
//            @Override
//            public void run() {
//                labelButtons.get(0).setText(s);
//                display.readAndDispatch();
//            }
//        });
//    }
//
//    //Wyświetlenie rezultatow w odpowiednim miejscu, być może powinna przekazywana inna struktura
//    @Override
//    public void displayResults(String s) {
//        display.syncExec(new Runnable() {
//            @Override
//            public void run() {
//                labelButtons.get(1).setText(s);
//                display.readAndDispatch();
//            }
//        });
//    }
//
//    // Metoda rysująca/generująca przyciski do aktualnej licitacji, po naciśnięciu na przycisk musi wywolywac się metoda executor.actionButtonsForBidExecuteSelect
//    @Override
//    public void enableActionButtonsForBid(Integer bid) {
//        display.syncExec(new Runnable() {
//            @Override
//            public void run() {
//                for( Button b:actionButtons) {
//                    for (Listener sl : b.getListeners(SWT.Selection)) {
//                        b.removeListener(SWT.Selection, sl);
//                    }
//                }
//                actionButtons.forEach(b -> b.setVisible(true));
//                actionButtons.forEach(b -> b.setEnabled(true));
//                actionButtons.get(0).setText(bid.toString());
//                actionButtons.get(0).setBackground(display.getSystemColor(SWT.COLOR_GREEN));
//                actionButtons.get(0).setForeground(display.getSystemColor(SWT.COLOR_GREEN));
//                actionButtons.get(0).addSelectionListener(new SelectionAdapter() {
//                    @Override
//                    public void widgetSelected(SelectionEvent selectionEvent) {
//                        executor.actionButtonsForBidExecuteSelect(Integer.parseInt(actionButtons.get(0).getText()));
//                    }
//                });
//                actionButtons.get(1).setText(Const.SKIP);
//                actionButtons.get(1).setBackground(display.getSystemColor(SWT.COLOR_GREEN));
//                actionButtons.get(1).setForeground(display.getSystemColor(SWT.COLOR_GREEN));
//                actionButtons.get(1).addSelectionListener(new SelectionAdapter() {
//                    @Override
//                    public void widgetSelected(SelectionEvent selectionEvent) {
//                        executor.actionButtonsForBidExecuteSelect(Const.SKIP_VALUE);
//                    }
//                });
//            }
//        });
//
//    }
//
//
//    // Metoda rysująca/generująca przyciski do decyzji, czy dawac po 60 , po naciśnięciu na przycisk musi wywolywac się metoda executor.actionButtonsForBombExecuteSelect
//    @Override
//    public void enableActionButtonsForBomb() {
//        display.syncExec(new Runnable() {
//            public void run() {
//                for( Button b:actionButtons) {
//                    for (Listener sl : b.getListeners(SWT.Selection)) {
//                        b.removeListener(SWT.Selection, sl);
//                    }
//                }
//                actionButtons.forEach(b -> b.setVisible(true));
//                actionButtons.forEach(b -> b.setEnabled(true));
//                actionButtons.get(0).setText(Const.PLAY);
//                actionButtons.get(0).setForeground(display.getSystemColor(SWT.COLOR_GREEN));
//                actionButtons.get(0).setBackground(display.getSystemColor(SWT.COLOR_GREEN));
//                actionButtons.get(0).addSelectionListener(new SelectionAdapter() {
//                    @Override
//                    public void widgetSelected(SelectionEvent selectionEvent) {
//                        executor.actionButtonsForBombExecuteSelect(0);
//                    }
//                });
//                actionButtons.get(1).setText(Const.BOMB);
//                actionButtons.get(1).setForeground(display.getSystemColor(SWT.COLOR_GREEN));
//                actionButtons.get(1).setBackground(display.getSystemColor(SWT.COLOR_GREEN));
//                actionButtons.get(1).addSelectionListener(new SelectionAdapter() {
//                    @Override
//                    public void widgetSelected(SelectionEvent selectionEvent) {
//                        executor.actionButtonsForBombExecuteSelect(1);
//                    }
//                });
//            }
//        });
//
//    }
//
//    @Override
//    // Metoda rysująca/generująca przyciski do decyzji, czy dawac po 60 , po naciśnięciu na przycisk musi wywolywac się metoda executor.actionButtonsForCardExchange, po dwukliku na kartę musi się ustawiac odpowiednia karta jako selectedCard
//    public void EnableCardsAndButtonsForExchange(Integer playerNr) {
//        display.syncExec(new Runnable() {
//            @Override
//            public void run() {
//                for( Button b:actionButtons) {
//                    for (Listener sl : b.getListeners(SWT.Selection)) {
//                        b.removeListener(SWT.Selection, sl);
//                    }
//                }
//                for( Button b:cardButtons.get(CARD_LOCATION_TYPE.MY_PLAYER)) {
//                    for (Listener sl : b.getListeners(SWT.Selection)) {
//                        b.removeListener(SWT.Selection, sl);
//                    }
//                    b.addSelectionListener(new SelectionAdapter() {
//                        @Override
//                        public void widgetSelected(SelectionEvent selectionEvent) {
//                            Button me = (Button) selectionEvent.widget;
//                            selectedCard = buttonToCardsMapping.get(me);
//                            for( Button b:cardButtons.get(CARD_LOCATION_TYPE.MY_PLAYER)) {
//                                b.setBackground(null);
//                                b.setForeground(null);
//                            }
//                            ((Button) selectionEvent.widget).setBackground(display.getSystemColor(SWT.COLOR_BLUE));
//                            ((Button) selectionEvent.widget).setForeground(display.getSystemColor(SWT.COLOR_BLUE));
//                        }
//                    });
//                    b.setEnabled(true);
//                }
//                selectedCard=null;
//                actionButtons.forEach(b -> b.setEnabled(true));
//                actionButtons.get(0).setText(Const.CARD_TO_LEFT);
//                actionButtons.get(0).setForeground(display.getSystemColor(SWT.COLOR_GREEN));
//                actionButtons.get(0).setBackground(display.getSystemColor(SWT.COLOR_GREEN));
//                actionButtons.get(0).addSelectionListener(new SelectionAdapter() {
//                    @Override
//                    public void widgetSelected(SelectionEvent selectionEvent) {
//                            executor.actionButtonsForCardExchangeExecuteSelect((playerNr + 1) % 3, CARD_LOCATION_TYPE.PLAYER_B,selectedCard,0);
//                    }
//                });
//                actionButtons.get(1).setText(Const.CARD_TO_RIGHT);
//                actionButtons.get(1).setBackground(display.getSystemColor(SWT.COLOR_GREEN));
//                actionButtons.get(1).setForeground(display.getSystemColor(SWT.COLOR_GREEN));
//                actionButtons.get(1).addSelectionListener(new SelectionAdapter() {
//                    @Override
//                    public void widgetSelected(SelectionEvent selectionEvent) {
//                        executor.actionButtonsForCardExchangeExecuteSelect((playerNr+2)%3,CARD_LOCATION_TYPE.PLAYER_C ,selectedCard,1);
//                    }
//                });
//            }
//        });
//
//    }
//
//    @Override
//    // Metoda odblokowująca  karty w celu połozenia ich na stole, po dwukliknięciu (albo innym sposobie wyboru) nalezy wywołać executor.cardsDoubleClicked(card);
//    public void enableCards(Boolean firstTrick) {
//        display.syncExec(new Runnable() {
//            @Override
//            public void run() {
//                if(firstTrick) {
//                    setActionButtonsVisible(false);
//                }
//                displayMessage(Const.DOUBLE_CLICK_TO_PUT_CARD);
//                for (Button b : cardButtons.get(CARD_LOCATION_TYPE.MY_PLAYER)) {
//                    b.setBackground(null);
//                    b.setForeground(null);
//                    b.setEnabled(true);
//                    if(firstTrick) {
//                        for (Listener sl : b.getListeners(SWT.Selection)) {
//                            b.removeListener(SWT.Selection, sl);
//                        }
//                        b.addMouseListener(new MouseAdapter() {
//                            @Override
//                            public void mouseDoubleClick(MouseEvent mouseEvent) {
//                                Card card = buttonToCardsMapping.get((Button) mouseEvent.widget);
//                                executor.cardsDoubleClicked(card);
//                            }
//                        });
//                    }
//                }
//            }
//        });
//
//    }
//
//
//    // W wersji libgdx - niepotrzebne
//    public Shell getShell() {
//        return shell;
//    }
//}
