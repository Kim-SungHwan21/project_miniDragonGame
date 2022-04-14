package com.nhnacademy.gameserver;

import com.nhnacademy.gamedomain.BossStage;
import com.nhnacademy.gamedomain.DungeonEnter;
import com.nhnacademy.gamedomain.Enter;
import com.nhnacademy.gamedomain.Soldier;
import com.nhnacademy.gamedomain.StageOne;
import com.nhnacademy.gamedomain.StageTwo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Some javadoc.
 */

@SuppressWarnings("all")
public class MyGameServer {
    private final ConcurrentHashMap<String, DataOutputStream> clientOutMap
        = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        MyGameServer server = new MyGameServer();
        server.start();
    }


    /**
     * Some javadoc.
     */

    public void start() throws IOException {
        boolean test = true;
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println(getTime() + " Start server " + serverSocket.getLocalSocketAddress());


            do {
                try {
                    Socket socket = serverSocket.accept();
                    MyGameServer.ClientSession client = new MyGameServer.ClientSession(socket);
                    client.start();
                    test = client.isConnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    //접속 실패
                }
            } while (test);
        }
    }

    private String getTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
    }

    private void joinChat(ClientSession session) {
        clientOutMap.put(session.id, session.out);

        sendToAll("[System] " + session.id + "님이 입장했습니다.");
        System.out.println(getTime() + " " + session.id
            + " is joined: " + session.socket.getInetAddress());
        loggingCurrentClientCount();
    }


    private void joinDungeon(ClientSession session) {
        clientOutMap.put(session.id, session.out);

        sendToAll("용사 {" + session.id + "}님 던전에 있는 드래곤을 물리쳐주세요!!!");

    }

    private void clearDungeon(ClientSession session) {
        clientOutMap.put(session.id, session.out);

        sendToAll("[외침] 용사 {" + session.id + "}가 드래곤을 물리쳤다!");

    }





    private void sendToAll(String select) {
        for (DataOutputStream out : clientOutMap.values()) {
            try {
                out.writeUTF(select);
            } catch (IOException e) {
                // 해당 클라이언트로 송출 스트림이 실패함(네트워크 끈김)
            }
        }
    }



    private void loggingCurrentClientCount() {
        System.out.println(getTime() + " Currently "
            + clientOutMap.size() + " clients are connected.");
    }




    private void leaveChat(ClientSession session) {
        clientOutMap.remove(session.id);

        sendToAll("[System] " + session.id + "님이 나갔습니다.");
        System.out.println(getTime() + " " + session.id + " is leaved: "
            + session.socket.getInetAddress());
        loggingCurrentClientCount();
    }








    class ClientSession extends Thread {
        private final Socket socket;
        private final DataInputStream in;
        private final DataOutputStream out;
        protected String id;


        private String select;
        Enter et = new Enter();
        DungeonEnter de = new DungeonEnter();
        StageOne so = new StageOne();
        Soldier sd = new Soldier();
        StageTwo st = new StageTwo();
        BossStage bs = new BossStage();




        ClientSession(Socket socket) throws IOException {
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());




        }

        @Override
        public void run() {
            initialize();
            dungeonEnter();
        }

        private void bossStage() {
            try {
                out.writeUTF(bs.toString());
                this.select = in.readUTF();
                if (this.select.equals("1")) {
                    bs.oneButton(out);
                    clearDungeon(this);
                } else if (this.select.equals("2")) {
                    disconnect();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Thread.currentThread().interrupt();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        private void stageTwo() {
            try {
                out.writeUTF(st.toString());
                this.select = in.readUTF();
                if (this.select.equals("1")) {
                    st.oneButton(out);
                    this.select = in.readUTF();
                    bossStage();
                } else if (this.select.equals("2")) {
                    disconnect();
                }
            } catch (IOException cause) {
                cause.printStackTrace();
            }
        }

        private void dungeonEnter() {
            try {
                out.writeUTF(de.toString());
                this.select = in.readUTF();
                if (this.select.equals("1")) {
                    stageOne();
                } else if (this.select.equals("2")) {
                    disconnect();
                }
            } catch (IOException cause) {
                cause.printStackTrace();
            }
        }




        private void stageOne() {
            try {
                out.writeUTF(sd.toString());
                out.writeUTF(so.toString());
                this.select = in.readUTF();
                if (this.select.equals("1")) {
                    so.oneButton(out);
                    this.select = in.readUTF();
                    stageTwo();
                } else if (this.select.equals("2")) {
                    disconnect();
                }
            } catch (IOException cause) {
                cause.printStackTrace();
            }
        }

        private void initialize() {
            try {
                this.id = in.readUTF();
                joinChat(this);
                out.writeUTF(et.toString());
                this.select = in.readUTF();
                joinDungeon(this);
            } catch (IOException cause) {
                // 최초 통신(아이디 받기)이 실패하는 경우
            }
        }


        private boolean isConnect() {
            return this.in != null;
        }

        protected void disconnect() {
            leaveChat(this);
        }

    }







}




