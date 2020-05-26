package com.chat;

import java.time.LocalTime;

public class Message {
    private final String TEXT;
    private int FROM_PORT;
    private final String USERNAME;
    private final LocalTime TIME;

    public enum messageType {WELCOME, REGULAR}
    private final messageType TYPE;

    public Message(int uid, messageType type, String userName, String text) {
        FROM_PORT = uid;
        TYPE = type;
        TIME = LocalTime.now().withNano(0);
        USERNAME = userName;
        TEXT = text;
    }

    public int getPort() { return FROM_PORT; }
    public messageType getType() { return TYPE; }
    public LocalTime getTime() { return TIME; }
    public String getUsername() { return USERNAME; }
    public String getText() { return TEXT; }

    public void serializable() {
        //TODO: save to file when app closed
    }

    public void setPort(int port) { this.FROM_PORT = port; }
}
