package EjerciciosFicheros1dam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*Crear y escribir en un archivo
Crea un programa en Java que cree un archivo de texto llamado datos.txt y escriba dentro de él un mensaje de bienvenida.*/
public class Ej1 {
    public static void main(String[] args) {
        File archivo = new File("ficheros/src/EjerciciosFicheros1dam/datos1.txt");
        if(!archivo.exists()){
            System.out.println("Se ha creado el archivo" + archivo.getName());
        }else {
            System.out.println("El archivo existe");
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("Hola, bienvenido al apasionante mundo de la PROgramacion\n");
            bw.close();
        }catch (IOException e){
            System.out.println("Error al escribir en el archivo");
        }


    }
}
