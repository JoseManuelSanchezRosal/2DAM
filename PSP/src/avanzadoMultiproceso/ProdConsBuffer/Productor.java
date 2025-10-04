package avanzadoMultiproceso.ProdConsBuffer;

public class Productor extends Thread {
    private Recurso recurso;

    public Productor(Recurso recurso) {
        this.recurso = recurso;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    @Override
    public void run(){
        for (int i = 0; i <= 10; i++){
            try {
                recurso.producir();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}