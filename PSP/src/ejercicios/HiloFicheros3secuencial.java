package ejercicios;

import java.io.*;

//SIN ACABAR!!!!!!!!!!!
public class HiloFicheros3secuencial {
    public static void main(String[] args) {

        //RUTAS ARCHIVOS DONDE GUARDAREMOS LAS FALLAS.TXT
        File ruta1 = new File("src/ejercicios/fallos1.txt");
        File ruta2 = new File("src/ejercicios/fallos2.txt");
        int contador = 0;
        int info = 0;
        int warn = 0;
        int error = 0;

        Long inicio = System.currentTimeMillis();
        try {
            BufferedReader br1 = new BufferedReader(new FileReader(ruta1));
            String linea;
            while ((linea = br1.readLine()) != null) {
                contador++;
                String[] errores;
                errores = linea.split(";");
                if (errores[0].equalsIgnoreCase("info")) {
                    info++;
                } else if (errores[0].equalsIgnoreCase("warn")) {
                    warn++;
                } else {
                    error++;
                }
            }
            br1.close();
            System.out.println("En el archivo de la " + ruta1.getName() + " hay: " + contador + " lineas");
            System.out.println("Info aparece: " + info + "\n" + "Warn aparece " + warn + "\n" + "Error aparece: " + error);
            System.out.println("---------------------------------------------------------");

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        // Archivo 2:
        // Reseteamos los contadores
        contador = 0;
        info = 0;
        warn = 0;
        error = 0;
        try {
            BufferedReader br2 = new BufferedReader(new FileReader(ruta2));
            String linea;
            while ((linea = br2.readLine()) != null) {
                contador++;
                String[] errores;
                errores = linea.split(";");
                if (errores[0].equalsIgnoreCase("info")) {
                    info++;
                } else if (errores[0].equalsIgnoreCase("warn")) {
                    warn++;
                } else {
                    error++;
                }
            }
            br2.close();
            System.out.println("En el archivo de la ruta" + ruta2.getName() + " hay: " + contador + " lineas");
            System.out.println("Info aparece: " + info + " veces" + "\n" + "Warn aparece " + warn + " veces" + "\n" + "Error aparece: " + error + " veces");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Long fin = System.currentTimeMillis();
        System.out.println("El tiempo total modo secuencial es: " + (fin - inicio) + " MS");
    }
}