package ComunicacionHilos;
//COMUNICACION BASICO SIN CONTROL DE COMUNICACION
public class EjemploComunicacionBasico {
    public static void main(String[] args) {

        Recurso recurso = new Recurso();
        new Productor(recurso).start();
        new Consumidor(recurso).start();
    }
}