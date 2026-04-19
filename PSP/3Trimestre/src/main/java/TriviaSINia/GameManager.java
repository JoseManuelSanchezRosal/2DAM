package TriviaSINia;

import java.util.ArrayList;

public class GameManager {
    private ArrayList<ClienteHandler> clientes = new ArrayList<>();
    private static boolean rondaAbierta = false;
    private ArrayList<Pregunta> preguntas = new ArrayList<>();

    public GameManager(ArrayList<ClienteHandler> clientes) {
        this.clientes = clientes;
        this.preguntas.add(new Pregunta("Un perro dando vueltas a una farola, a cual vuelta se echa??", "1", "2", "77", "Ultima", "d"));
        this.preguntas.add(new Pregunta("Cuantos primos tiene un gitano??", "122", "244", "355", "12554", "d"));
        this.preguntas.add(new Pregunta("Si un perro te mordiera y otro por culo te diera, a cual de los dos le echarias de comer??", "Al primero", "Al segundo", "A ninguno le echaria y al dueno por culo le daria", "A los 2", "c"));
        this.preguntas.add(new Pregunta("Cuantos lunares tiene la luna", "133", "332", "727", "infinito", "d"));
        this.preguntas.add(new Pregunta("Cuantos primos tiene un polaco ??", "0", "0.5", "1", "ninguno", "d"));
    }

    // Métodos sincronizados
    public static synchronized boolean isRondaAbierta() {
        return rondaAbierta;
    }

    public static synchronized void setRondaAbierta(boolean estado) {
        rondaAbierta = estado;
    }

    public void iniciarPartida(){
        try {
            System.out.println("Partida iniciada");
            enviarTodos("EL JUEGO HA COMENZADO");

            for (int i = 0; i < preguntas.size(); i++) {
                Pregunta p = preguntas.get(i);
                enviarTodos("PREGUNTA " + (i + 1) + ": " + extraerPregunta(p));

                setRondaAbierta(true);
                Thread.sleep(10000);
                setRondaAbierta(false);

                corregirRespuestas(p.getRespuestaCorrecta());
                limpiarRespuestas();

                // Punto 10: Mostrar ranking actualizado tras cada pregunta
                mostrarRanking();
            }

            // Punto 11: Mostrar ranking final y ganador al terminar todo
            determinarGanadorFinal();

        }catch (Exception e){
            System.out.println("Error en iniciar partida" + e.getMessage());
        }
    }

    public void mostrarRanking(){
        String total = "\nRANKING ACTUAL:";
        for (ClienteHandler cl : clientes){
            total += "\n" + cl.mostrarNota();
        }
        System.out.println(total);
        enviarTodos(total);
    }

    private void determinarGanadorFinal() {
        String total = "\n====== RANKING FINAL ======";
        ClienteHandler ganador = clientes.get(0);

        for (ClienteHandler cl : clientes){
            total += "\n" + cl.mostrarNota();
            if (cl.getPuntos() > ganador.getPuntos()) {
                ganador = cl;
            }
        }
        String textoGanador = "\n\n¡EL GANADOR ES: " + ganador.getNick().toUpperCase() + " CON " + ganador.getPuntos() + " PUNTOS!";
        System.out.println(total + textoGanador);
        enviarTodos(total + textoGanador);
    }

    public void limpiarRespuestas(){
        for (ClienteHandler cl : clientes){
            cl.limpiaRespuesta();
        }
    }

    public void enviarTodos(String msg){
        for (ClienteHandler cl : clientes){
            cl.enviarMensaje(msg);
        }
    }

    public void corregirRespuestas(String sol){
        for (ClienteHandler cl : clientes){
            cl.corregirRespuesta(sol);
        }
    }

    public String extraerPregunta(Pregunta p){
        return p.getEnunciado()+" | A: "+p.getRespuestaA()
                +" | B: "+p.getRespuestaB()
                +" | C: "+p.getRespuestaC()
                +" | D: "+p.getRespuestaD();
    }

    public ArrayList<ClienteHandler> getClientes() { return clientes; }
    public ArrayList<Pregunta> getPreguntas() { return preguntas; }
    public void setClientes(ArrayList<ClienteHandler> clientes) { this.clientes = clientes; }
    public void setPreguntas(ArrayList<Pregunta> preguntas) { this.preguntas = preguntas; }
}