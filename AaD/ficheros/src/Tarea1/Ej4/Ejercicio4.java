package Tarea1.Ej4;

import java.io.*;
import java.util.ArrayList;

//Crear fichero ejercicio4.txt con la siguiente estructura: Juan;25;programador.
//Leer el fichero, crear objetos empleado y guardarlos en un ArrayList<Empleado>,
//Mostrar luego todos los empleados.
public class Ejercicio4 {
    public static void main(String[] args) {
        String ruta = new String("ficheros/src/Tarea1/ejercicio4.txt");

        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(ruta));
            br.append("Juan;25;Programador\n");
            br.append("Ana;30;Disenadora\n");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String linea = null;
        //Creamos un ArrayList de empleados vacio
        ArrayList<empleado> empleados = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            //Mientras nuestro txt tenga lineas, las separamos y las vamos instanciando en empleados.
            while ((linea=br.readLine()) != null) {
                String[] datos = linea.split(";");
                empleados.add(new empleado(datos[0], Integer.parseInt(datos[1]), datos[2]));

            }
        }catch(IOException e){
            e.printStackTrace();
        }
        //Sacamos por pantalla los empleados del ArrayList de empleados.
        //DUDA, COMO QUIERE QUE SAQUEMOS LA INFO DE LOS EMPLEADOS?
        System.out.println(empleados);
    }
}