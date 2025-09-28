package ComunicacionHilos;
import java.io.IOException;

public class Productor extends Thread{
    private Recurso recurso;

    public Productor(Recurso recurso){
        this.recurso = recurso;
    }
    @Override
    public void run(){
        for (int i = 1; i <=5; i++){
            recurso.producir(i);
            try {
                Thread.sleep(1500);
            }catch (Exception e){//
                System.out.println(e.getMessage());
            }
        }
    }
}