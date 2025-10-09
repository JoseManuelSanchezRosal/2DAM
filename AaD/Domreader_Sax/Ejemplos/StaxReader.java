package Ejemplos;
import javax.xml.stream.*;                // Librerías principales del API StAX (Streaming API for XML)
import javax.xml.stream.events.Attribute; // Para manejar los atributos de las etiquetas (como id="...")
import javax.xml.stream.events.XMLEvent;  // Representa cada evento XML (inicio, fin, texto, etc.)
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StaxReader {
    // Metodo principal que lee un archivo XML y devuelve una lista de objetos Book
    public static List<Book> read(File xmlFile) throws Exception {
        // 1️ Se crea una fábrica de lectores StAX (XMLInputFactory)
        //    Este objeto produce un lector de eventos XML que lee el archivo de forma secuencial
        XMLInputFactory f = XMLInputFactory.newInstance();

        // 2️ A partir de la fábrica, se crea el lector de eventos XML (XMLEventReader)
        //    El lector leerá los eventos (inicio de etiqueta, texto, cierre de etiqueta, etc.)
        XMLEventReader r = f.createXMLEventReader(new FileInputStream(xmlFile));

        // 3️ Se crea una lista donde se guardarán todos los libros leídos
        List<Book> books = new ArrayList<>();

        // 4️ Objeto temporal para el libro que se está leyendo
        Book current = null;

        // 5️ Variable para recordar qué etiqueta se está procesando (por ejemplo: title, author, etc.)
        String currentTag = null;

        // 6️ Bucle principal: se repite mientras haya más eventos en el XML
        while (r.hasNext()) {
            // Se obtiene el siguiente evento del lector
            XMLEvent ev = r.nextEvent();
            // 🟢 Si el evento es un "inicio de etiqueta" (por ejemplo, <book>, <title>, etc.)
            if (ev.isStartElement()) {
                // Se obtiene el nombre de la etiqueta actual
                String name = ev.asStartElement().getName().getLocalPart();
                // Se guarda el nombre de la etiqueta actual (para saber qué leer después)
                currentTag = name;
                // Si la etiqueta es <book>, significa que comienza un nuevo libro
                if ("book".equals(name)) {
                    // Se crea un nuevo objeto Book
                    current = new Book();
                    // Se obtienen los atributos de la etiqueta (por ejemplo, id="bk101")
                    Iterator<?> it = ev.asStartElement().getAttributes();
                    // Se recorre cada atributo y se guarda su valor si es "id"
                    while (it.hasNext()) {
                        Attribute a = (Attribute) it.next();
                        if ("id".equals(a.getName().getLocalPart()))
                            current.id = a.getValue();
                    }
                }
                // Si el evento es de tipo "texto" (contenido dentro de una etiqueta)
            } else if (ev.isCharacters()) {
                // Verifica que haya un libro en curso y una etiqueta actual
                if (current != null && currentTag != null) {
                    // Se obtiene el texto dentro de la etiqueta (por ejemplo, el título o autor)
                    String text = ev.asCharacters().getData().trim();
                    // Se ignoran los espacios en blanco vacíos
                    if (!text.isEmpty()) {
                        // Dependiendo de la etiqueta actual, se guarda el valor en el campo correspondiente
                        switch (currentTag) {
                            case "title":
                                current.title = text; // Título del libro
                                break;
                            case "author":
                                current.author = text; // Autor (solo uno en este caso)
                                break;
                            case "year":
                                current.year = Integer.parseInt(text); // Año de publicación
                                break;
                            case "price":
                                current.price = Double.parseDouble(text); // Precio del libro
                                break;
                        }
                    }
                }

                // Si el evento es un "cierre de etiqueta" (por ejemplo, </book>)
            } else if (ev.isEndElement()) {
                // Se obtiene el nombre de la etiqueta que se está cerrando
                String name = ev.asEndElement().getName().getLocalPart();
                // Si se ha cerrado un </book>, significa que el libro actual está completo
                if ("book".equals(name) && current != null) {
                    // Se añade el libro terminado a la lista
                    books.add(current);
                    // Se reinicia la variable current para el siguiente libro
                    current = null;
                }
                // Se limpia la variable currentTag ya que la etiqueta terminó
                currentTag = null;
            }
        }
        // 7️ Cuando se termina de leer el archivo, se devuelve la lista completa de libros
        return books;
    }
}