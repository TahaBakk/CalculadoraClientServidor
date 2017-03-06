import java.io.*;
import java.util.ArrayList;
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
    //falta L'hora en que s'ha realitzat l'operació, la IP

    //public static ArrayList operacion = new ArrayList();
    //public static ArrayList resultado = new ArrayList();
    public static ArrayList operacion = cargarDatos(1);
    public static ArrayList resultado = cargarDatos(2);

    public static void pasarDatos(String operacionRes, String resultadoRes) throws IOException {

        //leerObjetos();

        String fitxero = "registro";

        operacion.add(operacionRes);
        resultado.add(resultadoRes);

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

            ficheroOperacion.writeObject(operacion);
            ficheroRespuesta.writeObject(resultado);

            ficheroOperacion.flush();
            ficheroRespuesta.flush();

            ficheroOperacion.close();
            ficheroRespuesta.close();

            System.out.println("Arraylist guardadas correctamente...");
    }

    public static ArrayList cargarDatos(int opcion)  {
        try {
            ObjectInputStream ficheroOperacion = null;
            ObjectInputStream ficheroRespuesta = null;

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

                case 3: break;
                case 4: break;
                default:break;
            }

            ficheroOperacion.close();
            ficheroRespuesta.close();


        }
        catch (IOException e) {e.printStackTrace();}
        catch (ClassNotFoundException e) {e.printStackTrace();}
        return null;
    }


}