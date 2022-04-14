package com.nhnacademy.gameclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Some javadoc.
 */

public class MyGameClient {
    private final String id;

    public MyGameClient(String id) {
        this.id = id;
    }

    /**
     * Some javadoc.
     */

    public static void main(String[] args) {
        if (hasNotArgs(args)) {
            System.out.println("USAGE: java MyChatClient {id}");
            return;
        }
        String id = args[0];
        MyGameClient client = new MyGameClient(id);
        client.connect("127.0.0.1", 8888);
    }

    private static boolean hasNotArgs(String[] args) {
        return args.length == 0;
    }

    private void connect(String serverHost, int port) {
        try {
            Socket socket = new Socket(serverHost, port);
            System.out.println("Connected to server " + serverHost + ":" + port);
            Thread sender = new Sender(socket, id);
            Thread receiver = new Receiver(socket);

            sender.start();
            receiver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @SuppressWarnings("all")
    private static class Sender extends Thread {
        private final String id;
        String select;
        private final DataOutputStream out;

        private Sender(Socket socket, String id) throws IOException {
            this.id = id;
            this.out = new DataOutputStream(socket.getOutputStream());
        }

        @Override
        public void run() {
            try {
                initialize();
                sendSelect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void initialize() throws IOException {
            //맨처음에 서버에 아이디를 보낸다.
            if (isSendable()) {
                this.out.writeUTF(id);
            }
        }

        private boolean isSendable() {
            return this.out != null;
        }

        private void sendSelect() throws IOException {

            try (Scanner scanner = new Scanner(System.in)) {
                while (isSendable()) {
                    sleep(1000);
                    this.out.writeUTF(this.select = scanner.nextLine());
                    if (this.select.equals("2")) {
                        System.exit(0);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }



    }




    private static class Receiver extends Thread {
        private final DataInputStream in;

        private Receiver(Socket socket) throws IOException {
            this.in = new DataInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            while (isReceivable()) {
                receiveMessage();

            }
        }

        private boolean isReceivable() {
            return this.in != null;
        }

        private void receiveMessage() {
            try {
                System.out.println(in.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }









}
