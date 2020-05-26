package com.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static ServerSocket serverSocket;
    private int serverPort;
    private List<ClientThread> clients;

    public static void main(String[] args){
        Server server = new Server(4444);
        server.startServer();
    }

    public Server(int portNumber){
        this.serverPort = portNumber;
        this.clients = new ArrayList<>();
        startServer();
    }

    public void stop() throws IOException {
        for (ClientThread clientThread : clients) {
            clientThread.getSocket().close();
        }
        serverSocket.close();
    }

    public void putClient(ClientThread client) {
        clients.add(client);
    }

    public int getPortNumber() { return serverPort; }
    public List<ClientThread> getClients(){
        return clients;
    }

    private void startServer(){
        try {
            serverSocket = new ServerSocket(serverPort);
            acceptClients();
        } catch (IOException e){
            System.err.println("Could not listen on port: " + serverPort);
            System.exit(1);
        }
    }

    private void acceptClients(){
        System.out.println("server starts port = " + serverSocket.getLocalSocketAddress());
        AcceptClients ac = new AcceptClients(this, serverSocket);
    }
}
