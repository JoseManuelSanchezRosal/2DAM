package Dom_Sax_Tarea2;
import Dom_Sax_Tarea2.models.Author;
import Dom_Sax_Tarea2.models.Book;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

public class DomXml {
    static void main() {
        NodeList booksNodeList = null;
        try {
            booksNodeList = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse("src/c/books.xml")
                    .getDocumentElement()
                    .getElementsByTagName("book");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Se almacena el valor del metodo getLength porque si se usa directamente en el for, por cada vuelta del bucle,
        // se va a volver a recalcular la longitud de los libros, y es una tontería ya que nunca va a cambiar
        int booksLength = booksNodeList.getLength();
        // Almacenar cada libro recogido del bucle en la misma dirección de memoria
        ArrayList<Book> books = new ArrayList<>();
        Book book;
        String id;
        String isbn;
        String title;
        ArrayList<Author> authors;
        ArrayList<String> categories;
        int year;
        double price;
        // OJO: Importar la clase Currency de mis modelos, no la de java.util
        String currency;
        Element bookElement;
        NodeList authorsNodeList;
        int authorsLength;
        Author author;
        Element authorElement;
        NodeList categoriesNodeList;
        int categoriesLength;
        Element priceElement;
        for (int i = 0; i < booksLength; i++) {
            bookElement = (Element) booksNodeList.item(i);
            // Los atributos son metadatos que tienen dentro una etiqueta
            id = bookElement.getAttribute("id");
            isbn = bookElement.getAttribute("isbn");
            title = bookElement.getElementsByTagName("title").item(0).getTextContent();
            authors = new ArrayList<>();
            authorsNodeList = bookElement.getElementsByTagName("authors").item(0).getChildNodes();
            authorsLength = authorsNodeList.getLength();
            // Recorrer autores
            for (int j = 0; j < authorsLength; j++) {
                if (authorsNodeList.item(j).getNodeType() == Node.TEXT_NODE) {
                    continue;
                }
                // Obtener autor de cada iteración
                authorElement = (Element) authorsNodeList.item(j);
                // Crear autor
                author = new Author(
                        authorElement.getTextContent(),
                        authorElement.getAttribute("role")
                );
                authors.add(author);
            }

            categories = new ArrayList<>();
            categoriesNodeList = bookElement.getElementsByTagName("categories").item(0).getChildNodes();
            categoriesLength = categoriesNodeList.getLength();
            // Recorrer categorías
            for (int j = 0; j < categoriesLength; j++) {
                categories.add(categoriesNodeList.item(j).getTextContent().trim().replaceAll("\n", ""));
            }

            year = Integer.parseInt(bookElement.getElementsByTagName("year").item(0).getTextContent());
            priceElement = (Element) bookElement.getElementsByTagName("price").item(0);
            price = Double.parseDouble(priceElement.getTextContent());
            currency = priceElement.getAttribute("currency");

            book = new Book(
                    id,
                    isbn,
                    title,
                    authors,
                    categories,
                    year,
                    price,
                    currency
            );
            books.add(book);
        }

        books.forEach(bookForEach -> {
            System.out.println(bookForEach + "\n");
        });
    }
}
