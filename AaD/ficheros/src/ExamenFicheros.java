import java.io.*;
import java.util.Scanner;


// TERMINADO AMIN, EN TOTAL 3 HORAS APROXIMADAMENTE DESPUES DE LA ACLARACION DE HOY.

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
                Scanner s = new Scanner(System.in);
                System.out.println("Ingrese nombre a buscar: ");
                String nombre = s.nextLine();
                System.out.println("Ingrese apellido a buscar: ");
                String apellidos = s.nextLine();
                int id = devolverID(nombre, apellidos);
                if(id == 0){
                    System.out.println(("El alumno con nombre: " + nombre + " y apellidos: " + apellidos + ", no se encuentra en el archivo"));
                }else System.out.println("El nombre: " + nombre + " y apellidos: " + apellidos + " tiene el ID: " + id );

            } else if (opcion == 3) {
                insertarNotas();

            } else if (opcion == 4) {
                calcularMedia();

            } else if (opcion ==0){
                System.out.println("Saliendo del programa");
            }else{
                System.out.println("Ingrese una opcion valida");

            }
        } while (opcion != 0 && menu == true);
    }

    private static void calcularMedia() {

        File notas = new File("ficheros/src/notas.txt");

        Scanner s = new Scanner(System.in);
        System.out.println("Ingrese nombre del alumno: ");
        String nombre = s.nextLine();
        System.out.println("Ingrese apellidos del alumno: ");
        String apellidos = s.nextLine();

        int id = devolverID(nombre, apellidos);

        if(id == 0){
            System.out.println(("El alumno con nombre: " + nombre + " y apellidos: " + apellidos + ", no se encuentra en el archivo"));
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(notas));
            double media = 0;
            String linea;
            while ((linea = br.readLine())!=null) {
                String[] palabras = linea.split("-");
                if (id == Integer.parseInt(palabras[0])) {
                    String[] notasAlumnos = palabras[1].split(";");
                    int numeroNotas = notasAlumnos.length;
                    double suma = 0;
                    for (int i = 0; i < numeroNotas; i++) {
                        suma += Double.parseDouble(notasAlumnos[i]);
                    }
                    media = (double) suma / numeroNotas;
                }
            }
            System.out.println("La nota media del alumno " + nombre + " es de: " + media);

        }catch (Exception e){
            System.out.println("Error al leer el archivo " + e.getMessage());
        }
    }
    private static void insertarNotas() {
        File notas = new File("ficheros/src/notas.txt");

        Scanner s = new Scanner(System.in);
        System.out.println("Ingrese nombre del alumno: ");
        String nombre = s.nextLine();
        System.out.println("Ingrese apellidos del alumno: ");
        String apellidos = s.nextLine();

        int id = devolverID(nombre, apellidos);

        if(id == 0){
            System.out.println(("El alumno con nombre: " + nombre + " y apellidos: " + apellidos + ", no se encuentra en el archivo"));
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(notas, true));
            bw.write(id+ "-");
            System.out.println("Introduzca notas del alumno " + id + " (separado por ;)");
            String notasAlumno = s.nextLine();
            bw.write(notasAlumno);
            bw.newLine();

            bw.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int devolverID(String nombre, String apellidos) {
        File archivo = new File("ficheros/src/Alumnos.txt");
        int id = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine())!= null){
                String[] palabras = linea.trim().split("-");
                if(nombre.equals(palabras[1]) && apellidos.equals(palabras[2])){
                    id = Integer.parseInt(palabras[0]);
                }
            }
        }catch (IOException e){
            System.out.println("Error al leer el archivo "+e.getMessage());
        }
        return id;
    }

    private static int autoIncremento(String archivo){
        int contador = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine())!=null){
                contador++;
            }
            br.close();
        }catch (Exception e){
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return contador+1;
    }

    private static void agregarAlumno() {
        Scanner aa = new Scanner(System.in);
        int insercion = 1;
        int contador = autoIncremento("ficheros/src/Alumnos.txt");
        File archivo = new File("ficheros/src/Alumnos.txt");
        do {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
                bw.write(String.valueOf(contador + "-"));
                System.out.println("Introduzca nombre: ");
                String nombre = aa.nextLine();
                bw.write(nombre + "-");
                System.out.println("Introduzca los apellidos: ");
                String apellidos = aa.nextLine();
                bw.write(apellidos +"-");
                System.out.println("Introduzca fecha nacimiento (dd/mm/aaaa): ");
                String fecha = aa.nextLine();
                bw.write(fecha + "-");
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