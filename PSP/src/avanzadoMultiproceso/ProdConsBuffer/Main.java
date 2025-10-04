package avanzadoMultiproceso.ProdConsBuffer;

public class Main {
    // Main para instanciar los productores y consumidores e iniciar los metodos de producir y consumir respectivamente
    // NOTA: cada proceso crea 10 y consume 10, en este caso 20 en total
    public static void main(String[] args) {
        Recurso recurso = new Recurso();
        Productor p1 = new Productor(recurso);
        Productor p2 = new Productor(recurso);

        Consumidor c1 = new Consumidor(recurso);
        Consumidor c2 = new Consumidor(recurso);

        p1.start();
        p2.start();
        c1.start();
        c2.start();
    }
}