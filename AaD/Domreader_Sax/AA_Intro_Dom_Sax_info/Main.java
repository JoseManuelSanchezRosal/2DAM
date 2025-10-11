package AA_Intro_Dom_Sax_info;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception{
        // FOTO 0:
        File xmlFile = new File("Domreader_Sax/Dom_Sax_Tarea2/books.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // Nueva fabrica
        DocumentBuilder builder = factory.newDocumentBuilder(); // Nuevo constructor.
        Document xmlDoc = (Document) builder.parse(xmlFile); // Documento XML parseado a árbol DOM.
        //MUCHO CUIDADO CON IMPORTAR TANTO EL DOCUMENT COMO EL ELEMENT (QUE NO SEAN DE JAVA SWING, SINO DE org.w3c.dom)

        // FOTO 1:
        Element raiz = xmlDoc.getDocumentElement(); //Cogemos el elemento RAIZ y lo convertimos a Objeto con ELEMENT
        System.out.println("El nombre del elemento RAIZ es: " + raiz.getTagName()); // Sacamos el nombre del elemento raiz.













    }
}
