package ejercicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HiloFicheros3concurrente  extends Thread{
    // Creamos atributo para pasarle al objeto hilo la ruta por parametro.
    private String ruta;
    public HiloFicheros3concurrente(String ruta){
        this.ruta = ruta;
    }
    @Override
    public void run() {

        //RUTAS ARCHIVOS DONDE GUARDAREMOS LAS FALLAS.TXT
        File archivo = new File(ruta);
        int contador = 0;
        int info = 0;
        int warn = 0;
        int error = 0;

        try {
            BufferedReader br1 = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br1.readLine()) != null) {
                contador++;
                String[] errores;
                errores = linea.split(";");
                if (errores[0].equalsIgnoreCase("info")) {
                    info++;
                } else if (errores[0].equalsIgnoreCase("warn")) {
                    warn++;
                } else {
                    error++;
                }
            }
            br1.close();
            System.out.println("En el archivo de la " + archivo.getName() + " hay: " + contador + " lineas");
            System.out.println("Info aparece: " + info + "\n" + "Warn aparece " + warn + "\n" + "Error aparece: " + error);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        HiloFicheros3concurrente concurrente = new HiloFicheros3concurrente("src/ejercicios/fallos1.txt");
        HiloFicheros3concurrente concurrente2 = new HiloFicheros3concurrente("src/ejercicios/fallos2.txt");
        Long inicio = System.currentTimeMillis();
        concurrente.start();
        concurrente2.start();
        try {
            concurrente.join();
            concurrente2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Long fin = System.currentTimeMillis();
        System.out.println("El tiempo total modo concurrente es: " + (fin - inicio) + " MS");
    }
}