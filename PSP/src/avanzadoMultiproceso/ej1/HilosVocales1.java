package avanzadoMultiproceso.ej1;

//Crear un programa que sea capaz de contar cuántas vocales hay en un fichero.
//El programa padre debe lanzar cinco hilos hijo, donde cada uno de ellos se
//ocupará de contar una vocal concreta (que puede ser minúscula o mayúscula).
//Cada subproceso que cuenta vocales deberá dejar el resultado en un fichero. El
//programa padre se ocupará de recuperar los resultados de los ficheros, sumar
//todos los subtotales y mostrar el resultado final en pantalla.

import java.io.File;
import java.io.FileReader;

public class HilosVocales1 {
    public static void main(String[] args) {
        File archivo = new File("src/avanzadoMultiproceso/cuentavocales.txt");
        int contadorA = 0;
        int contadorE = 0;
        int contadorI = 0;
        int contadorO = 0;
        int contadorU = 0;
        try {
            FileReader fr = new FileReader(archivo);
            int charEntero;
            while ((charEntero = fr.read()) != -1) {
                char caracter = (char) charEntero;
                String aTexto = String.valueOf(caracter);
                if (aTexto.equalsIgnoreCase("a")) {
                    contadorA++;
                } else if (aTexto.equalsIgnoreCase("e")) {
                    contadorE++;
                } else if (aTexto.equalsIgnoreCase("i")) {
                    contadorI++;
                } else if (aTexto.equalsIgnoreCase("o")) {
                    contadorO++;
                } else if (aTexto.equalsIgnoreCase("u")) {
                    contadorU++;
                }
            }
                fr.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("La letra a aparece : " + contadorA + " veces");
        System.out.println("La letra e aparece : " + contadorE + " veces");
        System.out.println("La letra i aparece : " + contadorI + " veces");
        System.out.println("La letra o aparece : " + contadorO + " veces");
        System.out.println("La letra u aparece : " + contadorU + " veces");

    }
}