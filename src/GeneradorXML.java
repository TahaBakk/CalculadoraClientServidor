import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
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

    public static void pasarDatos(String operacionRes, String resultadoRes){
        String fitxero = "registro";
        ArrayList operacion = new ArrayList();
        ArrayList resultado = new ArrayList();

        operacion.add(operacionRes);
        resultado.add(resultadoRes);

        try {
            generate(fitxero, operacion, resultado);
        } catch (Exception e) {}
    }

    public static void generate(String name, ArrayList<String> operacion,ArrayList<String> resultado) throws Exception{

        if(operacion.isEmpty() || resultado.isEmpty() || operacion.size()!=resultado.size()){
            System.out.println("Error ArrayList vacio");
            return;
        }else{

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

}