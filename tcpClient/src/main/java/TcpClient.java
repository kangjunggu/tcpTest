import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpClient {

    static ExecutorService executorService; // 스레드풀
    public static void main(String[] args) {
        executorService = Executors.newFixedThreadPool(10);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ThreadClientHandler client = new ThreadClientHandler();
                client.init("127.0.0.1", 10100);
            }
        };
        for(int i=0; i<10; i++) {
            executorService.submit(runnable);
        }

    }
}