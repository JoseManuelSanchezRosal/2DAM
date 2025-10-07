package RepasoFicheros;
import java.io.*;
import java.util.ArrayList;
//Crear un fichero con un número en cada fila
//Guardar los números en un ArrayList<Integer> y mostrar la suma y la media.
public class Ej2 {
    public static void main(String[] args) {
        File archivo = new File("ficheros/src/RepasoFicheros/Ejercicio2.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))){
            int numeros;
            for (int i = 1; i <= 9; i++){//Escribimos numeros del 1 al 9 en diferentes lineas
                bw.write(Integer.toString(i));//Para escribir en fichero, DEBEMOS PASAR EL NUMERO ENTERO (i) A STRING:
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Error al escribir en el archivo");
        }
        //Leemos del archivo
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            ArrayList<Integer> listaNumeros = new ArrayList<>();
            String linea;
            while ((linea = br.readLine()) !=null){
                listaNumeros.add(Integer.parseInt(linea));
            }
            System.out.println("El arrayList de enteros contiene: " + listaNumeros);
            int suma = 0;
            for (int numero : listaNumeros){
                suma+=numero;
            }
            double media = (double) suma/listaNumeros.size();
            System.out.println("La suma total es de: " + suma);
            System.out.println("La media es de: " + media);
            System.out.printf("La media es de: %.2f%n", media);//Media formateada 2 decimales.
        }catch (IOException e){
            System.out.println("Error al leer del archivo " + e.getMessage());
        }
    }
}