package Trabajo_A3_PSP.BarberoSemaforo;

import java.util.Random;

class Clientee extends Thread {
    //este hilo de cliente espera un tiempo aleatorio antes de sentarse en el sillon
    private SillonBarberoSemaforo sillon;

    public Clientee(SillonBarberoSemaforo sillon) {
        this.sillon = sillon;
    }
    @Override
    public void run() {
        try {
            Random random = new Random();
            int tiempoEspera = random.nextInt(5000);
            Thread.sleep(tiempoEspera);
            sillon.sentarseSillon();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}