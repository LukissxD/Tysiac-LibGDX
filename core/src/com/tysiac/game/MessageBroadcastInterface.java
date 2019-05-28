package com.tysiac.game;

import com.tysiac.game.defs.MessageInfo;
import com.tysiac.game.defs.enums.GAME_MESSAGE_TYPE;

import java.util.Queue;

public interface MessageBroadcastInterface {
    void sendMessage(GAME_MESSAGE_TYPE message, String toJson);
    void sendMessage(GAME_MESSAGE_TYPE messageType, String toJson, Boolean alreadyProcessed, GameSingleForm sender);
    Queue<MessageInfo> avaitingMessagesInQueue();
    void addToQueue(MessageInfo messageInfo);
}
