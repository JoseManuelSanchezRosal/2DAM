package Trabajo_A3_PSP.BarberoSemaforo;

class Barberoo extends Thread { //este hilo accede al monitor de las sillas
    // y los semaforos (la variable sillon)
    // funciona a la vez que el hilo de los clientes
    private SillonBarberoSemaforo sillon;

    //la variable sillon sirve para que el barbero pueda acceder al metodo de cortar el pelo
    public Barberoo(SillonBarberoSemaforo sillon) {
        this.sillon = sillon;
    }

    @Override
    public void run() {
        try {
            while (true) {
                sillon.cortarPelo();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}