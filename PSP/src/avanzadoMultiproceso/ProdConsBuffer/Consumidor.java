package avanzadoMultiproceso.ProdConsBuffer;

import com.sun.management.UnixOperatingSystemMXBean;

public class Consumidor extends Thread {

    private Recurso recurso;

    public Consumidor (Recurso recurso){
        this.recurso = recurso;

    }

    @Override
    public void run(){
        for (int i = 0; i <=10; i++){
            try {
                recurso.consumir();
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}