import java.io.*;
import java.net.Socket;

public class ThreadServerHandler extends Thread {
    private Socket connectedClientSocket;

    public ThreadServerHandler(Socket connectedClientSocket) {
        this.connectedClientSocket = connectedClientSocket;
    }

    public void startThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //서버가 보낸 내용
                    InputStream is = connectedClientSocket.getInputStream();
                    DataInputStream ds = new DataInputStream(is);
                    //길이를 먼저 받고 내용을 받음
                    String receiveText = ds.readUTF();

                    //서버가 보내는 내용
                    OutputStream os = connectedClientSocket.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(os);
                    String sendText = "서버입니다. 수발신에 스레드 : " + Thread.currentThread().getName();
                    System.out.println("수신 내용 : " + receiveText+" 발신 내용 : " + sendText);
                    //길이를 먼저 보내고 내용을 보냄
                    dos.writeUTF(sendText);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        System.out.println("클라이언트 종료");
                        System.out.println();
                        connectedClientSocket.close(); // 클라이언트 접속 종료
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        TcpServer.executorService.submit(runnable);
    }
}