package avanzadoMultiproceso.ProdConsBuffer;

import java.util.ArrayList;

// Clase Recurso que tendra un buffer de almacenamiento que mas tarde limitaremos en los metodos.
public class Recurso {
    private ArrayList<Integer> buffer = new ArrayList<>();

    // Metodo syncronized para control de concurrencia al metodo PRODUCIR, con buffer de produccion maximo de 3 unidades.
    // "Mientras el tamanio del buffer sea igual a 3, el metodo que intente acceder a PRODUCIR, espera.
    public synchronized void producir() throws InterruptedException {
        while (buffer.size() == 3) {
            wait();
        }
        // Si el buffer tiene 2 o menos elementos, el proceso (productor 1 u 2, producte y almacena en buffer)
        buffer.add(1);
        System.out.println("Productor " + Thread.currentThread().getName() + " produjo");
        System.out.println("Contenido del buffer: " + buffer);
        // Una vez producido, notifica al proceso que este DURMIENDO con wait()
        notifyAll();
    }

    // Metodo para consumir RECURSO (buffer), mientras el buffer este vacio, el proceso(consumidor 1 u 2) espera.
    public synchronized void consumir() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        // Si el buffer contiene algun elemento, el proceso actual, consume (resta 1 al buffer)
        buffer.removeFirst();
        System.out.println("Consumidor " + Thread.currentThread().getName() + " consumio");
        System.out.println("Contenido del buffer: " + buffer);
        // Una vez consumido, notifica el recurso que este dormido (en este caso alguno de los 2 productores listos para producir)
        notifyAll();
    }

    // To String para saber el contenido del buffer
    @Override
    public String toString() {
        return "Recurso{" +
                "buffer=" + buffer +
                '}';
    }
}