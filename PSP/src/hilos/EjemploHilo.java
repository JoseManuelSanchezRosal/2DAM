package hilos;

public class EjemploHilo extends Thread{
    public void run(){
        System.out.println("Ejecutando hilo: "+ getName());
    }

    public static void main(String[] args) {
        new EjemploHilo().start();
    }
}