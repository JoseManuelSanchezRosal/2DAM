package Tarea2SAXReader;
/**
 * Java SAX (Simple API for XML) es un analizador basado en eventos para analizar documentos XML. A diferencia del analizador DOM, el analizador SAX no crea un árbol de análisis. No carga el documento completo en memoria, sino que lo lee y notifica al programa cliente cuando encuentra elementos, atributos, contenido de texto y otros datos en forma de eventos. Estos eventos se gestionan mediante los métodos implementados en el controlador de eventos.
 */
/**¿Qué hace el analizador SAX?:
 Un analizador SAX hace lo siguiente en un programa cliente:
 Lee el documento XML de arriba a abajo e identifica los tokens.
 Procesa los tokens en el mismo orden en que aparecen.
 Informa al analizador sobre la naturaleza de los tokens.
 Invoca los métodos de devolución de llamada en el controlador de eventos en función de los tokens identificados.
 */
/**
 * Las principales ventajas son: 1-CONSUME MENOS MEMORIA. 2-ES MAS RAPIDO QUE DOM AL NO CARGAR EL DOCUMENTO. 3-PARA DOCUMENTOS MAS GRANDES
 */
/**
 * Las desventajas son: 1-NO ACCESO ALEATORIO AL XML. 2-NO SE PUEDEN CREAR DOCUMENTOS. 3-SI DESEAMOS REALIZAR SEGUIMIENTO DE DATOS QUE EL ANALIZADOR VE O CAMBIAR EL ORDEN DE ELEMENTOS, DEBEMOS ESCRIBIR CODIGO PARA ALMACENAR LOS DATOS POR NUESTRA CUENTA.
 */
/**
 * La interfaz ContentHandler es la interfaz principal del paquete org.xml.sax . La mayoría de las aplicaciones la implementan para realizar eventos básicos de análisis. Estos eventos incluyen el inicio y el final de un documento, el inicio y el final de los elementos y los datos de caracteres. Es necesario implementar y registrar un Handler para realizar cualquier tarea en el documento XML.
 *
 * Existen clases integradas, a saber, DefaultHandler , DefaultHandler2 y ValidatorHandler, que implementan la interfaz ContentHandler. Podemos usar estas clases para implementar nuestros controladores definidos por el usuario.
 *
 * Esta interfaz especifica los métodos de devolución de llamada que el analizador SAX utiliza para notificar a una aplicación los componentes del documento XML que ha visto. A continuación, se muestran los métodos de la interfaz ContentHandler: -----FOTO 1-----
 */

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 La interfaz de ATRIBUTOS se encuantra en el paquete ORG.XML.SAX. Esta Interfaz se utiliza para la lista de atributos XML especificados en un elemento. A continuacion se presentan los metodos mas comunes de la interfaz Atributos: -----FOTO 2-----
 */
public class FuncionamientoSAXReader {
    public static List<Book> read(File xmlFile) throws ParserConfigurationException, SAXException, IOException{

        //-----FOTO3-----
        // Leemos el archivo XML pasandole la ruta al main como parámetro con read(File xmlFile) definida en el MAIN:
        // File xmlFile = new File("Domreader_Sax/Tarea2SAXReader/books.xml");
        //1- Creamos la Fábrica SPF
        SAXParserFactory spf = SAXParserFactory.newInstance();
        //2- Creamos el objeto PARSER
        SAXParser parser = spf.newSAXParser();

        //-----FOTO4-----//3- Creamos el Handler:

        DefaultHandler handler = new DefaultHandler();

        Book currentBook; //Creamos libro clase Book donde se irá guardando la info de cada libro:
        StringBuilder bookBuilder = new StringBuilder();



    }

}
