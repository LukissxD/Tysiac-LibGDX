package com.tysiac.game;//package com.tysiac.game;
//
//import com.tysiac.game.defs.GameSingleFormDescription;
//import com.tysiac.game.defs.MessageInfo;
//import com.tysiac.game.defs.enums.GAME_MESSAGE_TYPE;
////import org.eclipse.swt.widgets.Display;
////import org.eclipse.swt.widgets.Shell;
//
//import java.util.ArrayList;
//
//
//public class GamePlay {
//
//
////    private Display display;
//    ArrayList<GameSingleFormDescription> gameFormDescriptions = new ArrayList<GameSingleFormDescription>();
//
//
//    protected void init() {
////        display= Display.getDefault();
////        ArrayList<Shell> shells= new ArrayList<Shell>(3);
//        for(int i=0; i<=2; i++) {
//            GameSingleForm gameSingleForm=new GameSingleForm(display,this,i,i==0);
//            GameSingleFormDescription gsfd=new GameSingleFormDescription();
//            gsfd.setForm(gameSingleForm);
//            gsfd.setMaster(i==0);
//            gsfd.setPlayerNr(i);
//            gameFormDescriptions.add(gsfd);
//            gameSingleForm.gui.createBoard(i);
//            Shell shell=((SWTGameFormGUI)gameSingleForm.gui).getShell();
//            gameSingleForm.initializeBoardAndDispatcher();
//            shell.open();
//            shell.pack();
//            if(i!=2) {
//                shell.setLocation(900*i, 101);
//            }
//            else{
//                shell.setLocation(500, 500);
//            }
//            shells.add(shell);
//        }
//        gameFormDescriptions.get(0).getForm().initGame();
//        while (!shells.get(0).isDisposed()  || !shells.get(1).isDisposed()  || !shells.get(2).isDisposed()) {
//            if (!display.readAndDispatch())
//                display.sleep();
//        }
//        display.dispose();
//    }
//    protected void sendMessage(GAME_MESSAGE_TYPE messageType, String toJson) {
//        sendMessage(messageType, toJson,false,null);
//    }
//    protected void sendMessage(GAME_MESSAGE_TYPE messageType, String toJson, Boolean alreadyProcessed, GameSingleForm sender) {
//        for(GameSingleFormDescription gfd:gameFormDescriptions){
//            if(!(alreadyProcessed && gfd.getForm().equals(sender))){
//                MessageInfo messageInfo=new MessageInfo(messageType,toJson,false);
//                gfd.getForm().getMessageBroadcaster().addToQueue(messageInfo);
//            }
//        };
//    }
//
//    public static  void main(String[] args){
//        GamePlay gp=new GamePlay();
//        gp.init();
//    }
//
//
//}
