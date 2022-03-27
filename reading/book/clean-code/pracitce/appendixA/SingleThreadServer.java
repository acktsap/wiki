import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadServer implements Runnable {
    private ServerSocket serverSocket;
    private volatile boolean keepProcessing = true;

    public SingleThreadServer(int port, int millisecondsTimeout) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(millisecondsTimeout);
    }

    public void run() {
        System.out.printf("SingleServer Starting\n");
        while (keepProcessing) {
            try {
                System.out.printf("[SingleServer] accepting client\n");
                Socket socket = serverSocket.accept();
                process(socket);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }

    public void stopProcessing() {
        keepProcessing = false;
        closeIgnoringException(serverSocket);
    }

    private void process(Socket socket) {
        if (socket == null)
            return;
        try {
            String message = getMessage(socket);
            System.out.printf("[SingleServer] got message: %s%n", message);
            Thread.sleep(1000);

            String replyMessage = "Processed: " + message;
            System.out.printf("[SingleServer] sending reply: %s%n", replyMessage);
            sendMessage(socket, replyMessage);
            closeIgnoringException(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeIgnoringException(Socket socket) {
        if (socket != null)
            try {
                socket.close();
            } catch (IOException ignore) {
            }
    }

    private void closeIgnoringException(ServerSocket serverSocket) {
        if (serverSocket != null)
            try {
                serverSocket.close();
            } catch (IOException ignore) {
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

    public static void main(String[] args) throws IOException {
        SingleThreadServer server = new SingleThreadServer(8000, 60000);
        server.run();
    }
}
