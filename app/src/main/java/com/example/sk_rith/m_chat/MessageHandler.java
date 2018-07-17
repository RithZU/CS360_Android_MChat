package com.example.sk_rith.m_chat;

/**
 * Created by SK_Rith on 7/16/2018.
 */

public class MessageHandler {

    private String messageAuthor;
    private String message;

    public MessageHandler(String messageAuthor, String message) {
        this.messageAuthor = messageAuthor;
        this.message = message;
    }

    public String getMessageAuthor() {
        return messageAuthor;
    }

    public String getMessage() {
        return message;
    }

    public MessageHandler() {
    }
}
