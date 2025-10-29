package repasoExamenPsp;

public class Ejercicio2 {
    public static void main(String[] args) {
        Runnable hiloA = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println("Hilo A: Descargando datos...");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        Runnable hiloB = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++){
                    System.out.println("Hilo B: Procesando...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        Runnable hiloC = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++){
                    System.out.println("Hilo C: Guardando...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Thread proceso1 = new Thread(hiloA);
        proceso1.start();
        Thread proceso2 = new Thread(hiloB);
        proceso2.start();
        Thread proceso3 = new Thread(hiloC);
        proceso3.start();

        try {
            proceso1.join();
            proceso2.join();
            proceso3.join();
            System.out.println("Todos los procesos terminados");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}