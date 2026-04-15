package ActividadEvaluableSockets;

import java.io.BufferedReader;
import java.io.IOException;

public class HiloLecturaCliente extends Thread {
    private BufferedReader in;

    public HiloLecturaCliente(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            String mensajeServidor;
            while ((mensajeServidor = in.readLine()) != null) {
                if (mensajeServidor.equals("FIN")) {
                    System.out.println("El servidor ha cerrado la conexion.");
                    System.exit(0);
                }
                System.out.println(mensajeServidor);
            }
        } catch (IOException e) {
            System.out.println("Conexion terminada.");
            System.exit(0);
        }
    }
}