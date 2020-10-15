import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    public static void main(String[] args){
        System.out.println("****************************************");
        System.out.println("Thread를 이용한 다중 접속 서버 작동됨...");
        System.out.println("****************************************");
        ServerSocket server = null;
        int connectCount=0;
        try {
            server = new ServerSocket(10100);
            while(true){
                Socket connectedClient = server.accept();

                ++connectCount; //접속자수 카운트
                System.out.print("[ "+connectCount+" ]");
                System.out.print(" 서버 IP: "+connectedClient.getLocalAddress()+":"+connectedClient.getLocalPort());
                System.out.println(" 클라이언트 IP: " + connectedClient.getInetAddress().getHostAddress()+":"+connectedClient.getPort());

                //서버 스레드 실행
                ThreadServerHandler handler = new ThreadServerHandler(connectedClient);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch(IOException ignored) {}
        }
    }
}
