package Trabajo_A3_PSP.BarberoMonitores;

// Clase Cliente que extiende de HILO
public class Cliente extends Thread{
    private Sillon sillon;

    public Cliente(Sillon sillon){
        this.sillon = sillon;
    }
    @Override
    public void run(){
        // Enviamos a 7 clientes al sillón:
        for (int i = 0; i <= 6; i++){
            try {
                // Intervalo de espera entre que mandamos un cliente a la barberia y otro.
                Thread.sleep(500);
                sillon.SentarseSillon();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}