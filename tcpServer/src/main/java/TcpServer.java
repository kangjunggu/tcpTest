import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer {
    static ServerSocket server = null;
    static ExecutorService executorService; // 스레드풀
    static int connectCount = 0;
    public static void main(String[] args){
        System.out.println("****************************************");
        System.out.println("Thread를 이용한 다중 접속 서버 작동됨...");
        System.out.println("****************************************");


        //쓰레드 갯수 생성
        executorService = Executors.newFixedThreadPool(10);
        try {
            server = new ServerSocket();
            server.bind(new InetSocketAddress("localhost", 10100));
        } catch (Exception e){
            try {
                server.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Socket connectedClient = server.accept();

                        ++connectCount; //접속자수 카운트
                        System.out.println("[ " + connectCount + " ]"+" 서버 IP: " + connectedClient.getLocalAddress() +
                                ":" + connectedClient.getLocalPort()+" 클라이언트 IP: " + connectedClient.getInetAddress().getHostAddress() +
                                ":" + connectedClient.getPort()+" 접속에 사용된 스레드: " + Thread.currentThread().getName());

                        //서버 스레드 실행
                        ThreadServerHandler handler = new ThreadServerHandler(connectedClient);
                        handler.startThread();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        server.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        };
        executorService.submit(runnable);
    }
}
