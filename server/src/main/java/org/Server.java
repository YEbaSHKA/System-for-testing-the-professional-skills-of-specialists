package org;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    protected int server_port = 8002;
    protected ServerSocket server_socket = null;
    protected boolean is_stopped = false;

    public Server(int port){
        this.server_port = port;
    }

    @Override
    public void run(){
        open_server_socket();
        while(! is_stopped()){
            Socket client_socket = null;
            try {
                client_socket = this.server_socket.accept();
            } catch (IOException e) {
                if(is_stopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            new Thread(new ServerWorker(client_socket)).start();
            System.out.println("Client connected.");
        }
        System.out.println("Server Stopped.") ;
    }


    private synchronized boolean is_stopped() {
        return this.is_stopped;
    }

    public synchronized void stop() {
        System.out.println("Stopping Server");
        this.is_stopped = true;
        try {
            this.server_socket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void open_server_socket() {
        System.out.println("Opening server socket...");
        try {
            this.server_socket = new ServerSocket(this.server_port);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.server_port, e);
        }
    }
}
