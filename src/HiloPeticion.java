import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by x3727349s on 02/03/17.
 */
public class HiloPeticion extends Thread{

    private Socket socket;


    public HiloPeticion() {
    }

    public HiloPeticion(Socket socket) {this.socket = socket;}

    @Override
    public void run() {
        InputStream is = null;
        try {
            is = socket.getInputStream();//recibir
            OutputStream os = socket.getOutputStream();//enviar

            byte[] mensaje = new byte[50];
            is.read(mensaje);

            Double resposta = operacion(new String(mensaje));
            String resposta2 = String.valueOf(resposta);//tranformamos la resposta a string para poder pasarla al os.write
            System.out.println("Mensaje recibido : "+ new String (mensaje));//mostramos el mensaje por la pantalla

            os.write(resposta2.getBytes());//enviamos la respuesta para mostrarla al cliente

            System.out.println("Cerrando el socket");
            socket.close();

            System.out.println("Cerrando el socket servidor");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double operacion (String operacion){
        Double resultado=0.0;
        //usamos el split para que nos separe los valores que estan antes de la operacion y el de despues
        if (operacion.contains("+")){
            String [] datos = operacion.split("\\+");
            resultado = Double.parseDouble(datos[0])+Double.parseDouble(datos[1]);//los pasamos al tipo double
            return resultado;
        }

        if (operacion.contains("-")){
            String [] datos = operacion.split("-");
            resultado = Double.parseDouble(datos[0])-Double.parseDouble(datos[1]);//los pasamos al tipo double
            return resultado;
        }

        if (operacion.contains("/")){
            String [] datos = operacion.split("/");
            resultado = Double.parseDouble(datos[0])/Double.parseDouble(datos[1]);//los pasamos al tipo double
            return resultado;
        }

        if (operacion.contains("*")){
            String [] datos = operacion.split("\\*");
            resultado = Double.parseDouble(datos[0])*Double.parseDouble(datos[1]);//los pasamos al tipo double
            System.out.println(resultado);
            return resultado;
        }

        return resultado;
    }

}