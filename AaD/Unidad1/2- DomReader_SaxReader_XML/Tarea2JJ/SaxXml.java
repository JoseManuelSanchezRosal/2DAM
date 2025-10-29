package Tarea2JJ;


import Tarea2JJ.models.Author;
import Tarea2JJ.models.Book;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;

public class SaxXml {
    static void main() {
        SAXParser saxParser = null;
        try {
            saxParser = SAXParserFactory.newInstance().newSAXParser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Book> books = new ArrayList<>();

        DefaultHandler defaultHandler = new DefaultHandler() {
            Book book;
            ArrayList<Author> authors = new ArrayList<>();
            ArrayList<String> categories = new ArrayList<>();
            Author author = new Author();
            String category;
            StringBuilder stringBuilder = new StringBuilder();
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if ("book".equals(qName)) {
                    book = new Book(); // Nuevo libro
                    book.setId(attributes.getValue("id"));
                    book.setIsbn(attributes.getValue("isbn"));
                } else if ("author".equals(qName)) {
                    author.setRole(attributes.getValue("role"));
                } else if ("price".equals(qName)) {
                    book.setCurrency(attributes.getValue("currency"));
                }
                stringBuilder = new StringBuilder();
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                stringBuilder.append(ch, start, length);
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                String text = stringBuilder.toString().trim();

                // Si hay un libro en proceso de lectura...
                if (book != null) {
                    // Dependiendo de qué etiqueta se haya cerrado, se guarda el valor correspondiente
                    switch (qName) {
                        case "title":
                            book.setTitle(text); // Guarda el título
                            break;
                        case "author":
                            author.setName(text);
                            authors.add(author);
                            // Crear un nuevo autor para desreferenciar la dirección de memoria del anterior
                            author = new Author();
                            break;
                        case "category":
                            category = text;
                            categories.add(category);
                            break;
                        case "year":
                            book.setYear(Integer.parseInt(text));
                            break;
                        case "price":
                            book.setPrice(Double.parseDouble(text));
                            break;
                        case "book":
                            book.setAuthors(authors);
                            book.setCategories(categories);
                            books.add(book);
                            authors = new ArrayList<>();
                            categories = new ArrayList<>();
                            book = null;
                            break;
                    }
                }
            }
        };

        try {
            saxParser.parse("src/c/books.xml", defaultHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Book book : books) {
            System.out.println(book + "\n");
        }
    }
}