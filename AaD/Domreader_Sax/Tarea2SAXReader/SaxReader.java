package Tarea2SAXReader;

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

public class SaxReader {

    /**
     * Método que devuelve una lista de libros. Leídos desde un fichero XML con SAX.
     * @param xmlFile
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     */

    public static List<Book> read(File xmlFile) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser = spf.newSAXParser();

        // Lista donde se almacenarán los libros.
        List<Book> books = new ArrayList<>();

        DefaultHandler handler = new DefaultHandler(){

            // LIbro donde se irá almacenando la información leida de cad libro del XML.
            Book current;

            // String Builder donde se irá almacenando el texto de cada etiqueta.
            StringBuilder content = new StringBuilder();

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if (qName.equals("book")){
                    // INicializar objeto book. Al instanciar el libro ya se inicializan las listas de autored y
                    // categorías.
                    current = new Book();

                    // Obtener atributos etiqueta book.
                    current.setId(attributes.getValue("id"));
                    current.setIsbn(attributes.getValue("isbn"));

                } else if (qName.equals("author")) {
                    current.setRole(attributes.getValue("role"));

                } else if (qName.equals("price")) {
                    current.setCurrency(attributes.getValue("currency"));
                }

                // Limpia el StringBuilder antes de empezar a leer texto nuevo
                content.setLength(0);
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                content.append(ch, start, length);
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {

                // Se obtiene el texto leido y almacenado en el StringBuilder e una variable String.
                String texto = content.toString().trim();

                // Si el objeto libro no está vacío, depende de la etiqueta que se cierre, se añade el texto a
                // un atributo u otro del objeto libro.
                if (current != null){
                    switch (qName){
                        case "title":
                            current.setTitle(texto);
                            break;

                        case "author":
                            current.addAuthor(texto);
                            break;

                        case "category":
                            current.addCategory(texto);
                            break;

                        case "year":
                            current.setYear(Integer.parseInt(texto));
                            break;

                        case "price":
                            current.setPrice(Double.parseDouble(texto));
                            break;

                        // Si es la etiqueta book significa que ya ha terminado de leer todo el libro y por
                        // tanto se añade a la lista que devolverá el método.
                        case "book":
                            books.add(current);

                            // Se reinicia el objeto.
                            current = null;
                            break;
                    }
                }
            }

        };

        parser.parse(xmlFile, handler);
        return books;
    }

}