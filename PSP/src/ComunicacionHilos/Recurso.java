package ComunicacionHilos;

public class Recurso {
    private int valor;
    private boolean disponible = false;

    // METODO PARA QUE EL PRODUCTOR ESCRIBA UN VALOR
    public synchronized void producir(int nuevoValor){
        while (disponible){// SI HAY DATO PRODUCIDO, EL PRODUCTOR ESPERA
            try {
                wait();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        valor = nuevoValor;
        disponible = true;
        System.out.println("productor produjo: "+ valor);
        notify();// AVISA AL CONSUMIDOR
    }
    // METODO PARA QUE EL CONSUMIDOR LEA UN VALOR
    public synchronized int consumir(){
        while(!disponible){ // SI AUN NO HAY DATO, EL CONSUMIDOR ESPERA A QUE LO HAYA.
            try {
                wait();}catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        disponible = false;
        System.out.println("Consumidor consumio: "+ valor);
        notify();// AVISA AL PRODUCTOR
        return valor;
    }
}