package Tarea2DomReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomReader {
    public static void main(String[] args) throws Exception {
        // Creación del factory, builder y document:
        File xmlFile = new File("Domreader_Sax/Dom_Sax_Tarea2/books.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // Nueva fabrica
        DocumentBuilder builder = factory.newDocumentBuilder(); // Nuevo constructor.
        Document xmlDoc = (Document) builder.parse(xmlFile); // Documento XML parseado a árbol DOM.
        //MUCHO CUIDADO CON IMPORTAR TANTO EL DOCUMENT COMO EL ELEMENT (QUE NO SEAN DE JAVA SWING, SINO DE org.w3c.dom)

        // FOTO 1:
        Element rootElement = xmlDoc.getDocumentElement(); //Cogemos el elemento RAIZ y lo convertimos a Objeto con ELEMENT
        //System.out.println("El nombre del elemento RAIZ es: " + rootElement.getTagName()); Para sacar el nombre del elemento Raiz.

        // OBTENER TODOS LOS NODOS HIJOS DEL ELEMENTO RAIZ CON NODELIST:
        NodeList nodosHijo = rootElement.getChildNodes();
        ArrayList<Book> books = new ArrayList<>();// Creamos el arraylist de libros.

        // Recorremos todos los nodos hijo del elemento Raiz con un FOR:
        for (int index = 0; index < nodosHijo.getLength(); index++) {
            Node currentNode = nodosHijo.item(index);
            // Con este IF nos aseguramos que el nodo actual es un ELEMENTO.
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                // Si es un elemento, convertimos el nodo actual a OBJETO (element) para acceder a sus elementos internos
                // En este caso, cada hijo (cada libro), en cada iteración, lo convertimos a objeto para extraer información de cada uno.
                Element bookElement = (Element) currentNode;

                // Obtener Datos de cada libro:
                String id = bookElement.getAttribute("id");
                String isbn = bookElement.getAttribute("isbn");
                String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
                String year = bookElement.getElementsByTagName("year").item(0).getTextContent();

                // Obtener el precio y su atributo:
                Element priceElement = (Element) bookElement.getElementsByTagName("price").item(0);
                String price = priceElement.getTextContent();
                String currency = priceElement.getAttribute("currency");

                // book es un objeto Element que representa un nodo <book> en el documento XML.

                // getElementByTagName("author") busca todos los nodos <author> dentro del nodo book.
                NodeList authorsList = bookElement.getElementsByTagName("author");
                StringBuilder authorsBuilder = new StringBuilder(); // Devuelve un nodeList de autores llamado authorsList.

                for (int j = 0; j < authorsList.getLength(); j++){
                    Element authorElement = (Element) authorsList.item(j);
                    authorsBuilder.append(authorElement.getTextContent());
                    // Si el autor tiene rol, lo apendamos tras el nombre entre parentesis.
                    if (authorElement.hasAttribute("role")){
                        authorsBuilder.append(" (").append(authorElement.getAttribute("role")).append(")");
                    }
                    if (j < authorsList.getLength() -1){
                        authorsBuilder.append(", ");
                    }
                }

                // Hacemos lo mismo con categorías por haber más de una en algún caso
                NodeList categoryList = bookElement.getElementsByTagName("category");
                StringBuilder categoriesBuilder = new StringBuilder();
                for (int i = 0; i < categoryList.getLength(); i++) {
                    categoriesBuilder.append(categoryList.item(i).getTextContent());
                    // Si no es el último autor, añade una coma ","
                    if (i < categoryList.getLength() - 1) categoriesBuilder.append(", ");
                }
                // Creamos el objeto libro con sus propiedades y lo añadimos al ArrayList.
                Book book = new Book(id, isbn, title, authorsBuilder.toString(),
                        categoriesBuilder.toString(), year, price, currency);

                books.add(book);
            }
        }
        for (Book b: books){
            System.out.println(b); // Sacamos todos los libros por pantalla
        }
        // Sacamos por pantallas los métodos:
        after2010(books, 2010);
        System.out.println("\n");
        mostrarLibros(books);
        System.out.println("\n");
        precioMedio(books);
    }
    // Mostrar títulos de libros a partir del 2010
    private static void after2010(List<Book> books, int desde) {
        System.out.println("Libros publicados después de " + desde + ":");
        for (Book b : books) {
            try {
                int year = Integer.parseInt(b.getYear());
                if (year > desde) {
                    System.out.println("- " + b.getTitle() + " (" + year + ")");
                }
            } catch (NumberFormatException e) {
                // Si el año no es un número válido, lo ignoramos
            }
        }
    }
    // Método para mostrar los libros con más de un autor
    private static void mostrarLibros(List<Book> books) {
        System.out.println("Libros con más de un autor:");
        for (Book b : books) {
            // Contamos cuántos autores hay separando por coma
            String[] autores = b.getAuthors().split(",");
            if (autores.length > 1) {
                System.out.println("- " + b.getTitle() + " → " + b.getAuthors());
            }
        }
    }

    // Mostrar la media de precio de los libros en EUROS
    private static void precioMedio(List<Book> books){
        double suma = 0;
        int contador = 0;
        for (Book b: books){
            if("EUR".equalsIgnoreCase(b.getCurrency())) { //Filtramos divisa EUR
                try {
                    double precio = Double.parseDouble(b.getPrice());
                    suma += precio;
                    contador++;
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);// si el precio no es numerico se omite
                }
            }
        }
        if (contador > 0){
            double media = suma/contador;
            System.out.println("El precio medio de los libros en EUR: "+ media);
        }else{
            System.out.println("No hay libros en EUROS");
        }
    }
}

