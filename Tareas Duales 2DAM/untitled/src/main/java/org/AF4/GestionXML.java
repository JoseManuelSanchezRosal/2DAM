package org.AF4;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Scanner;

/**
 * CLASE PARA EL RESULTADO DE APRENDIZAJE 5 (RA5)
 * Gestión de información en bases de datos nativas XML (Simulación con DOM).
 * Estructura: Menú interactivo similar al RA2.
 * MODIFICACIÓN: ID Autoincremental.
 */
public class GestionXML {

    // Criterio RA5.c: Configuramos el gestor (Definimos las rutas de nuestros archivos).
    private static final String NOMBRE_ARCHIVO = "productos.xml";

    public static void main(String[] args) {
        System.out.println("--- INICIO RA5: GESTIÓN DE PRODUCTOS XML (INTERACTIVO) ---");

        try {
            // Criterio RA5.b: Instalamos/Preparamos el gestor.
            File archivo = new File(NOMBRE_ARCHIVO);
            if (!archivo.exists()) {
                crearEstructuraBase(archivo);
            }

            Scanner sc = new Scanner(System.in);
            int opcion = 0;

            do {
                mostrarMenu();
                try {
                    opcion = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    opcion = -1;
                }

                switch (opcion) {
                    case 1: // Insertar (Ahora con ID Automático)
                        System.out.println("\n--- Nuevo Producto XML ---");

                        // Calculamos el siguiente ID automáticamente
                        String idAuto = generarNuevoId(archivo);
                        System.out.println("Generando ID automático: " + idAuto);

                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Descripción: ");
                        String desc = sc.nextLine();
                        System.out.print("Precio: ");
                        String precio = sc.nextLine();
                        System.out.print("Stock: ");
                        String stock = sc.nextLine();

                        // Criterio RA5.g: Desarrollamos aplicaciones para añadir documentos/nodos.
                        insertarProductoXML(archivo, idAuto, nombre, desc, precio, stock);
                        break;

                    case 2: // Listar
                        System.out.println("\n--- Leyendo Base de Datos XML ---");
                        // Criterio RA5.e: Desarrollamos aplicaciones que efectúan consultas.
                        leerProductosXML(archivo);
                        break;

                    case 3: // Modificar Precio
                        System.out.println("\n--- Modificar Precio en XML ---");
                        System.out.print("ID del producto a modificar: ");
                        String idMod = sc.nextLine();
                        System.out.print("Nuevo Precio: ");
                        String nuevoPrecio = sc.nextLine();

                        // Criterio RA5.g: Desarrollamos aplicaciones para modificar documentos.
                        modificarPrecioXML(archivo, idMod, nuevoPrecio);
                        break;

                    case 4: // Gestión de Colecciones
                        // Criterio RA5.f: Añadimos y eliminamos colecciones.
                        gestionarColecciones();
                        break;

                    case 5:
                        System.out.println("Saliendo del gestor XML...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
                System.out.println("------------------------------------------------");

            } while (opcion != 5);

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\nMENÚ XML:");
        System.out.println("1. Insertar Producto (ID Auto)");
        System.out.println("2. Listar Productos");
        System.out.println("3. Modificar Precio por ID");
        System.out.println("4. Gestionar Colecciones (Prueba carpeta)");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // Inicializamos el archivo XML si no existe
    private static void crearEstructuraBase(File archivo) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        // Creamos el elemento raíz <tienda>
        Element rootElement = doc.createElement("tienda");
        doc.appendChild(rootElement);

        guardarXML(doc, archivo);
        System.out.println("Base de datos XML inicializada.");
    }

    // MÉTODO NUEVO: Calcula el siguiente ID leyendo los existentes
    private static String generarNuevoId(File archivo) {
        int maxId = 0;
        try {
            Document doc = cargarDOM(archivo);
            NodeList lista = doc.getElementsByTagName("producto");

            // Recorremos todos los productos para encontrar el ID más alto
            for (int i = 0; i < lista.getLength(); i++) {
                Element e = (Element) lista.item(i);
                String idStr = e.getAttribute("id");
                try {
                    int idNum = Integer.parseInt(idStr);
                    if (idNum > maxId) {
                        maxId = idNum;
                    }
                } catch (NumberFormatException ex) {
                    // Ignoramos IDs no numéricos si los hubiera
                }
            }
        } catch (Exception e) {
            System.err.println("Error al calcular ID: " + e.getMessage());
        }
        // Retornamos el máximo encontrado + 1
        return String.valueOf(maxId + 1);
    }

    // Criterio RA5.g: Añadimos un nuevo nodo producto al XML.
    private static void insertarProductoXML(File archivo, String id, String nom, String desc, String prec, String st) {
        try {
            // Criterio RA5.d: Establecemos la conexión (Cargamos el DOM).
            Document doc = cargarDOM(archivo);
            Element root = doc.getDocumentElement();

            // Creamos el nodo producto
            Element producto = doc.createElement("producto");
            producto.setAttribute("id", id); // Usamos el ID autoincremental

            // Añadimos los hijos
            crearElemento(doc, producto, "nombre", nom);
            crearElemento(doc, producto, "descripcion", desc);
            crearElemento(doc, producto, "precio", prec);
            crearElemento(doc, producto, "stock", st);

            // Añadimos el producto a la raíz
            root.appendChild(producto);

            // Guardamos cambios
            guardarXML(doc, archivo);
            System.out.println(" -> [OK] Producto añadido al XML con ID " + id);

        } catch (Exception e) {
            System.err.println("Error al insertar: " + e.getMessage());
        }
    }

    // Método auxiliar para crear nodos hijos
    private static void crearElemento(Document doc, Element padre, String etiqueta, String valor) {
        Element elem = doc.createElement(etiqueta);
        elem.setTextContent(valor);
        padre.appendChild(elem);
    }

    // Criterio RA5.e: Recorremos el DOM para consultar datos.
    private static void leerProductosXML(File archivo) {
        try {
            Document doc = cargarDOM(archivo);
            NodeList lista = doc.getElementsByTagName("producto");

            if (lista.getLength() == 0) System.out.println("(No hay productos en el XML)");

            for (int i = 0; i < lista.getLength(); i++) {
                Element e = (Element) lista.item(i);
                String id = e.getAttribute("id");
                String nombre = e.getElementsByTagName("nombre").item(0).getTextContent();
                String precio = e.getElementsByTagName("precio").item(0).getTextContent();
                String stock = e.getElementsByTagName("stock").item(0).getTextContent();

                System.out.printf("ID: %s | %s | %s€ | Stock: %s%n", id, nombre, precio, stock);
            }
        } catch (Exception e) {
            System.err.println("Error al leer: " + e.getMessage());
        }
    }

    // Criterio RA5.g: Buscamos un nodo por ID y modificamos su contenido.
    private static void modificarPrecioXML(File archivo, String idBuscado, String nuevoPrecio) {
        try {
            Document doc = cargarDOM(archivo);
            NodeList lista = doc.getElementsByTagName("producto");
            boolean encontrado = false;

            for (int i = 0; i < lista.getLength(); i++) {
                Element e = (Element) lista.item(i);
                if (e.getAttribute("id").equals(idBuscado)) {
                    // Modificamos el nodo precio
                    e.getElementsByTagName("precio").item(0).setTextContent(nuevoPrecio);
                    encontrado = true;
                    System.out.println(" -> [OK] Precio actualizado.");
                    break;
                }
            }

            if (encontrado) {
                guardarXML(doc, archivo);
            } else {
                System.out.println(" -> [ERROR] Producto no encontrado.");
            }

        } catch (Exception e) {
            System.err.println("Error al modificar: " + e.getMessage());
        }
    }

    // Criterio RA5.f: Añadimos y eliminamos colecciones.
    private static void gestionarColecciones() {
        System.out.println("--- Gestión de Colecciones ---");
        File carpetaBackup = new File("coleccion_backups");

        // Creamos una colección (Directorio)
        if (!carpetaBackup.exists()) {
            if (carpetaBackup.mkdir()) {
                System.out.println("1. Hemos creado la colección 'coleccion_backups'.");
            }
        } else {
            System.out.println("1. La colección ya existía.");
        }

        // Simulamos eliminarla
        if (carpetaBackup.delete()) {
            System.out.println("2. Hemos eliminado la colección 'coleccion_backups' (Prueba de borrado).");
        } else {
            System.out.println("2. No pudimos borrar la colección (puede que no esté vacía).");
        }
    }

    // --- MÉTODOS AUXILIARES (RA5.c y RA5.d) ---

    // Cargamos el archivo XML en memoria (DOM)
    private static Document cargarDOM(File archivo) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(archivo);
    }

    // Guardamos los cambios de memoria al archivo físico
    private static void guardarXML(Document doc, File archivo) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(archivo);
        transformer.transform(source, result);
    }
}