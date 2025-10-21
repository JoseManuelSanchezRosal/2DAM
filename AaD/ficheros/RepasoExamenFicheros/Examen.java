package RepasoExamenFicheros;

import java.io.*;
import java.security.spec.ECField;
import java.util.Scanner;

public class Examen {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("--------------------MENU--------------------\n" +
                    "1 - Anadir alumnos\n" +
                    "2 - Devolver ID por nombre y apellido\n" +
                    "3 - Insertar notas\n" +
                    "4 - Calcular media notas alumno\n" +
                    "0 - Salir\n" +
                    "--------------------------------------------)\n" +
                    "INGRESE UNA OPCION: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    anadirAlumno();
                    break;
                case 2:
                    System.out.println("Nombre:");
                    String nombreBuscado = sc.nextLine();
                    System.out.println("Apellidos: ");
                    String apellidosBuscado = sc.nextLine();
                    int id = devolverId(nombreBuscado, apellidosBuscado);
                    // Si nuestro Alumnos.txt empieza por el registro 0, hay que inicializarlo en -1. Si no encuentra alumnos devuelve -1. Sino devuelve el ID del alumno encontrado
                    if (id != 0){
                        System.out.println("El alumno " + nombreBuscado + " " + apellidosBuscado + " tiene el ID: " + id);
                    }
                    break;
                case 3:
                    System.out.println("Nombre:");
                    String nombreAlumno = sc.nextLine();
                    System.out.println("Apellidos: ");
                    String apellidosAlumno = sc.nextLine();
                    int idAlumno = devolverId(nombreAlumno, apellidosAlumno);
                    // SI HAY ALUMNO QUE COINCIDA CON LA BUSQUEDA (QUE LO DA EL METODO ANTERIOR DEVOLVERID) LLAMAMOS A LA FUNCION PARA INSERTAR NOTAS
                    if(idAlumno != 0){
                        insertarNotas(idAlumno);
                    }
                    break;
                case 4:
                    System.out.println("Nombre:");
                    String nombreMedia = sc.nextLine();
                    System.out.println("Apellidos: ");
                    String apellidosMedia = sc.nextLine();
                    int idMedia = devolverId(nombreMedia, apellidosMedia);
                    // SI HAY ALUMNO, LLAMA AL METODO PARA HACER LA MEDIA
                    if(idMedia != 0){
                        calcularMedia(idMedia);
                    }
                    break;
                case 0:
                    System.out.println("Saliendo del programa...........");
                    break;
                default:
                    System.out.println("Introduzca un valor correcto");
            }
        }while (opcion !=0);
    }

    public static void anadirAlumno() {
        File archivo = new File("ficheros/RepasoExamenFicheros/Alumnos.txt");
        Scanner ss = new Scanner(System.in);
        int contador = obtenerUltimoId("ficheros/RepasoExamenFicheros/Alumnos.txt");
        int anadirMas = 1;
        do {

            System.out.println("Nombre: ");
            String nombre = ss.nextLine();
            System.out.println("Apellidos: ");
            String apellidos = ss.nextLine();
            System.out.println("Fecha de nacimiento (dd/mm/aaaa)");
            String fecha = ss.nextLine();
            System.out.println("Clase: ");
            String clase = ss.nextLine();

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
                bw.write(contador + "-" + nombre + "-" + apellidos + "-" + fecha + "-" + clase + "\n");
                System.out.println("Alumno con ID " + contador + " anadido");
                bw.close();
                contador++;

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Pulse 1 para anadir alumno, pulse 0 para salir: ");
            anadirMas = ss.nextInt();
            ss.nextLine();
        }while (anadirMas == 1);
    }

    private static int obtenerUltimoId(String ruta) {
        File archivo = new File(ruta);
        int contador = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine())!= null){
                contador++;
            }
        }catch (Exception e){
            e.getMessage();
        }
        return contador+1;
    }

    private static int devolverId(String nombre, String apellidos) {

        File ruta = new File("ficheros/RepasoExamenFicheros/Alumnos.txt");
        boolean encontrado = false;
        int id = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea;
            while ((linea = br.readLine())!=null){
                String[] palabras = linea.trim().split("-");

                if (nombre.equals(palabras[1].trim()) && apellidos.equals(palabras[2].trim())){
                    id = Integer.parseInt(palabras[0].trim());
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado){
                throw new Exception("El alumno " + nombre + " " + apellidos + " no se encuentra");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return id;
    }

    private static void insertarNotas(int id) {
        File notas = new File("ficheros/RepasoExamenFicheros/notas.txt");
        try {
            Scanner sscc = new Scanner(System.in);
            BufferedWriter bw = new BufferedWriter(new FileWriter(notas, true));
            System.out.println("Introduzca notas (separadas por ;): ");
            String calificaciones = sscc.nextLine();
            bw.write(id + "-" + calificaciones);
            bw.newLine();
            bw.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private static void calcularMedia(int id) {
        File notas = new File("ficheros/RepasoExamenFicheros/notas.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(notas));
            String linea;
            double suma = 0;
            int numeroNotas = 0;

            while ((linea = br.readLine()) !=null){
                String[] palabras = linea.trim().split("-");// DIVIDIMOS LA FILE EN ID Y NOTAS
                String[]notasAlumno = palabras[1].trim().split(";"); // DIVIDIMOS LAS NOTAS INDIVIDUALES
                if(palabras[0].equals(String.valueOf(id))){

                    for (int i = 0; i < notasAlumno.length; i++){
                        suma+=Double.parseDouble(notasAlumno[i]);
                        numeroNotas++;
                    }
                }
            }
            br.close();
            System.out.println("La media del alumno con ID: " + id + " es " + (suma/numeroNotas));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}