import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;

public class Ej_dom {
    public static void main(String[] args) throws IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("datosbeca.dat"))) {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "Becarios", null);
            document.setXmlVersion("1.0");

            //Obtenemos los datos de la lista de becarios
            ArrayList<Becario> listaBecarios = (ArrayList<Becario>) ois.readObject();

            for (Becario becario : listaBecarios) {

                Element raiz = document.createElement("becario");//nodo becario
                document.getDocumentElement().appendChild(raiz);

                //creamos el elemento nombreCompleto con su contenido
                CrearElemento("nombreCompleto", becario.getNombreCompleto(), raiz, document);
                //creamos el elemento sexo con su contenido
                CrearElemento("sexo", becario.getSexo(), raiz, document);
                //creamos el elemento edad con su contenido
                CrearElemento("edad", String.valueOf(becario.getEdad()), raiz, document);
                //creamos el elemento numSuspensos con su contenido
                CrearElemento("numSuspensos", String.valueOf(becario.getNumSuspensos()), raiz, document);
                //creamos el elemento residenciaFamiliar con su contenido
                CrearElemento("residenciaFamiliar", becario.getResidenciaFamiliar(), raiz, document);
                //creamos el elemento ingresosAnuales con su contenido
                CrearElemento("ingresosAnuales", String.valueOf(becario.getIngresosAnuales()), raiz, document);
            }
            Source source = new DOMSource(document);
            Result result = new StreamResult(new java.io.File("Becario.xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void CrearElemento(String datoEmpleado, String valor, Element raiz, Document document) {
        Element element = document.createElement(datoEmpleado);//creamos el elemento
        Text text = document.createTextNode(valor);//le asignamos valor
        raiz.appendChild(element);//pegamos el elemento hijo a la raiz
        element.appendChild(text);//ponemos el valor
    }
}
