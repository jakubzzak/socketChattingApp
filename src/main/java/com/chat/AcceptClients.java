package com.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptClients extends Thread {
    private final ServerSocket serverSocket;
    private final Server server;

    public AcceptClients(Server server, ServerSocket serverSocket) {
        this.server = server;
        this.serverSocket = serverSocket;
        start();
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try{
                Socket socket = serverSocket.accept();
                System.out.println("accepts : " + socket.getRemoteSocketAddress());
                ClientThread client = new ClientThread(server, socket);
                Thread thread = new Thread(client);
                thread.start();
                server.putClient(client);
            } catch (IOException ex){
                System.out.println("Accept failed on : " + server.getPortNumber());
            }
        }
        System.out.println("thread killed");
    }
}
