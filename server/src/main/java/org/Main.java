package org;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(8002);
        new Thread(server).start();
    }
}
