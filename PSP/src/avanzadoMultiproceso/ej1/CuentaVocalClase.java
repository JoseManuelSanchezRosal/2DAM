package avanzadoMultiproceso.ej1;

import java.io.BufferedReader;
import java.io.FileReader;

public class CuentaVocalClase {
    private String ruta;
    private String vocal;

    public CuentaVocalClase(String ruta, String vocal){
        this.ruta = ruta;
        this.vocal = vocal;
    }
    public String getRuta() {
        return ruta;
    }
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    public String getVocal() {
        return vocal;
    }
    public void setVocal(String vocal) {
        this.vocal = vocal;
    }

    public void contarVocal(){
        String ruta = this.getRuta();
        int contador = 0;
        try {
            FileReader fr = new FileReader(ruta);
            int charEntero;
            while ((charEntero = fr.read()) !=-1){
                char caracter = (char) charEntero;
                String aTexto = String.valueOf(caracter);
                if (aTexto.equalsIgnoreCase(getVocal())){
                    contador++;

                }
            }
            fr.close();
            System.out.println("En el documento aparece la vocal " + this.vocal + ": " + contador + " veces");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        CuentaVocalClase doc1 = new CuentaVocalClase("src/avanzadoMultiproceso/cuentavocales.txt",  "i");
        doc1.contarVocal();

    }
}












