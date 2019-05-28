package com.tysiac.game;//package com.tysiac.game;
//
//import com.tysiac.game.defs.MessageInfo;
//import com.tysiac.game.defs.enums.GAME_MESSAGE_TYPE;
//
//import java.util.ArrayDeque;
//import java.util.Queue;
//import java.util.stream.Collectors;
//
//public class QueueMessageBroadcaster implements MessageBroadcastInterface {
//
//    Queue<MessageInfo> messages=new ArrayDeque<MessageInfo>();
//
//    GamePlay game;
//    Integer playerNr;
//
//    public QueueMessageBroadcaster(GamePlay game, Integer playerNr) {
//        this.game = game;
//        this.playerNr = playerNr;
//    }
//
//    @Override
//    public void sendMessage(GAME_MESSAGE_TYPE message, String toJson) {
//        game.sendMessage( message, toJson);
//
//    }
//
//    @Override
//    public void sendMessage(GAME_MESSAGE_TYPE messageType, String toJson, Boolean alreadyProcessed, GameSingleForm sender) {
//        game.sendMessage(messageType, toJson,alreadyProcessed,sender);
//
//    }
//
//    @Override
//    public Queue<MessageInfo> avaitingMessagesInQueue() {
//        return messages.stream().filter(p->p.getProcessed()!=null && !( p.getProcessed(playerNr)!=null &&  p.getProcessed(playerNr))  ).collect(Collectors.toCollection(ArrayDeque::new));
//    }
//
//    public void addToQueue(MessageInfo messageInfo) {
//        messages.add(messageInfo);
//    }
//
//
//    public Queue<MessageInfo> getMessages() {
//        return messages;
//    }
//
//    public void setMessages(Queue<MessageInfo> messages) {
//        this.messages = messages;
//    }
//}
