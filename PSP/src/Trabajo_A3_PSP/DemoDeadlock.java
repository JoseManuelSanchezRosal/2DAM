package Trabajo_A3_PSP;

import Trabajo_A3_PSP.BarberoMonitores.Barbero;
import Trabajo_A3_PSP.BarberoMonitores.Cliente;
import Trabajo_A3_PSP.BarberoMonitores.Sillon;

public class DemoDeadlock {
    public static void main(String[] args) {
        Sillon sillon = new Sillon();

        // El primer cliente intenta acceder a una silla y luego al sillón del barbero
        Thread primCliente = new Thread(new Runnable() {
            @Override
            public void run() {
                //el método synchronized controla el acceso a un recurso compartido con varios hilos --> es un bloqueo
                synchronized (sillon.getSillas()) {
                    System.out.println("El primer cliente ha ocupado las sillas de espera");
                    try {
                        //El hilo espera 100ms para ejecutarse
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("El primer cliente ha ocupado el sillón del barbero");
                    //con synchronized sillon se pone un bloqueo al sillon
                    //al estar los dos synchronized en el mismo hilo, se produce el bloqueo al estar los
                    //dos candados en distinto orden
                    synchronized (sillon) {
                        System.out.println("El primer cliente está siendo atendido");
                    }
                }
            }
        });

        // Un segundo cliente intenta acceder al sillón del barbero y luego a las sillas
        Thread clienteB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (sillon) {
                    System.out.println("El segundo cliente ha accedido al sillón del barbero");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("El segundo cliente ha accedido a las sillas de espera");
                    synchronized (sillon.getSillas()) {
                        System.out.println("El segundo cliente está ocupando las sillas de espera");
                    }
                }
            }
        });

        // Iniciamos los hilos
        primCliente.start();
        clienteB.start();

        // Aunque iniciemos al barbero y al cliente reales, los sillones están ocupados
        // Cliente A y B están esperando a usar recursos del otro, asi ninguno avanza en el proceso. Eso es un deadlock.
        Barbero barbero = new Barbero(sillon);
        Cliente cliente = new Cliente(sillon);
        barbero.start();
        cliente.start();
    }
}