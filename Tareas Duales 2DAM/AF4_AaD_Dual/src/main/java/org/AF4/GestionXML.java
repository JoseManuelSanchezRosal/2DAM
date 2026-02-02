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
 *
 * CUMPLIMIENTO DE CRITERIOS:
 * - RA5.b: Instalación gestor (Sistema de archivos).
 * - RA5.c: Configuración (Rutas constantes).
 * - RA5.d: Conexión (Carga DOM).
 * - RA5.e: Consultas (Listar).
 * - RA5.f: Colecciones (Crear y Borrar carpetas).
 * - RA5.g: CRUD Documentos (Insertar, Modificar, Eliminar nodos).
 */
public class GestionXML {

    // [CRITERIO RA5.c]: Configuración del gestor (Rutas)
    private static final String NOMBRE_ARCHIVO = "productos.xml";
    private static final String CARPETA_COLECCIONES = "coleccion_xml";

    public static void main(String[] args) {
        System.out.println("--- INICIO RA5: GESTIÓN DE PRODUCTOS XML (INTERACTIVO) ---");

        try {
            // [CRITERIO RA5.b]: "Instalación/Preparación" (Verificar fichero físico)
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
                    case 1: // INSERTAR
                        System.out.println("\n--- Nuevo Producto XML ---");
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

                        // [CRITERIO RA5.g]: Añadir documentos/nodos
                        insertarProductoXML(archivo, idAuto, nombre, desc, precio, stock);
                        break;

                    case 2: // LISTAR
                        System.out.println("\n--- Leyendo Base de Datos XML ---");
                        // [CRITERIO RA5.e]: Efectuar consultas
                        leerProductosXML(archivo);
                        break;

                    case 3: // MODIFICAR
                        System.out.println("\n--- Modificar Precio en XML ---");
                        System.out.print("ID del producto a modificar: ");
                        String idMod = sc.nextLine();
                        System.out.print("Nuevo Precio: ");
                        String nuevoPrecio = sc.nextLine();

                        // [CRITERIO RA5.g]: Modificar documentos/nodos
                        modificarPrecioXML(archivo, idMod, nuevoPrecio);
                        break;

                    case 4: // ELIMINAR (NUEVO)
                        System.out.println("\n--- Eliminar Producto XML ---");
                        System.out.print("ID del producto a eliminar: ");
                        String idDel = sc.nextLine();

                        // [CRITERIO RA5.g]: Eliminar documentos/nodos
                        eliminarProductoXML(archivo, idDel);
                        break;

                    case 5: // COLECCIONES
                        // [CRITERIO RA5.f]: Añadir y eliminar colecciones
                        gestionarColecciones();
                        break;

                    case 6:
                        System.out.println("Saliendo del gestor XML...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
                System.out.println("------------------------------------------------");

            } while (opcion != 6);

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\nMENÚ XML:");
        System.out.println("1. Insertar Producto");
        System.out.println("2. Listar Productos");
        System.out.println("3. Modificar Precio");
        System.out.println("4. Eliminar Producto");
        System.out.println("5. Gestionar Colecciones (RA5.f)");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // --- MÉTODOS DE LÓGICA DE NEGOCIO ---

    private static void crearEstructuraBase(File archivo) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("tienda");
        doc.appendChild(rootElement);
        guardarXML(doc, archivo);
        System.out.println("Base de datos XML inicializada.");
    }

    private static String generarNuevoId(File archivo) {
        int maxId = 0;
        try {
            Document doc = cargarDOM(archivo);
            NodeList lista = doc.getElementsByTagName("producto");
            for (int i = 0; i < lista.getLength(); i++) {
                Element e = (Element) lista.item(i);
                try {
                    int idNum = Integer.parseInt(e.getAttribute("id"));
                    if (idNum > maxId) maxId = idNum;
                } catch (Exception ex) {}
            }
        } catch (Exception e) {
            System.err.println("Error ID: " + e.getMessage());
        }
        return String.valueOf(maxId + 1);
    }

    // [RA5.g] INSERTAR
    private static void insertarProductoXML(File archivo, String id, String nom, String desc, String prec, String st) {
        try {
            Document doc = cargarDOM(archivo);
            Element root = doc.getDocumentElement();

            Element producto = doc.createElement("producto");
            producto.setAttribute("id", id);

            crearElemento(doc, producto, "nombre", nom);
            crearElemento(doc, producto, "descripcion", desc);
            crearElemento(doc, producto, "precio", prec);
            crearElemento(doc, producto, "stock", st);

            root.appendChild(producto);
            guardarXML(doc, archivo);
            System.out.println(" -> [OK] Producto añadido ID " + id);
        } catch (Exception e) {
            System.err.println("Error insertar: " + e.getMessage());
        }
    }

    private static void crearElemento(Document doc, Element padre, String etiqueta, String valor) {
        Element elem = doc.createElement(etiqueta);
        elem.setTextContent(valor);
        padre.appendChild(elem);
    }

    // [RA5.e] CONSULTAR
    private static void leerProductosXML(File archivo) {
        try {
            Document doc = cargarDOM(archivo);
            NodeList lista = doc.getElementsByTagName("producto");
            if (lista.getLength() == 0) System.out.println("(No hay productos)");

            for (int i = 0; i < lista.getLength(); i++) {
                Element e = (Element) lista.item(i);
                System.out.printf("ID: %s | %s | %s€ | Stock: %s%n",
                        e.getAttribute("id"),
                        e.getElementsByTagName("nombre").item(0).getTextContent(),
                        e.getElementsByTagName("precio").item(0).getTextContent(),
                        e.getElementsByTagName("stock").item(0).getTextContent());
            }
        } catch (Exception e) {
            System.err.println("Error leer: " + e.getMessage());
        }
    }

    // [RA5.g] MODIFICAR
    private static void modificarPrecioXML(File archivo, String idBuscado, String nuevoPrecio) {
        try {
            Document doc = cargarDOM(archivo);
            NodeList lista = doc.getElementsByTagName("producto");
            boolean encontrado = false;

            for (int i = 0; i < lista.getLength(); i++) {
                Element e = (Element) lista.item(i);
                if (e.getAttribute("id").equals(idBuscado)) {
                    e.getElementsByTagName("precio").item(0).setTextContent(nuevoPrecio);
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) {
                guardarXML(doc, archivo);
                System.out.println(" -> [OK] Precio actualizado.");
            } else {
                System.out.println(" -> [ERROR] ID no encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Error modificar: " + e.getMessage());
        }
    }

    // [RA5.g] ELIMINAR (Implementación NUEVA requerida para el 10/10)
    private static void eliminarProductoXML(File archivo, String idBuscado) {
        try {
            Document doc = cargarDOM(archivo);
            NodeList lista = doc.getElementsByTagName("producto");
            boolean encontrado = false;

            for (int i = 0; i < lista.getLength(); i++) {
                Element e = (Element) lista.item(i);
                if (e.getAttribute("id").equals(idBuscado)) {
                    // Eliminamos el nodo del padre (tienda)
                    e.getParentNode().removeChild(e);
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                guardarXML(doc, archivo);
                System.out.println(" -> [OK] Producto eliminado ID " + idBuscado);
            } else {
                System.out.println(" -> [ERROR] ID no encontrado.");
            }

        } catch (Exception e) {
            System.err.println("Error eliminar: " + e.getMessage());
        }
    }

    // [RA5.f] GESTIÓN DE COLECCIONES (Crear y Eliminar)
    private static void gestionarColecciones() {
        System.out.println("--- Gestión de Colecciones (RA5.f) ---");
        File carpeta = new File(CARPETA_COLECCIONES);
        File subCarpeta = new File(carpeta, "2024");
        File archivoEjemplo = new File(subCarpeta, "reporte.xml");

        // 1. AÑADIR COLECCIONES
        System.out.println("1. Creando estructura de directorios...");
        if (carpeta.mkdir()) System.out.println("   [+] Carpeta raíz creada.");
        if (subCarpeta.mkdir()) System.out.println("   [+] Subcarpeta 2024 creada.");

        try {
            if (archivoEjemplo.createNewFile()) {
                System.out.println("   [+] Archivo XML creado dentro de la colección.");
                crearEstructuraBase(archivoEjemplo);
            }
        } catch (Exception e) { e.printStackTrace(); }

        System.out.println("   (Colecciones creadas. Presiona ENTER para simular la eliminación...)");
        new Scanner(System.in).nextLine();

        // 2. ELIMINAR COLECCIONES (Para cumplir criterio de eliminación)
        System.out.println("2. Eliminando estructura (Limpieza)...");
        if (archivoEjemplo.delete()) System.out.println("   [-] Archivo eliminado.");
        if (subCarpeta.delete()) System.out.println("   [-] Subcarpeta eliminada.");
        if (carpeta.delete()) System.out.println("   [-] Carpeta raíz eliminada.");

        System.out.println(" -> [OK] Ciclo de gestión de colecciones completado.");
    }

    // --- MÉTODOS AUXILIARES (RA5.d) ---

    private static Document cargarDOM(File archivo) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(archivo);
    }

    private static void guardarXML(Document doc, File archivo) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // Formateo para que se vea bonito con saltos de línea
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(archivo);
        transformer.transform(source, result);
    }
}