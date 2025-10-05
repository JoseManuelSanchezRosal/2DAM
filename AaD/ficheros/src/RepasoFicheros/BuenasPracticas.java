package RepasoFicheros;
import java.io.*;
    /**
     * 🧹 Ejemplo de buenas prácticas en manejo de archivos en Java
     * Autor: [Tu nombre]
     * Fecha: [Actualiza la fecha]
     *
     * Este archivo demuestra:
     * ✅ Uso de try-with-resources
     * ✅ Verificación de existencia del archivo
     * ✅ Uso de BufferedReader / BufferedWriter
     * ✅ Evitar lectura/escritura carácter a carácter en archivos grandes
     */
public class BuenasPracticas {
        public static void main(String[] args) {
        // Rutas de ejemplo (puedes cambiarlas según tu sistema)
        String archivoEntrada = "entrada.txt";
        String archivoSalida = "salida.txt";

        comprobarExistenciaArchivo(archivoEntrada);
        leerArchivoConBufferedReader(archivoEntrada);
        escribirArchivoConBufferedWriter(archivoSalida);
        evitarLecturaCaracterPorCaracter(archivoEntrada);
    }
        /**
         * ✅ Comprobar si el archivo existe antes de leerlo
         */
        public static void comprobarExistenciaArchivo(String ruta) {
        System.out.println("\n=== Comprobando existencia del archivo ===");
        File archivo = new File(ruta);

        if (archivo.exists() && archivo.isFile()) {
            System.out.println("El archivo \"" + ruta + "\" existe. Tamaño: " + archivo.length() + " bytes");
        } else {
            System.out.println("El archivo \"" + ruta + "\" no existe o no es válido.");
        }
    }
        /**
         * ✅ Usar try-with-resources y BufferedReader para leer archivos de forma eficiente
         */
        public static void leerArchivoConBufferedReader(String ruta) {
        System.out.println("\n=== Leyendo archivo con BufferedReader ===");

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (FileNotFoundException e) {
            System.err.println("El archivo no se encontró: " + ruta);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
        /**
         * ✅ Usar BufferedWriter para escribir en archivos de forma eficiente
         */
        public static void escribirArchivoConBufferedWriter(String ruta) {
        System.out.println("\n=== Escribiendo archivo con BufferedWriter ===");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            bw.write("Primera línea escrita correctamente.");
            bw.newLine();
            bw.write("Segunda línea: usando BufferedWriter mejora el rendimiento.");
            bw.newLine();
            bw.write("Fin del archivo.");
            System.out.println("Archivo escrito exitosamente en: " + ruta);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
        /**
         * 🚫 Evitar leer carácter a carácter cuando el archivo es grande.
         * ✅ En su lugar, usar BufferedReader (ejemplo comparativo)
         */
        public static void evitarLecturaCaracterPorCaracter(String ruta) {
        System.out.println("\n=== Comparando lectura carácter a carácter vs BufferedReader ===");

        // ❌ Mala práctica: leer carácter a carácter (ineficiente)
        long inicioMala = System.currentTimeMillis();
        try (FileReader fr = new FileReader(ruta)) {
            while (fr.read() != -1) {
                // No hace nada, solo lee
            }
        } catch (IOException e) {
            System.err.println("Error en lectura carácter a carácter: " + e.getMessage());
        }
        long finMala = System.currentTimeMillis();

        // ✅ Buena práctica: leer línea a línea con BufferedReader
        long inicioBuena = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            while (br.readLine() != null) {
                // No hace nada, solo lee
            }
        } catch (IOException e) {
            System.err.println("Error en lectura con BufferedReader: " + e.getMessage());
        }
        long finBuena = System.currentTimeMillis();

        System.out.println("Tiempo lectura carácter a carácter: " + (finMala - inicioMala) + " ms");
        System.out.println("Tiempo lectura con BufferedReader: " + (finBuena - inicioBuena) + " ms");
    }
}
