package com.chat;

public class NoServerRunningException extends Exception {

    public NoServerRunningException() {
        System.out.println("not able to close server, because there is no running server!");
    }
}
