package avanzadoMultiproceso.ej1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
//Crear un programa que sea capaz de contar cuántas vocales hay en un fichero.
//El programa padre debe lanzar cinco hilos hijo, donde cada uno de ellos se
//ocupará de contar una vocal concreta (que puede ser minúscula o mayúscula).
//Cada subproceso que cuenta vocales deberá dejar el resultado en un fichero. El
//programa padre se ocupará de recuperar los resultados de los ficheros, sumar
//todos los subtotales y mostrar el resultado final en pantalla.
public class CuentaVocalClase extends Thread{
    private String entrada;
    private String vocal;
    private String salida;

    public CuentaVocalClase(String ruta, String vocal, String salida){
        this.entrada = ruta;
        this.vocal = vocal;
        this.salida = salida;
    }
    public String getEntrada() {
        return entrada;
    }
    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }
    public String getVocal() {
        return vocal;
    }
    public void setVocal(String vocal) {
        this.vocal = vocal;
    }
    public String getSalida() {
        return salida;
    }
    public void setSalida(String salida) {
        this.salida = salida;
    }

    @Override
    public void run(){
        // Definimos las rutas de lectura y escritura e inicializamos contador:
        String ruta = this.getEntrada();
        String salida = this.getSalida();
        int contador = 0;

        // Leemos caracter uno por uno hasta final de archivo
        try {
            FileReader fr = new FileReader(ruta);
            FileWriter fw = new FileWriter(salida);
            // Tenemos que declarar variable tipo entero, luego convertirlo a char y por ultimo a String para poder comparar con la vocal que queremos.
            int charEntero;
            while ((charEntero = fr.read()) !=-1){
                char caracter = (char) charEntero;
                String aTexto = String.valueOf(caracter);
                if (aTexto.equalsIgnoreCase(getVocal())){
                    contador++;
                }
            }
            fw.write(String.valueOf(contador));
            fw.close();
            fr.close();
            System.out.println("Hilo " + this.vocal + " cuenta un total de: " + contador + " veces");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        // Instanciamos los 5 hilos para buscar y contar las vocales del txt.
        CuentaVocalClase A = new CuentaVocalClase("src/avanzadoMultiproceso/cuentavocales.txt", "a", "src/avanzadoMultiproceso/cuentaA.txt");
        CuentaVocalClase E = new CuentaVocalClase("src/avanzadoMultiproceso/cuentavocales.txt", "e", "src/avanzadoMultiproceso/cuentaE.txt");
        CuentaVocalClase I = new CuentaVocalClase("src/avanzadoMultiproceso/cuentavocales.txt", "i", "src/avanzadoMultiproceso/cuentaI.txt");
        CuentaVocalClase O = new CuentaVocalClase("src/avanzadoMultiproceso/cuentavocales.txt", "o", "src/avanzadoMultiproceso/cuentaO.txt");
        CuentaVocalClase U = new CuentaVocalClase("src/avanzadoMultiproceso/cuentavocales.txt", "u", "src/avanzadoMultiproceso/cuentaU.txt");

        A.start();
        E.start();
        I.start();
        O.start();
        U.start();

        try {
            A.join();
            E.join();
            I.join();
            O.join();
            U.join();
            //Esperamos a que finalicen todos los hilos para hacer el recuento total.
            System.out.println("----------------------------------------------------");
            File lecturaA = new File("src/avanzadoMultiproceso/cuentaA.txt");
            int contador =  0;
            // NOTA: SE PUEDE OPTIMIZAR LA LECTURA DE LOS TXT.

            try {
                BufferedReader br = new BufferedReader(new FileReader(lecturaA));
                String linea = br.readLine();
                contador += Integer.parseInt(linea);

                br.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            File lecturaE = new File("src/avanzadoMultiproceso/cuentaE.txt");
            try {
                BufferedReader br = new BufferedReader(new FileReader(lecturaE));
                String linea = br.readLine();
                contador += Integer.parseInt(linea);

                br.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            File lecturaI = new File("src/avanzadoMultiproceso/cuentaI.txt");
            try {
                BufferedReader br = new BufferedReader(new FileReader(lecturaI));
                String linea = br.readLine();
                contador += Integer.parseInt(linea);

                br.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            File lecturaO = new File("src/avanzadoMultiproceso/cuentaO.txt");
            try {
                BufferedReader br = new BufferedReader(new FileReader(lecturaO));
                String linea = br.readLine();
                contador += Integer.parseInt(linea);

                br.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            File lecturaU = new File("src/avanzadoMultiproceso/cuentaU.txt");
            try {
                BufferedReader br = new BufferedReader(new FileReader(lecturaU));
                String linea = br.readLine();
                contador += Integer.parseInt(linea);

                br.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            System.out.println("El programa principal hace un recuento total de: " + contador +" vocales");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}