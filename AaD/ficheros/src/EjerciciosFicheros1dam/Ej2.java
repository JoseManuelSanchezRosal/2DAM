package EjerciciosFicheros1dam;
import java.io.*;
public class Ej2 {
    public static void main(String[] args) {

        File archivo = new File("ficheros/src/EjerciciosFicheros1dam/datos1.txt");
        try {
            FileReader fr = new FileReader(archivo);
            int c;
            while ((c = fr.read())!= -1){//el metodo read devuelve un INT unicode
                char caracter = (char) c;//aqui parseamos el int a char para sacarlo por pantalla
                System.out.print(caracter);
            }
            System.out.println("Mensaje sacado por pantalla caracter a caracter");
        }catch (IOException e){
            System.out.println("Error al leer del archivo " + e.getMessage());
        }
    }
}