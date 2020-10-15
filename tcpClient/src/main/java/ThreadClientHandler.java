import java.io.*;
import java.net.Socket;

public class ThreadClientHandler extends Thread{
    public void init(String ip, int port, int index) {
        System.out.println("****************************************");
        System.out.println("클라이언트 서버가 작동됨");
        System.out.println("****************************************");

        Socket socket = null;

        try {
            String serverIP = ip;
            int serverPort = port;

            socket = new Socket(serverIP, serverPort);
            System.out.print("클라이언트 접속-Local Port: "+ socket.getLocalPort());
            System.out.println(" Server IP: " + socket.getInetAddress().getHostAddress());

            //클라이언트가 보내는 내용
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            String sendText = index+"번 클라이언트입니다.";
            System.out.println("발신 내용 : " + sendText);
            //길이를 먼저 보내고 내용을 보냄
            dos.writeUTF(sendText);

            //서버가 보낸 내용
            InputStream is = socket.getInputStream();
            DataInputStream ds = new DataInputStream(is);
            //길이를 먼저 받고 내용을 받음
            String receiveText = ds.readUTF();
            System.out.println("수신 내용 : " + receiveText);

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
