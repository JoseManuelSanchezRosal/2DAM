package ejercicios;
//Crea 3 hilos con las siguientes tareas: -
//Hilo A: imprime “Descargando datos…” cada 2 segundos (3 veces). - -
//Hilo B: imprime “Procesando…” cada 3 segundos (2 veces).
//Hilo C: imprime “Guardando…” cada 1 segundo (5 veces).
//El programa principal debe esperar que terminen los 3 hilos y muestren el siguiente
//mensaje: “Todas las tareas finalizadas”.

//ESTE ES UN CLARO EJEMPLO DE CONCURRENCIA PUESTO QUE ABRIMOS HILOS PARA HACER TAREAS QUE SE EJECUTAN AL MISMO TIEMPO.
//DIFERENCIA ENTRE PONER O NO IMPLEMENTS RUNNABLE????
public class HilosEj2 {
    public static void main(String[] args) {
        //DEFINIMOS CON RUNNABLE LA LOGICA QUE QUEREMOS QUE SE EJECUTE AL LLAMAR A NUESTRO HILO
        Runnable hiloA = new Runnable() {
            @Override
            public void run() {
                for(int i=1; i<=3;i++){
                    System.out.println("HiloA: Descargando datos....................");
                    //System.out.println(Thread.currentThread().getName()+": "+"Descargando datos............");
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
                for(int i=1; i<=2;i++){
                    System.out.println("HiloB: Procesando....................");
                    //System.out.println(Thread.currentThread().getName()+": "+"Descargando datos............");
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
                for(int i=1; i<=5;i++){
                    System.out.println("HiloC: Guardando....................");
                    //System.out.println(Thread.currentThread().getName()+": "+"Descargando datos............");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        //LANZAMOS CADA PROCESO DIFERENTE EN UN HILO
        Thread proceso1 = new Thread(hiloA);//PASAMOS POR PARAMETRO LA LOGICA QUE QUERAMOS EJECUTAR EN FUNCION DEL PROCESO
        proceso1.start();
        Thread proceso2 = new Thread(hiloB);//PASAMOS POR PARAMETRO LA LOGICA QUE QUERAMOS EJECUTAR EN FUNCION DEL PROCESO
        proceso2.start();
        Thread proceso3 = new Thread(hiloC);//PASAMOS POR PARAMETRO LA LOGICA QUE QUERAMOS EJECUTAR EN FUNCION DEL PROCESO
        proceso3.start();

        try {
            //HASTA QUE NO SE FINALICEN LOS 3 PROCESOS, NO ENVIAMOS EL MENSAJE DE TAREAS TERMINADAS.
            proceso1.join();
            proceso2.join();
            proceso3.join();
            System.out.println("Todas las tareas finalizadas");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}