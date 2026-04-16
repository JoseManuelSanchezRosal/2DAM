package TriviaConIA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Servidor {

    public static List<ClienteHandler> clientes = Collections.synchronizedList(new ArrayList<>());
    public static List<Pregunta> bancoPreguntas = new ArrayList<>();
    public static boolean juegoIniciado = false;
    public static int tiempoPorPregunta = 10;

    public static void main(String[] args) {
        System.out.println("Iniciando componentes del servidor...");

        bancoPreguntas = GestorArchivos.cargarPreguntas();
        System.out.println("Cargadas " + bancoPreguntas.size() + " preguntas.");

        HiloConexiones hiloRed = new HiloConexiones(7000);
        hiloRed.start();

        ConsolaServidor consola = new ConsolaServidor();
        consola.start();
    }

    public static void iniciarJuego() {
        for (ClienteHandler c : clientes) {
            c.prepararPreguntas(bancoPreguntas);
            c.enviarMensaje("--- LA TRIVIA HA COMENZADO ---");
        }

        for (int i = 0; i < 5; i++) {
            for (ClienteHandler c : clientes) {
                c.enviarSiguientePregunta(i);
            }

            try {
                Thread.sleep(tiempoPorPregunta * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (ClienteHandler c : clientes) {
                c.verificarRespuesta();
            }
        }

        enviarRanking();
        enviarGanador();

        GestorArchivos.guardarPartidaXML(clientes);

        System.exit(0);
    }

    public static void ordenarRankingManual() {
        for (int i = 0; i < clientes.size() - 1; i++) {
            for (int j = 0; j < clientes.size() - i - 1; j++) {
                if (clientes.get(j).getPuntuacion() < clientes.get(j + 1).getPuntuacion()) {
                    ClienteHandler temporal = clientes.get(j);
                    clientes.set(j, clientes.get(j + 1));
                    clientes.set(j + 1, temporal);
                }
            }
        }
    }

    public static void enviarRanking() {
        ordenarRankingManual();

        StringBuilder ranking = new StringBuilder("\n--- RANKING ACTUALIZADO ---\n");
        for (ClienteHandler c : clientes) {
            ranking.append(c.getNick()).append(": ").append(c.getPuntuacion()).append(" pts\n");
        }

        String rankingFinal = ranking.toString();
        System.out.println(rankingFinal);

        for (ClienteHandler c : clientes) {
            c.enviarMensaje(rankingFinal);
        }
    }

    public static void enviarGanador() {
        if (!clientes.isEmpty()) {
            String ganador = "\n*** EL GANADOR ES: " + clientes.get(0).getNick() + " ***";
            System.out.println(ganador);
            for (ClienteHandler c : clientes) {
                c.enviarMensaje(ganador);
                c.enviarMensaje("FIN");
            }
        }
    }

    public static void broadcastChat(String mensaje, ClienteHandler remitente) {
        if (!juegoIniciado) {
            for (ClienteHandler c : clientes) {
                if (c != remitente && c.getNick() != null) {
                    c.enviarMensaje("[LOBBY] " + remitente.getNick() + ": " + mensaje);
                }
            }
        }
    }
}