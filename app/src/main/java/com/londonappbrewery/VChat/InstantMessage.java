package com.londonappbrewery.VChat;

/**
 * Created by SubhasishNath on 8/4/2018.
 * this is the model class
 */

public class InstantMessage {

    private String message;
    private String author;

    public InstantMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public InstantMessage() {

    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
