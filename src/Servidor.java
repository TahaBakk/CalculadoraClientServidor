import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by x3727349s on 02/03/17.
 */
public class Servidor {

    public static void main(String[] args) throws IOException {
        System.out.println("generando el servidor");

        ServerSocket serverSocket = new ServerSocket();
        System.out.println("Realizando el vinculo");
        InetSocketAddress addr = new InetSocketAddress("localhost",5540);
        serverSocket.bind(addr);

        System.out.println("Aceptando conexiones");

        do {
            Socket socket = serverSocket.accept();
            HiloPeticion hp = new HiloPeticion(socket);
            hp.start();
            System.out.println("Conexión recibida");
        }while(true);
    }
}