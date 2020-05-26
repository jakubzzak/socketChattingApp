package com.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


public class ServerThread implements Runnable {
    private Socket socket;
    private String userName;
    private boolean isAlive;
    private final LinkedList<Message> messagesToSend;
    private boolean hasMessages = false;
    private ChatController chatController;

    public ServerThread(Socket socket, String userName, ChatController chatController){
        this.socket = socket;
        this.userName = userName;
        messagesToSend = new LinkedList<>();
        this.chatController = chatController;
    }

    public int getLocalPort() { return socket.getLocalPort(); }

    public void addNextMessage(Message message){
        synchronized (messagesToSend){
            hasMessages = true;
            messagesToSend.push(message);
        }
    }

    @Override
    public void run(){
        chatController.loadNewMessage(new Message(-1,  Message.messageType.WELCOME, "server", "Welcome " + userName));

        try{
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);

            while(!socket.isClosed()) {
                if(serverInStream.available() > 0) {
                    if(serverIn.hasNextLine()) {
                        String[] msg = serverIn.nextLine().split("<#/=");
                        try{
                            Message message = new Message(Integer.parseInt(msg[0]), Message.messageType.REGULAR, msg[1], msg[2]);
                            if (message.getPort() != getLocalPort()) {
                                chatController.loadNewMessage(message);
                            }
                        } catch (Exception e) {
                            System.out.println("msg: " + Arrays.toString(msg));
                            System.out.println("pruser pri parsovani message -> " + e);
                        }
                    }
                }

                if(hasMessages) {
                    Message m;
                    synchronized (messagesToSend) {
                        m = messagesToSend.pop();
                        hasMessages = !messagesToSend.isEmpty();
                    }
                    chatController.loadNewMessage(m);
                    if (m.getPort() != -1) { // server = -1
                        serverOut.println(socket.getLocalPort() + "<#/=" + m.getUsername() + "<#/=" + m.getText());
                        serverOut.flush();
                    }
                }
            }
            serverInStream.close();
            serverOut.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

    }
}
