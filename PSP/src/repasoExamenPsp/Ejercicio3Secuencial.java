package repasoExamenPsp;

import java.io.*;

public class Ejercicio3Secuencial {
    public static void main(String[] args) {
        File archivo1 = new File("src/repasoExamenPsp/fallos1.txt");
        File archivo2 = new File("src/repasoExamenPsp/fallos2.txt");

        try {
            BufferedWriter br1 = new BufferedWriter(new FileWriter(archivo1));
            BufferedWriter br2 = new BufferedWriter(new FileWriter(archivo2));
            br1.write("INFO;Inicio del programa");
            br1.newLine();
            br1.write("ERROR;No se pudo conectar a la BD");
            br1.newLine();
            br1.write("INFO;Usuario logueado");
            br1.newLine();
            br1.write("WARN;Memoria al 80%");
            br1.newLine();
            br1.write("ERROR;Archivo no encontrado");
            br1.newLine();

            br2.write("INFO;Inicio del programa");
            br2.newLine();
            br2.write("ERROR;No se pudo conectar a la BD");
            br2.newLine();
            br2.write("ERROR;Usuario logueado");
            br2.newLine();
            br2.write("ERROR;Memoria al 80%");
            br2.newLine();
            br2.write("ERROR;Archivo no encontrado");
            br2.newLine();

            br2.close();
            br1.close();

        } catch (Exception e) {
        }

        try {
            BufferedReader br1 = new BufferedReader(new FileReader(archivo1));
            int contador1 = 0;
            int info1 = 0;
            int warn1 = 0;
            int error1 = 0;

            String linea1;
            while ((linea1 = br1.readLine()) != null) {
                String[] palabras = linea1.trim().split(";");
                if (palabras[0].equalsIgnoreCase("info")) {
                    info1++;
                    contador1++;
                } else if (palabras[0].equalsIgnoreCase("warn")) {
                    warn1++;
                    contador1++;
                } else {
                    error1++;
                    contador1++;
                }
            }
            br1.close();

            System.out.println("El archivo " + archivo1.getName() + " tiene " + contador1 + " registros");
            System.out.println("INFO = " + info1);
            System.out.println("WARN = " + warn1);
            System.out.println("ERROR = " + error1);

        } catch (Exception e) {
        }

        try {
            BufferedReader br2 = new BufferedReader(new FileReader(archivo2));
            int contador2 = 0;
            int info2 = 0;
            int warn2 = 0;
            int error2 = 0;
            String linea2;
            while ((linea2 = br2.readLine()) != null){
                String[] palabras2 = linea2.trim().split(";");
                if (palabras2[0].equalsIgnoreCase("info")){
                    info2++;
                    contador2++;
                } else if (palabras2[0].equalsIgnoreCase("warn")) {
                    warn2++;
                    contador2++;
                }else {
                    error2++;
                    contador2++;
                }
                System.out.println();
            }
            br2.close();
            System.out.println("El archivo " + archivo2.getName() + " tiene " + contador2 + " registros");
            System.out.println("INFO = " + info2);
            System.out.println("WARN = " + warn2);
            System.out.println("ERROR = " + error2);

        } catch (Exception e) {
        }
    }
}