package procesos;
import java.io.IOException;
//Ejemplo de Proceso de apuntes Tema 1 procesos e hilos:
public class EjemploProceso {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("notepad");
            Process proceso = pb.start();

            int exitCode = proceso.waitFor();
            System.out.println("El proceso termino con codigo: " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();


            //El metodo waitFor necesita del siguiente catch:
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}