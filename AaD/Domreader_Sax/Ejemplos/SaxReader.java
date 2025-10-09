package Ejemplos; // Paquete donde se encuentra la clase

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaxReader {
    // Metodo principal que lee un archivo XML y devuelve una lista de objetos Book
    public static List<Book> read(File xmlFile) throws Exception {

        // 1️ Se crea una fábrica de analizadores SAX
        SAXParserFactory spf = SAXParserFactory.newInstance();

        // 2️ A partir de la fábrica, se obtiene un analizador SAX (parser)
        SAXParser parser = spf.newSAXParser();

        // 3️ Lista donde se guardarán los libros leídos del XML
        List<Book> books = new ArrayList<>();

        // 4️ Se crea un manejador (handler) anónimo que define cómo actuar al leer el XML
        DefaultHandler handler = new DefaultHandler() {
            // Variable para guardar el libro que se está leyendo actualmente
            Book current;
            // StringBuilder usado para acumular el texto dentro de las etiquetas
            StringBuilder content = new StringBuilder();
            @Override
            // Se ejecuta al encontrar el inicio de una etiqueta, por ejemplo <book> o <title>
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                // Si la etiqueta es <book>, se crea un nuevo objeto Book
                if ("book".equals(qName)) {
                    current = new Book(); // Nuevo libro
                    current.id = attributes.getValue("id"); // Se obtiene el atributo id del libro
                }
                // Se limpia el contenido acumulado para la siguiente etiqueta
                content.setLength(0);
            }
            @Override
            // Se ejecuta cuando se lee texto dentro de una etiqueta (por ejemplo, el título o autor)
            public void characters(char[] ch, int start, int length) {
                // Se añade el texto leído (SAX lo envía en fragmentos)
                content.append(ch, start, length);
            }
            @Override
            // Se ejecuta al cerrar una etiqueta, por ejemplo </book> o </title>
            public void endElement(String uri, String localName, String qName) {
                // Se obtiene el texto leído dentro de la etiqueta y se limpia de espacios
                String text = content.toString().trim();

                // Si hay un libro en proceso de lectura...
                if (current != null) {
                    // Dependiendo de qué etiqueta se haya cerrado, se guarda el valor correspondiente
                    switch (qName) {
                        case "title":
                            current.title = text; // Guarda el título
                            break;
                        case "author":
                            current.authors.add(text);// Con esto anadimios cada autor encontrado a la lista authors
                            //current.author = text; // Guarda el autor (solo uno en este caso)
                            break;
                        case "year":
                            if (!text.isEmpty())
                                current.year = Integer.parseInt(text); // Convierte el año a int
                            break;
                        case "price":
                            if (!text.isEmpty())
                                current.price = Double.parseDouble(text); // Convierte el precio a double
                            break;
                        case "book":
                            // Cuando se cierra </book>, se añade el libro a la lista y se reinicia current
                            books.add(current);
                            current = null;
                            break;
                    }
                }
            }
        };
        // 5️⃣ El parser comienza a analizar el archivo XML y usa el handler definido arriba
        parser.parse(xmlFile, handler);

        // 6️⃣ Devuelve la lista de libros ya procesados
        return books;
    }
}