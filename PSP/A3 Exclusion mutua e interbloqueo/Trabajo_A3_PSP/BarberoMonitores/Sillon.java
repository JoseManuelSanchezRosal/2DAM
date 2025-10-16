package Trabajo_A3_PSP.BarberoMonitores;

import java.util.ArrayList;
import java.util.Random;
// Monitor es el metodo para acceder al recurso compartido, en este caso usamos el metodo o bloque syncronized
// El sillon es el recurso compartido.
public class Sillon {
    // Declaramos el ArrayList de sillas disponibles en nuestra Barberia y una variable de control de barbería vacía.
    private ArrayList<Integer> sillas = new ArrayList<>();
    private boolean barberVacia = true;

    public ArrayList<Integer> getSillas() {
        return sillas;
    }
    // l
    public void setSillas(ArrayList<Integer> sillas) {
        this.sillas = sillas;
    }

    // Bloque  para Cortar Pelo
    public synchronized void CortarPelo() throws InterruptedException {
        // Si no hay clientes....el barbero se queda dormido:
        while (sillas.size() == 0) {
            System.out.println("No hay clientes, el barbero sigue durmiendo...");
            wait(5000);

        }
        // Si entra un cliente y hay sitio se sienta y el barbero lo atiende:
        System.out.println("Barbero atiende al cliente " + sillas.get(0) + " y se marcha...");
        // Simulamos el tiempo de corte de pelo:
        Thread.sleep(2000);
        // Cliente satisfecho se va.....
        sillas.remove(0);
        // Mostramos sillas ocupadas:
        System.out.print("Sillas ocupadas: " + sillas.size() + "     ");
        System.out.println(sillas);
        System.out.println("---------------------------------------------------------------");
        notifyAll();
        // Si se queda el sillón y la pelu vacías.....el barbero se queda dormido:
        if (barberVacia == true && sillas.size() == 0) {
            System.out.println("El barbero se queda dormido...");
        }
    }

    // Bloque syncronized para Sentarse en Sillón:
    public synchronized void SentarseSillon() throws InterruptedException {
        while (sillas.size() == 3) {
            System.out.println("Llega un cliente > no hay sillas disponibles > Y SE MARCHA");
            wait();
        }
        // Si hay sitio libre, el cliente se sienta...
        // Vamos creando numeros y los asociamos a los clientes que van entrando:
        Random random = new Random();
        int cliente = random.nextInt(50);
        sillas.add(cliente);
        // Sacamos por pantalla el cliente que llega y se sienta:
        System.out.println("Llega el cliente " + cliente + " y SE SIENTA");
        // Al sentarse el cliente, mostramos las sillas ocupadas con el número de cliente:
        System.out.print("Sillas ocupadas: " + sillas.size() + "     ");
        System.out.println(sillas);
        System.out.println("----------------------------------------------------------------");
        //PARA REPRODUCIR UN DEADLOCK >> BORRAR EL NOTIFY.
        notifyAll();
    }
   // ToString:
    @Override
    public String toString() {
        return "Sillon{" +
                "sillas=" + sillas +
                ", peluVacia=" + barberVacia +
                '}';
    }
}