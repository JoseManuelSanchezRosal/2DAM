package TriviaConIA;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivos {

    public static List<Pregunta> cargarPreguntas() {
        List<Pregunta> lista = new ArrayList<>();
        File archivo = new File("preguntas.txt");

        if (!archivo.exists()) {
            crearArchivoTxtPorDefecto();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    List<String> opciones = new ArrayList<>();
                    opciones.add(partes[1]);
                    opciones.add(partes[2]);
                    opciones.add(partes[3]);
                    opciones.add(partes[4]);

                    Pregunta p = new Pregunta(partes[0], opciones, partes[5]);
                    lista.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo preguntas.txt");
        }
        return lista;
    }

    private static void crearArchivoTxtPorDefecto() {
        try (FileWriter fw = new FileWriter("preguntas.txt")) {
            fw.write("¿Cual es el protocolo que traduce nombres de dominio a IP?;FTP;HTTP;DNS;SSH;DNS\n");
            fw.write("¿Que puerto usa por defecto HTTPS?;80;443;21;22;443\n");
            fw.write("¿Que capa del modelo OSI maneja el enrutamiento?;Red;Enlace;Transporte;Aplicacion;Red\n");
            fw.write("¿Comando para ver la IP en Windows?;ifconfig;ping;traceroute;ipconfig;ipconfig\n");
            fw.write("¿Cual es la IP de localhost?;192.168.1.1;127.0.0.1;10.0.0.1;0.0.0.0;127.0.0.1\n");
        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo preguntas.txt");
        }
    }

    public static void guardarPartidaXML(List<ClienteHandler> clientes) {
        try {
            File file = new File("partidas.xml");
            Document doc;
            Element rootElement;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            if (file.exists()) {
                doc = dBuilder.parse(file);
                rootElement = doc.getDocumentElement();
            } else {
                doc = dBuilder.newDocument();
                rootElement = doc.createElement("partidas");
                doc.appendChild(rootElement);
            }

            Element partida = doc.createElement("partida");

            for (ClienteHandler c : clientes) {
                Element jugador = doc.createElement("jugador");
                jugador.setAttribute("nick", c.getNick());
                jugador.setAttribute("puntuacion", String.valueOf(c.getPuntuacion()));
                partida.appendChild(jugador);
            }

            rootElement.appendChild(partida);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            System.out.println("Partida guardada correctamente en partidas.xml");

        } catch (Exception e) {
            System.out.println("Error al guardar partidas.xml");
            e.printStackTrace();
        }
    }
}