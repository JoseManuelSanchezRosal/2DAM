package RepasoFicheros2;
import java.io.*;
import java.util.ArrayList;
/*Crear un fichero con un número en cada fila
Guardar los números en un ArrayList<Integer> y mostrar la suma y la media.*/
public class Ej2 {
    public static void main(String[] args) {
        File fichero = new File("ficheros/src/RepasoFicheros2/ej2.txt");
        ArrayList<Integer> numeros = new ArrayList<>();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
            for(int i = 1; i <=10; i++){
                bw.write(i+"\n");
            }
            bw.close();
        }catch (IOException e){
            System.out.println("Error al escribir en el archivo" + e.getMessage());
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(fichero));
            String linea;
            int suma = 0;
            while ((linea = br.readLine())!=null){
                numeros.add(Integer.parseInt(linea));
                suma+=Integer.parseInt(linea);
            }
            System.out.println("La suma es total es de: " + suma);
            System.out.println("La media es de: " + (suma/numeros.size()));
        }catch (IOException e){
            System.out.println("Error al leer " + e.getMessage());
        }
    }
}