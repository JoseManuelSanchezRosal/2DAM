package Tarea2DomReader;

// Al analizar un documento XML con DOM obtenemos una estructura de árbol que contiene todos los elementos del documento. El DOM ofrece diversas funciones que permiten examinar el contenido y la estructura del documento.

// VENTAJAS: * Fácil acceso y modificación de cualquier parte del documento ya que todo el documento se carga en memoria.
// * El código Java escrito para un analizador DOM debería ejecutarse en cualquier otro analizador compatible con DOM sin tener que realizar ninguna modificación.

// DESVENTAJAS: * Consume más memoria * No apto para documentos grandes * No aplicable para dispositivos pequeños como PDA´s y teléfonos móviles.

/*Un documento XML consta de muchos elementos. En Java, un documento XML/HTML se representa mediante la interfaz Element. Esta interfaz proporciona varios métodos para recuperar, añadir y modificar el contenido de un documento XML/HTML.*/

/*Podemos recuperar el nombre del elemento raíz mediante el método getTagName() de la interfaz Element. Este método devuelve el nombre del elemento raíz como una cadena.*/

/*Dado que Element es una interfaz, para crear su objeto necesitamos usar el metodo getDocumentElement() . Este método recupera y devuelve el elemento raíz como un objeto.*/

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

public class AFuncionamientoDom {

    /**
     * Método estático que lee un archivo XML y devuelve una lista de objetos Book.
     * @param xmlFile //
     * @return
     * @throws Exception
     */
    public static List<Book> read(File xmlFile) throws Exception {
        // 1- Creamos una fabrica de constructores para leer el XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // 2- Creamos el constructor de documentos (parser XML)
        DocumentBuilder db = dbf.newDocumentBuilder();
        // 3- Parseamos el documento XML a árbol DOM.
        Document doc = db.parse(xmlFile);

// El documento XML/HTML se representa mediante la Interfaz ELEMENT. Esta interfaz proporciona varios métodos para recuperar, añadir y modificar el contenido de un documento XML/HTML:

        // Método getTagName() se usa para recuperar el nombre del elemento raíz como una cadena.
        // Dado que ELEMENT es una Interfaz, para crear su objeto necesitamos usar el método getDocumentElement(). Este método recupera y devuelve el elemento raíz como un objeto.

// Para analizar un único Subelemento en XML:
    // Dado que sólo tenemos un subelemento, usamos el método getFirstChild() para recuperarlo. Este método se usa con el elemento raíz para obtener su primer hijo, devolviendo el NODO HIJO como un objeto NODE.
    // Tras recuperar el NODO secundario, se utiliza el método getNodeName() para obtener su nombre. Este método devuelve el nombre del nodo como una cadena.
    // Por último, para obtener EL CONTENIDO DEL TEXTO: usamos el método getTextContent() que también lo devuelve como una cadena.

        return List.of();
    }

}