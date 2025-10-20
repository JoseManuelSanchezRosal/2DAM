import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


// He sido capaz de sacar el apartado de agregar alumno en hora y media. Como no sé sacar la ID por nombre y apellidos, tampoco meter las notas del ese alumno y por tanto tampoco calcular la media. Sí es cierto que podría meter notas en una fila por cada alumno y sacar la media. Pero no desde la ID, no he sabido sacarla....

public class ExamenFicheros {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 10;
        boolean menu = true;
        do {
            System.out.println("-------------------------Menu-------------------------\n" +
                    "1- Agregar alumnos\n" +
                    "2- Devolver el ID de un alumno (nombre y apellido)\n" +
                    "3- Insertar notas\n" +
                    "4- Calcular nota media\n" +
                    "0- Salir" +
                    "------------------------------------------------------\n" +
                    "Ingrese una Opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {
                agregarAlumno();
            } else if (opcion == 2) {
                System.out.println("devolver id");

                /*devolverID();*/
            } else if (opcion == 3) {
                System.out.println("insertar notas");
                insertarNotas();
            } else if (opcion == 4) {
                System.out.println("calcular media notas");
                calcularMedia();
            } else if (opcion ==0){
                System.out.println("Saliendo del programa");
            }else{
                System.out.println("Ingrese una opcion valida");

            }
        } while (opcion != 0 && menu == true);
    }





    private static void calcularMedia() {
    }
    private static void insertarNotas() {
    }

    private int devolverID(String nombre, String apellidos) {
        File archivo = new File("ficheros/src/Alumnos.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine())!= null){

                String[] palabras = linea.split("|");
                if(nombre.equals(palabras[1]) && apellidos.equals(palabras[2])){
                    return Integer.parseInt(palabras[0]);
                }
            }

        }catch (IOException e){
            System.out.println("Error al leer el archivo "+e.getMessage());
        }

        return 0;
    }
    private static void agregarAlumno() {
        Scanner aa = new Scanner(System.in);
        int insercion = 1;
        int contador = 1;
        File archivo = new File("ficheros/src/Alumnos.txt");
        do {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
                bw.write(String.valueOf(contador + " | "));
                System.out.println("Introduzca nombre: ");
                String nombre = aa.nextLine();
                bw.write(nombre + " | ");
                System.out.println("Introduzca los apellidos: ");
                String apellidos = aa.nextLine();
                bw.write(apellidos +" | ");
                System.out.println("Introduzca fecha nacimiento (dd-mm-aaaa): ");
                String fecha = aa.nextLine();
                bw.write(fecha + " |");
                System.out.println("Introduzca la clase del alumno: ");
                String clase = aa.nextLine();
                bw.write(clase + "\n");
                System.out.println("Pulse 1 para ingresar nuevo alumno, Pulse 0 para salir al MENU: ");


                insercion = aa.nextInt();
                if(insercion == Integer.parseInt("1")){
                    contador++;
                }
                aa.nextLine();
                bw.close();

            }catch (IOException e){
                System.out.println("Error al escribir en el archivo " + e.getMessage());
                insercion = 0;
            }
        }while (insercion == 1);
        // Por cierto tampoco he sabido controlar el contador de alumnos a la hora de meter otra vez después de salir (empezaría otra vez por el 1)...
    }
}