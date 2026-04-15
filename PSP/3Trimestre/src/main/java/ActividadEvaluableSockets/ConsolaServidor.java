package ActividadEvaluableSockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsolaServidor extends Thread {

    @Override
    public void run() {
        try (BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {
            String comando;
            while (!Servidor.juegoIniciado) {
                System.out.println("\n=== MENÚ DE CONTROL DEL SERVIDOR ===");
                System.out.println("1. Ver jugadores conectados");
                System.out.println("2. Iniciar Trivia (START)");
                System.out.println("3. Apagar Servidor");
                System.out.print("Elige una opción: ");

                comando = teclado.readLine();

                if (comando == null) break;

                switch (comando) {
                    case "1":
                        System.out.println("\n--- JUGADORES EN LA SALA (" + Servidor.clientes.size() + "/10) ---");
                        if (Servidor.clientes.isEmpty()) {
                            System.out.println("No hay nadie conectado aún.");
                        } else {
                            for (ClienteHandler c : Servidor.clientes) {
                                if (c.getNick() != null) {
                                    System.out.println("- " + c.getNick());
                                } else {
                                    System.out.println("- Conectando...");
                                }
                            }
                        }
                        break;
                    case "2":
                    case "START":
                        if (!Servidor.clientes.isEmpty()) {
                            Servidor.juegoIniciado = true;
                            System.out.println("\nIniciando partida...");
                            Servidor.iniciarJuego();
                        } else {
                            System.out.println("\n[!] Error: No puedes iniciar la partida sin jugadores.");
                        }
                        break;
                    case "3":
                        System.out.println("\nCerrando el servidor...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("\n[!] Opción no válida.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}