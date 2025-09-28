package ejercicios;
//NOTA, ESTE EJERCICIO EN MI PC ME LANZA LOS 2 MENSAJES DE NOTEPAD ABIERTO Y NOTEPAD CERRADO AL MISMO TIEMPO SIN CERRAR NOTEPAD.
//SE HA PROBADO EL MISMO CODIGO EN OTRO PC Y SI FUNCIONA BIEN, HASTA QUE NO CERRAMOS EL NOTEPAD, NO LANZA EL MENSAJE DE NOTEPAD CERRADO.
public class AbrirCalcRunnable {
    public static void main(String[] args) {
        Runnable notePad = new Runnable() {
            @Override
            public void run() {
                ProcessBuilder pb = new ProcessBuilder("notepad");
                try {
                    pb.start();
                    System.out.println("Notepad abierto");

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread hilo1 = new Thread(notePad);
        hilo1.start();
        try {
            hilo1.join();
            System.out.println("NOTEPAD CERRADO");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}