package ComunicacionHilos;
//COMUNICACION BASICO SIN CONTROL DE COMUNICACION
public class EjemploComunicacionBasico {
    public static void main(String[] args) {

        Recurso recurso = new Recurso();
        //FORMA 1:
        new Productor(recurso).start();
        new Consumidor(recurso).start();

        // FORMA 2:
        //Productor p1 = new Productor(recurso);
        //p1.start();
        //Consumidor c1 = new Consumidor(recurso);
        //c1.start();
    }
}