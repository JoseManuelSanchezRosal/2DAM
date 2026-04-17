package TriviaSINia;

import java.io.BufferedReader;
import java.io.IOException;

public class ReceptorMensajes extends Thread {
    BufferedReader in;

    public ReceptorMensajes(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            // ESTA EL FALLO EN LOS 2 READLINE, HAY QUE SUSTITUIR EL SEGUNDO POR MESSAGE (PARA NO QUEMAR UNA LECTURA)
            String message;
            while ((message=in.readLine())!=null){
                //System.out.println(in.readLine());
                System.out.println(message); // Leemos el mensaje, no del readline otra vez.
            }
            System.out.println("Servidor finalizado");
        }catch (IOException e){
            System.out.println("Error"+e.getMessage());
        }
    }
}