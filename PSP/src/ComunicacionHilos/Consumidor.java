package ComunicacionHilos;

public class Consumidor extends Thread {
    private Recurso recurso;

    public Consumidor(Recurso recurso){
        this.recurso = recurso;
    }
    @Override
    public void run(){
        for (int i =1; i <=5; i++){
            recurso.consumir();
            try {
                Thread.sleep(4000);
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            };
        }
    }
}