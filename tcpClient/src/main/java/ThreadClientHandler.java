import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class ThreadClientHandler extends Thread{
    public Logger log = LoggerFactory.getLogger(this.getClass());

    public void init(String ip, int port) {
        System.out.println("****************************************");
        System.out.println("클라이언트 서버가 작동됨");
        System.out.println("****************************************");

        Socket socket = null;

        try {
            String serverIP = ip;
            int serverPort = port;

            socket = new Socket(serverIP, serverPort);

            //클라이언트가 보내는 내용
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            String sendText = "클라이언트입니다.";
            //길이를 먼저 보내고 내용을 보냄
            dos.writeUTF(sendText);

            //서버가 보낸 내용
            InputStream is = socket.getInputStream();
            DataInputStream ds = new DataInputStream(is);
            //길이를 먼저 받고 내용을 받음
            String receiveText = ds.readUTF();
            System.out.println("클라이언트 접속-Local Port: "+ socket.getLocalPort()+" Server IP: " + socket.getInetAddress().getHostAddress()+
                    "발신 내용 : " + sendText+"수신 내용 : " + receiveText);

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("****************************************");
                System.out.println("서버와의 접속을 종료합니다.");
                System.out.println("****************************************");
                System.out.println();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

    }
}
