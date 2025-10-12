package Tarea2DOMySAXReader.Tarea2DomReader;

// Al analizar un documento XML con DOM obtenemos una estructura de árbol que contiene todos los elementos del documento. El DOM ofrece diversas funciones que permiten examinar el contenido y la estructura del documento.

// VENTAJAS: * Fácil acceso y modificación de cualquier parte del documento ya que todo el documento se carga en memoria.
// * El código Java escrito para un analizador DOM debería ejecutarse en cualquier otro analizador compatible con DOM sin tener que realizar ninguna modificación.

// DESVENTAJAS: * Consume más memoria * No apto para documentos grandes * No aplicable para dispositivos pequeños como PDA´s y teléfonos móviles.

/*Un documento XML consta de muchos elementos. En Java, un documento XML/HTML se representa mediante la interfaz Element. Esta interfaz proporciona varios métodos para recuperar, añadir y modificar el contenido de un documento XML/HTML.*/

/*Podemos recuperar el nombre del elemento raíz mediante el método getTagName() de la interfaz Element. Este método devuelve el nombre del elemento raíz como una cadena.*/

/*Dado que Element es una interfaz, para crear su objeto necesitamos usar el metodo getDocumentElement() . Este método recupera y devuelve el elemento raíz como un objeto.*/

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

public class AFuncionamientoDom {

    /**
     * Método estático que lee un archivo XML y devuelve una lista de objetos Book. Hay que
     * @param xmlFile //
     * @return
     * @throws Exception
     */
    public static List<Book> read(File xmlFile) throws Exception {// El archivo FILE xmlFile es la ruta de nuestro .XML

        //FOTO 2
        // Creamos una fabrica de constructores para leer el XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // Creamos el constructor de documentos (parser XML)
        DocumentBuilder db = dbf.newDocumentBuilder();
        // Parseamos el documento XML a árbol DOM.
        Document doc = db.parse(xmlFile);

        //FOTO 3
        // Element representa un nodo individual, en este caso "book", mientras que NODELIST es una colección ordenada de "books"
        NodeList nodeBooks = doc.getElementsByTagName("book");

        // Una vez tengamos nuestra lista de nodos, mediante bucle for vamos a recorrer todos los nodos hijo para extraer la información que necesitemos.
        for (int index = 0; index < nodeBooks.getLength(); index++){
            // Cada nodo se manipula de forma individual usando la interfaz ELEMENT (a través de sus métodos)
            // FOTO 4
            Element book = (Element) nodeBooks.item(index); //en cada vuelta devuelve una instancia de objeto de los elementos "book"
        }
        return List.of();
    }
}