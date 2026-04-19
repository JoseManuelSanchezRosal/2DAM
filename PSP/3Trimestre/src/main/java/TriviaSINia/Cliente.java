package TriviaSINia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 5001);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader sc = new BufferedReader(new InputStreamReader(System.in))
        ) {
            // Punto 3: Identificación del jugador
            System.out.print("Introduce tu nick para conectarte: ");
            String nick = sc.readLine();
            out.println(nick); // Enviamos el nick al servidor

            new ReceptorMensajes(in).start();

            System.out.println("Cliente conectado. Esperando que el administrador escriba START...");

            while(true) {
                String respuesta = sc.readLine();
                out.println(respuesta);
            }
        } catch (IOException e){
            System.err.println("Error en conexión");
        }
    }
}