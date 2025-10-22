package otroRepasoFicheros;

import com.sun.management.UnixOperatingSystemMXBean;

import java.io.*;
import java.util.Scanner;

public class examin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("---------------------MENU---------------------\n" +
                    "1- Agregar alumno\n" +
                    "2- Buscar ID alumno por nombre y apellidos\n" +
                    "3- Insertar notas\n" +
                    "4- Hacer media notas\n" +
                    "0- Salier del programa\n");
            opcion = sc.nextInt();

            sc.nextLine();

            switch (opcion){
                case 1:
                    anadirAlumno();
                    break;
                case 2:
                    System.out.println("Nombre: ");
                    String nombre2 = sc.nextLine();
                    System.out.println("Apellidos: ");
                    String apellidos2 = sc.nextLine();
                    int id2 = buscarID(nombre2, apellidos2);
                    break;

                case 3:
                    System.out.println("Nombre: ");
                    String nombre3 = sc.nextLine();
                    System.out.println("Apellidos: ");
                    String apellidos3 = sc.nextLine();
                    int id3 = buscarID(nombre3, apellidos3);
                    if(id3 != -1){
                        insertarNotas(id3);
                    }

                    break;
                case 4:
                    System.out.println("Nombre: ");
                    String nombre4 = sc.nextLine();
                    System.out.println("Apellidos: ");
                    String apellidos4 = sc.nextLine();
                    int id4 = buscarID(nombre4, apellidos4);
                    if(id4 != -1){
                        hacerMedia(id4);
                    }
                case 0:
                    System.out.println("Saliendo del programa.........");
                default:
                    System.out.println("Ingrese una opcion correcta");
                    break;
            }
        }while(opcion != 0);
    }

    private static void hacerMedia(int id4) {
        File archivo = new File("ficheros/otroRepasoFicheros/notass.txt");
        double suma = 0;
        int contador = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine())!=null){
                String[]palabras = linea.trim().split("\\|");
                String[] datos = palabras[1].trim().split(";");
                for(int i = 0; i < datos.length; i++){
                    suma+= Double.parseDouble(datos[i]);
                    contador++;
                }
            }
            System.out.println("La media del alumno " + id4 + " es " + suma/contador);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void insertarNotas(int id3) {
        File notas = new File("ficheros/otroRepasoFicheros/notass.txt");
        Scanner ssss = new Scanner(System.in);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(notas, true));
            String calificaciones;
            System.out.println("Introduzca las calificaciones (separados por ;)");
            calificaciones = ssss.nextLine();
            bw.write(id3 + "|" + calificaciones);
            bw.newLine();
            bw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static int buscarID(String nombre, String apellidos) {
        File archivo = new File("ficheros/otroRepasoFicheros/alumnoss.txt");
        Scanner sss = new Scanner(System.in);
        int id = -1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine())!=null) {
                String[] datos = linea.trim().split("\\|");
                if (datos[1].equals(nombre) && datos[2].equals(apellidos)) {
                    id = Integer.parseInt(datos[0]);
                    System.out.println("El alumno " + nombre + " " + apellidos + "tiene la id " + id);
                }
            }
            if(id == -1)
                throw new Exception("No se encuentra el alumno");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return id;
    }

    private static void anadirAlumno() {
        File archivo = new File("ficheros/otroRepasoFicheros/alumnoss.txt");
        Scanner ss = new Scanner(System.in);
        int seguirIntroduciendo = 0;
        int id = recuperarUltimaId(archivo);
        do {
            System.out.println("Nombre: ");
            String nombre = ss.nextLine();
            System.out.println("Apellidos: ");
            String apellidos = ss.nextLine();
            System.out.println("Fecha nacimiento (dd/mm/aaaa");
            String fecha = ss.nextLine();
            System.out.println("Clase: ");
            String clase = ss.nextLine();

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
                bw.write(id +"|"+nombre+"|"+apellidos+"|"+fecha+"|"+clase);
                bw.newLine();
                bw.close();
                System.out.println("Alumno anadido....");
                id++;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println("Pulse 1 para seguir anadiendo alumnos, pulse 0 para salir: ");
            seguirIntroduciendo = ss.nextInt();
            ss.nextLine();
        }while(seguirIntroduciendo ==1);
    }

    private static int recuperarUltimaId(File archivo) {
        int id = 0;
        int contador = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) !=null){
                id++;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return id+1;
    }
}
