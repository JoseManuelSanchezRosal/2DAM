package TriviaSINia;

import java.util.Scanner;

public class HiloComandoStart extends Thread {

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        while (!Servidor.isPartidaIniciada()) {
            String comando = sc.nextLine();

            if (comando.equalsIgnoreCase("START")) {
                if (Servidor.clientes.isEmpty()) {
                    System.out.println("No hay jugadores conectados. Espera a que entre alguien.");
                } else {
                    Servidor.setPartidaIniciada(true);
                    System.out.println("Comando START recibido. Iniciando la trivia...");

                    try {
                        // Al no poder usar setSoTimeout, cerramos el socket del servidor
                        // para romper el accept() bloqueante y que el hilo principal avance.
                        if (Servidor.serverSocket != null) {
                            Servidor.serverSocket.close();
                        }
                    } catch (Exception e) {
                        // Ignoramos la excepción controlada
                    }

                    // Iniciamos la partida en este mismo hilo
                    new GameManager(Servidor.clientes).iniciarPartida();
                }
            }
        }
    }
}