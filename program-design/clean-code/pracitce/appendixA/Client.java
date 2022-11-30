import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private int port;

    public Client(int port) {
        this.port = port;
    }

    private void connectSendReceive(String message) {
        try {
            Socket socket = new Socket("localhost", this.port);
            System.out.printf("[Client] send %s%n", message);
            sendMessage(socket, message);

            String receivedMessage = getMessage(socket);
            System.out.printf("[Client] got %s%n", receivedMessage);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private void sendMessage(Socket socket, String message)
        throws IOException {
        OutputStream stream = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(stream);
        oos.writeUTF(message);
        oos.flush();
    }

    private String getMessage(Socket socket) throws IOException {
        InputStream stream = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(stream);
        return ois.readUTF();
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client(8000);
        long startMs = System.currentTimeMillis();
        
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            String message = Integer.toString(i);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    client.connectSendReceive(message);
                }
            });
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }


        long endMs = System.currentTimeMillis();
        System.out.printf("Took %dms%n", endMs - startMs);
    }
}
