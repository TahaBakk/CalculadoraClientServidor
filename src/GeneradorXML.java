import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
* Created by x3727349s on 02/03/17.
*/
public class GeneradorXML {
    //Carreguem el arraylist emmagatzemats al hora de carrega el servidor
    public static ArrayList operacion = cargarDatos(1);
    public static ArrayList resultado = cargarDatos(2);
    public static ArrayList ip = cargarDatos(3);
    public static ArrayList fecha = cargarDatos(4);

    public static void pasarDatos(String operacionRes, String resultadoRes) throws IOException {
        //fitxero xml
        String fitxero = "registro";
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        System.out.println("Hora y fecha: "+hourdateFormat.format(date));

        //guardar los datos
        operacion.add(operacionRes);
        resultado.add(resultadoRes);
        ip.add(Servidor.ip);
        fecha.add(hourdateFormat.format(date));

        //guardarObjetos(operacion,resultado);
        guardarDatos(operacion, resultado);

        try {
            generate(fitxero, operacion, resultado);
        } catch (Exception e) {}
    }

    public static void generate(String name, ArrayList<String> operacion,ArrayList<String> resultado) throws Exception{

        if(operacion.isEmpty() || resultado.isEmpty() || operacion.size()!=resultado.size()){
            System.out.println("Error ArrayList vacio");
            return;
        }else{
         //para no sobreescribir los DATOS
        //http://docs.oracle.com/javase/1.5.0/docs/api/java/io/FileOutputStream.html#FileOutputStream%28java.io.File,%20boolean
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, name, null);
            document.setXmlVersion("1.0");

            //Main Node
            Element raiz = document.getDocumentElement();
            //Por cada key creamos un item que contendrá la key y el value
            for(int i=0; i<operacion.size();i++){
                //Item Node
                Element itemNode = document.createElement("data");
                //Key Node
                Element keyNode = document.createElement("operacion");
                Text nodeKeyValue = document.createTextNode(operacion.get(i));
                keyNode.appendChild(nodeKeyValue);
                //Value Node
                Element valueNode = document.createElement("resultado");
                Text nodeValueValue = document.createTextNode(resultado.get(i));
                valueNode.appendChild(nodeValueValue);
                //append keyNode and valueNode to itemNode
                itemNode.appendChild(keyNode);
                itemNode.appendChild(valueNode);
                //append itemNode to raiz
                raiz.appendChild(itemNode); //pegamos el elemento a la raiz "Documento"
            }
            //Generate XML
            Source source = new DOMSource(document);
            //Indicamos donde lo queremos almacenar
            Result result = new StreamResult(new java.io.File(name+".xml")); //nombre del archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        }
    }

    public static void guardarDatos(ArrayList operacion, ArrayList resultado) throws IOException {

        ObjectOutputStream ficheroOperacion = new ObjectOutputStream(new FileOutputStream("objetos/operacion.obj"));
        ObjectOutputStream ficheroRespuesta = new ObjectOutputStream(new FileOutputStream("objetos/respuesta.obj"));
        ObjectOutputStream ficheroip = new ObjectOutputStream(new FileOutputStream("objetos/ip.obj"));
        ObjectOutputStream ficherofecha = new ObjectOutputStream(new FileOutputStream("objetos/fecha.obj"));
        //Las guardamos
        ficheroOperacion.writeObject(operacion);
        ficheroRespuesta.writeObject(resultado);
        ficheroip.writeObject(resultado);
        ficherofecha.writeObject(resultado);
        //limpiamos
        ficheroOperacion.flush();
        ficheroRespuesta.flush();
        ficheroip.flush();
        ficherofecha.flush();
        //cerramos
        ficheroOperacion.close();
        ficheroRespuesta.close();
        ficheroip.close();
        ficherofecha.close();

    }

    public static ArrayList cargarDatos(int opcion)  {
        try {
            ObjectInputStream ficheroOperacion = null;
            ObjectInputStream ficheroRespuesta = null;
            ObjectInputStream ficheroIp = null;
            ObjectInputStream ficherofecha = null;

            int opcionSwitch = opcion;
            switch (opcionSwitch) {
                case 1:
                        ficheroOperacion = new ObjectInputStream(new FileInputStream("objetos/operacion.obj"));//Se le pasa el fitxero
                        ArrayList operacion = (ArrayList)ficheroOperacion.readObject();//cargamos el objeto y lo guardamos en un arraylist
                        return  operacion;//devolvemos el arraylist
                case 2:
                        ficheroRespuesta = new ObjectInputStream(new FileInputStream("objetos/respuesta.obj"));
                        ArrayList resultado = (ArrayList)ficheroRespuesta.readObject();
                        return resultado;
                case 3:
                        ficheroIp = new ObjectInputStream(new FileInputStream("objetos/ip.obj"));
                        ArrayList ip = (ArrayList)ficheroIp.readObject();
                        return ip;
                case 4: ficherofecha = new ObjectInputStream(new FileInputStream("objetos/fecha.obj"));
                        ArrayList fecha = (ArrayList)ficherofecha.readObject();
                        return fecha;
                default:break;
            }
            ficheroOperacion.close();
            ficheroRespuesta.close();
            ficheroIp.close();
            ficherofecha.close();
            
        }
        catch (IOException e) {e.printStackTrace();}
        catch (ClassNotFoundException e) {e.printStackTrace();}
        return null;
    }


}