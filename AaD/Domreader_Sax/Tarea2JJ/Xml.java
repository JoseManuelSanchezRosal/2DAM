package Tarea2JJ;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Xml {
    static void main() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            // Leer el archivo XML y parsearlo de un String a un tipo Element para poder manejarlo desde código
            Document document = documentBuilder.parse("src/c/books.xml");
            // Coger la etiqueta raíz (library) para empezar a trabajar
            Element root = document.getDocumentElement();
            // Coger el segundo libro a través de la etiqueta book
            Element book = (Element) root.getElementsByTagName("book").item(1);
            // Coger la etiqueta title del segundo libro
            Node title = book.getElementsByTagName("title").item(0);
            // Coger texto de la etiqueta title
            String text = title.getChildNodes().item(0).getTextContent();
            System.out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}