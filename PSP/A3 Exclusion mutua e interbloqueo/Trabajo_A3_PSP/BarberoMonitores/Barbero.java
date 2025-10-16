package Trabajo_A3_PSP.BarberoMonitores;

// Clase barbero que extiende de HILO
public class Barbero extends Thread {
    private Sillon sillon;

    public Barbero(Sillon sillon) {
        this.sillon = sillon;
    }
    @Override
    public void run(){
        // SI CAMBIAMOS LOS TIEMPOS DE ESPERA DE UN PROCESO U OTRO, EL COMPORTAMIENTO DEL SISTEMA TAMBIÉN SE VERÁ AFECTADO
        // Hacemos bucle infinito para que trabaje el barbero (24/7/365)
        while(true){
            try {
                sillon.CortarPelo();
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}