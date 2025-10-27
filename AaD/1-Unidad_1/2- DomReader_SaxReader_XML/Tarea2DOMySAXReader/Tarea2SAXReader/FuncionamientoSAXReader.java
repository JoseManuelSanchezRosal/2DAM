package Tarea2DOMySAXReader.Tarea2SAXReader;
/**
 * Java SAX (Simple API for XML) es un analizador basado en eventos para analizar documentos XML.
 * A diferencia del analizador DOM, SAX no crea un árbol de análisis completo, sino que lee el documento
 * secuencialmente y lanza eventos cuando encuentra etiquetas, texto o atributos.
 *
 * Ventajas principales:
 * 1. Consume menos memoria.
 * 2. Es más rápido que el DOM al no cargar el documento en memoria
 * 3. Ideal para documentos grandes.
 *
 * Desventajas:
 * 1. No hay acceso aleatorio al XML.
 * 2. No permite crear documentos XML.
 * 3. Requiere programar estructuras propias para almacenar la información.
 */

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 La interfaz de ATRIBUTOS se encuantra en el paquete ORG.XML.SAX. Esta Interfaz se utiliza para la lista de atributos XML especificados en un elemento. A continuacion se presentan los metodos más comunes de la interfaz Atributos: -----FOTO 2-----
 */
public class FuncionamientoSAXReader {
    public static List<Book> read(File xmlFile) throws ParserConfigurationException, SAXException, IOException{

        //-----FOTO 3-----CONFIGURACIÓN DEL PARSER
        // 1. Creamos la Fábrica SAXParserFactory (SPF)
        SAXParserFactory spf = SAXParserFactory.newInstance();
        // 2. Creamos el objeto SAXParser a partir de la fábrica
        SAXParser parser = spf.newSAXParser();

        // Lista de libros:
        List<Book> books = new ArrayList<>();

        // -----FOTO 4-----DEFINICIÓN DEL HANDLER
        /** El DefaultHandler se encarga de capturar los eventos generados por el parser SAX:
         *  - startElement(): se llama al encontrar una etiqueta de apertura.
         *  - characters(): se llama al leer texto dentro de una etiqueta.
         *  - endElement(): se llama al encontrar una etiqueta de cierre.*/
        DefaultHandler handler = new DefaultHandler(){

            Book currentBook; // Creamos libro clase Book donde se irá guardando la info de cada libro:
            StringBuilder bookBuilder = new StringBuilder();  // Creamos nuestro SB para almacenar texto de las etiquetas

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if (qName.equals("book")) {
                    // Cuando se abre una etiqueta <book> se crea un objeto Book
                    currentBook = new Book();

                    // Obtener atributos etiqueta book
                    currentBook.setId(attributes.getValue("id"));
                    currentBook.setIsbn(attributes.getValue("isbn"));

                } else if (qName.equals("author")) {
                    // Antes se intentaba leer el atributo "role"
                    // Si tu XML tiene <author role="primary"> puedes ignorarlo
                    // o guardarlo más adelante si decides añadir roles
                    // → eliminamos el setRole de forma segura (sin romper la estructura)

                } else if (qName.equals("price")) {
                    currentBook.setCurrency(attributes.getValue("currency"));
                }

                // Limpia el StringBuilder antes de empezar a leer texto nuevo
                bookBuilder.setLength(0);
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                // Con characters almacenamos el texto leído entre etiquetas
                bookBuilder.append(ch, start, length);
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                // Se obtiene el texto leido y almacenado en el bookBuilder sin espacios:
                String texto = bookBuilder.toString().trim();

                // Si el libro actual no es nulo, asignamos los valores según la etiqueta cerrada
                if (currentBook != null){
                    switch (qName){
                        case "title":
                            currentBook.setTitle(texto);
                            break;

                        case "author":
                            currentBook.addAuthor(texto);
                            break;

                        case "category":
                            currentBook.addCategory(texto);
                            break;

                        case "year":
                            currentBook.setYear(Integer.parseInt(texto));
                            break;

                        case "price":
                            currentBook.setPrice(Double.parseDouble(texto));
                            break;


                        // Cuando el endElement es </book>, significa que hemos llegado al final del libro y por tanto lo añadimos a la lista
                        case "book":
                            books.add(currentBook);

                            currentBook = null;// Se reinicia el objeto para el siguiente libro
                            break;
                    }
                }
            }
        };
        //-----FOTO 5-----
        /** La clase SAXParser tiene el metodo parse(), que recibe dos argumentos: el archivo y el objeto DefaultHandler. Esta función analiza el archivo dado como un documento XML utilizando las funciones implementadas dentro de la clase DefaultHandler:*/
        parser.parse(xmlFile, handler);

        System.out.println("Lista de libros leidos con SAX");
        for (Book b : books) {
            System.out.println("ID: " + b.getId());
            System.out.println("ISBN: " + b.getIsbn());
            System.out.println("Título: " + b.getTitle());
            System.out.println("Autores: " + b.getAuthors());
            System.out.println("Categorías: " + b.getCategories());
            System.out.println("Año: " + b.getYear());
            System.out.println("Precio: " + b.getPrice() + " " + b.getCurrency());
            //System.out.println("Rol: " + b.getRole()+"\n");
        }
        return books;//Devolvemos la lista de books analizados
    }

    public static void main(String[] args) {
        try {
            // Ruta del archivo XML (ajusta la ruta según tu proyecto)
            File xmlFile = new File("Domreader_Sax/Tarea2SAXReader/books.xml");

            // Ejecutamos el metodo read() para procesar el archivo XML
            List<Book> libros = read(xmlFile);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Error al leer el archivo XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}