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
    public static void pasarDatos(String operacionRes, String resultadoRes){

        //leerObjetos();

        String fitxero = "registro";
        ArrayList operacion = new ArrayList();
        ArrayList resultado = new ArrayList();

        operacion.add(operacionRes);
        resultado.add(resultadoRes);

        guardarObjetos(operacion,resultado);

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


    public static void guardarObjetos(ArrayList operacion,ArrayList resultado){

        try {
            ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("informacionRegistros.obj"));
            salida.writeObject(operacion);
            salida.writeObject(resultado);
            salida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void leerObjetos(ArrayList operacion,ArrayList resultado) {


    /*ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("media.obj"));
    String str=(String)entrada.readObject();
    Lista obj1=(Lista)entrada.readObject();
      System.out.println("Valor medio "+obj1.valorMedio());
      System.out.println("-----------------------------");
      System.out.println(str+obj1);
      System.out.println("-----------------------------");
      entrada.close();*/

    }


}