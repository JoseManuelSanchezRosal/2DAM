package Tarea2DOMySAXReader.Tarea2SAXReader;

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

public class SAXReader {

    public static List<Book> read(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        // Creamos la fábrica Sax y el parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        // Lista de libros
        List<Book> books = new ArrayList<>();

        // DefaultHandler para procesar los elementos del XML
        DefaultHandler handler = new DefaultHandler() {
            // Inicializamos las variables donde guardaremos los libros y los roles, para control de errores.
            Book currentBook = null; //
            String currentRole = null; //
            StringBuilder bookBuilder = new StringBuilder(); //SB para agregar información de los libros

            // LLAMAMOS AL INICIO DE CADA ELEMENTO
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                switch (qName) {

                    // En función del nombre de la etiqueta de inicio, guardaremos los valores donde corresponda:
                    case "book":
                        currentBook = new Book();
                        currentBook.setId(attributes.getValue("id"));
                        currentBook.setIsbn(attributes.getValue("isbn"));
                        break;
                    case "author":
                        currentRole = attributes.getValue("role");
                        break;
                    case "price":
                        if (currentBook != null)
                            currentBook.setCurrency(attributes.getValue("currency"));
                        break;
                }
                // Inicializamos el bookBuilder para el siguiente libro a leer
                bookBuilder.setLength(0);
            }

            @Override
            // Almacenamos el texto leído entre la etiqueta de start y end element
            public void characters(char[] ch, int start, int length) throws SAXException {
                bookBuilder.append(ch, start, length);
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                // Se obtiene el texto del bookBuiler sin espacios:
                String text = bookBuilder.toString().trim();

                // Si hay libro, guardamos los valores de cada etiqueta de cierre según corresponda
                if (currentBook != null) {
                    switch (qName) {
                        case "title":
                            currentBook.setTitle(text);
                            break;
                        case "author":
                            currentBook.addAuthor(text);
                            currentBook.addRole(currentRole);
                            currentRole = null;
                            break;
                        case "category":
                            currentBook.addCategory(text);
                            break;
                        case "year":
                            currentBook.setYear(Integer.parseInt(text));
                            break;
                        case "price":
                            currentBook.setPrice(Double.parseDouble(text));
                            break;

                            // Si la etiqueta final leída es </book>, hemos llegado al final del libro y lo agregamos a nuestra lista de libros:
                        case "book":
                            books.add(currentBook);
                            currentBook = null;
                            break;
                    }
                }
            }
        };

        parser.parse(xmlFile, handler);
        return books;
    }

    // MÉTODOS ADICIONALES -------------------------
    // Metodo que recibe por parametro una lista de libros, y nos devuelve los publicados a partir del 2010
    public static void mostrarDesde(List<Book> books) {
        System.out.println("\nLibros publicados después de 2010:");
        for (Book b : books) {
            if (b.getYear() > 2010) {
                System.out.println(" - " + b.getTitle() + " (" + b.getYear() + ")");
            }
        }
    }
    // Metodo que recibe como parametro una lista de libros, y nos devuelve los que tengan mas de un autor
    public static void mostrarLibrosVariosAutores(List<Book> books) {
        System.out.println("\nLibros con más de un autor:");
        for (Book b : books) {
            if (b.getAuthors().size() > 1) {
                System.out.println(" - " + b.getTitle() + ": " + b.showAuthors());
            }
        }
    }
    // Metodo que recibe una lista de libros y nos devuelve el precio medio si la divisa es EUR
    public static void mostrarPrecioMedio(List<Book> books) {
        double total = 0;
        int count = 0;
        for (Book b : books) {
            if ("EUR".equalsIgnoreCase(b.getCurrency())) {
                total += b.getPrice();
                count++;
            }
        }
        double media = count > 0 ? total / count : 0;
        System.out.println("\nPrecio medio: " + media);
    }

    // MAIN--------------------------
    public static void main(String[] args) {
        try {
            File xmlFile = new File("Domreader_Sax/Tarea2SAXReader/books.xml");
            List<Book> books = read(xmlFile);

            System.out.println("Lista de libros leídos con SAX:\n");
            for (Book b : books) {
                System.out.println(b);
            }

            //Mostramos los metodos adicionales
            mostrarPrecioMedio(books);
            mostrarDesde(books);
            mostrarLibrosVariosAutores(books);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}