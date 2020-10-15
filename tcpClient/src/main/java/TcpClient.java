public class TcpClient {
    public static void main(String[] args) {
        for(int i=0; i<10; i++){
            ThreadClientHandler client = new ThreadClientHandler();
            client.init("127.0.0.1", 10100,i+1);
        }

    }
}