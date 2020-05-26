package com.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;


public class Client extends Thread {
    private int UID;
    private String username;
    private String host;
    private int portNumber;
    private ServerThread serverThread;
    private boolean connected = true;
    private ChatController chatController;

    private LinkedList<Message> messages = new LinkedList<>();

    public Client() {
        this.UID = -999;
        this.username = "";
        this.host = "localhost";
        this.portNumber = -999;
    }

    public int getUID() { return UID; }
    public int getPortNumber() { return portNumber; }
    public String getUsername() { return username; }
    public String getHost() { return host; }
    public int getLocalPort() { return serverThread.getLocalPort(); }

    public void setUID(int uid) { UID = uid; }
    public void setPortNumber(int port) { portNumber = port; }
    public void setUsername(String name) { this.username = name; }
    public void setHost(String host) { this.host = host; }
    public void setChatController(ChatController chat) { chatController = chat; }

    public void send(String msg) {
        messages.push(new Message(getLocalPort(), Message.messageType.REGULAR, username, msg));
        serverThread.addNextMessage(messages.peek());
    }

    public void startClient() throws IOException {
        try{
            Socket socket = new Socket(InetAddress.getByName(getHost()), getPortNumber());
            Thread.sleep(1000); // waiting for network communicating.
            System.out.println("client socket: " + socket);
            serverThread = new ServerThread(socket, getUsername(), chatController);
            new Thread(serverThread).start();
        } catch(InterruptedException ex){
            System.out.println("Interrupted");
        }
    }

}
