package EjemplosDomySax;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomReader {
    // Metodo principal: lee un archivo XML y devuelve una lista de objetos Book
    public static List<Book> read(File xmlFile) throws Exception {

        // 1️ Crea una fábrica de constructores de documentos (para leer XML)
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // 2️ Crea un constructor de documentos (parser XML)
        DocumentBuilder db = dbf.newDocumentBuilder();
        // 3️ Parsea el archivo XML y lo convierte en un árbol DOM (Document)
        Document doc = db.parse(xmlFile);

        // 4️ Obtiene una lista de todos los elementos <book> del documento
        NodeList nodeLibros = doc.getElementsByTagName("book");

        // 5️ Creamos una lista donde guardaremos los objetos Book que se lean del XML
        List<Book> books = new ArrayList<>();

        // 6️ Recorremos todos los nodos <book> encontrados
        for (int i = 0; i < nodeLibros.getLength(); i++) {
            // Obtenemos el elemento actual (cada <book>)
            Element e = (Element) nodeLibros.item(i);
            // Creamos un nuevo objeto Book
            Book b = new Book();

            // 7️ Leemos el atributo "id" del elemento <book>
            b.id = e.getAttribute("id");

            // 8️ Leemos los valores de las etiquetas hijas: <title>, <year>, <price>
            b.title  = textOf(e, "title");                  // obtiene el texto dentro de <title>
            b.year   = parseIntSafe(textOf(e, "year"));     // convierte <year> a int
            b.price  = parseDoubleSafe(textOf(e, "price")); // convierte <price> a double

            // 9️ Ahora recogemos *todos* los autores del libro
            // Antes solo se cogía uno, ahora se recogen varios si existen varios <author>
            NodeList nodAutor = e.getElementsByTagName("author");
            for (int j = 0; j < nodAutor.getLength(); j++) {
                // Obtenemos el texto de cada <author> y lo añadimos a la lista de autores del libro
                b.authors.add(nodAutor.item(j).getTextContent().trim());
            }
            // 10️ Añadimos el libro completo a la lista de libros
            books.add(b);
        }
        // 11  Devolvemos la lista completa de libros leídos
        return books;
    }

    // Metodo auxiliar para obtener el texto de una etiqueta hija dentro de un elemento
    private static String textOf(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        return (nl.getLength() > 0) ? nl.item(0).getTextContent().trim() : "";
    }
    // Metodo auxiliar que convierte un String en int de forma segura (si está vacío devuelve 0)
    private static int parseIntSafe(String s) {
        return s.isEmpty() ? 0 : Integer.parseInt(s);
    }
    // Metodo auxiliar que convierte un String en double de forma segura (si está vacío devuelve 0.0)
    private static double parseDoubleSafe(String s) {
        return s.isEmpty() ? 0.0 : Double.parseDouble(s);
    }
}