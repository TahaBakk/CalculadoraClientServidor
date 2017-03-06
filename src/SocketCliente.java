import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by x3727349s on 02/03/17.
 */
public class SocketCliente {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        int opcio = 0;

        do{
            System.out.println("");
            System.out.println("----------------------------------");
            System.out.println("Seleccione una opcion:");
            System.out.println("----------------------------------");
            System.out.println("\t1.Realizar una operacion");
            System.out.println("\t2.Salir");
            System.out.println("----------------------------------");
            opcio = sc.nextInt();
                if (opcio == 1) {

                    System.out.println("introduceme la operacion a realizar:(ej 5+4)");
                    String operacion = sc2.nextLine();

                    try {
                        Socket cliente = new Socket();
                        InetSocketAddress addr = new InetSocketAddress("localhost", 5540);
                        cliente.connect(addr);
                        InputStream is = cliente.getInputStream();
                        OutputStream os = cliente.getOutputStream();
                        String mensaje = operacion;
                        os.write(mensaje.getBytes());

                        byte [] resposta= new byte[50];
                        is.read(resposta);
                        System.out.println("Respuesta = "+ new String (resposta));

                        cliente.close();


                    } catch (IOException e) {
                        System.out.println("Error numeros alfanumericos"+e);
                    }

                }
        }while (opcio!=2);
    }
}
