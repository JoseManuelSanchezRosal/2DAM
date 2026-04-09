package ejercicio2;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ServidorNumeros {
    public static void main(String[] args) {
        int puertoDeEscucha = 7002;

        // Abrimos la "centralita" en el puerto especificado
        try (ServerSocket centralitaServidor = new ServerSocket(puertoDeEscucha)) {
            System.out.println("Servidor de números iniciado en el puerto " + puertoDeEscucha + ".");

            // Bucle principal del servidor: siempre está encendido esperando clientes
            while (true) {
                System.out.println("\nEsperando a que se conecte un nuevo cliente...");

                try (
                        // El servidor se queda pausado aquí hasta que un cliente llama
                        Socket conexionConCliente = centralitaServidor.accept();

                        // Preparamos los canales de envío y recepción
                        PrintWriter canalSalida = new PrintWriter(conexionConCliente.getOutputStream(), true);
                        BufferedReader canalEntrada = new BufferedReader(new InputStreamReader(conexionConCliente.getInputStream()))
                ) {
                    System.out.println("¡Un cliente se ha conectado!");

                    // Creamos una lista VACÍA específica para guardar los números de este cliente
                    List<Double> listaDeNumeros = new ArrayList<>();

                    // Variable para controlar si seguimos escuchando a este cliente
                    boolean clienteConectado = true;

                    // Bucle secundario: mantiene la SESIÓN ACTIVA con el cliente actual
                    while (clienteConectado) {
                        // Leemos lo que nos manda el cliente
                        String mensajeRecibido = canalEntrada.readLine();

                        // Si recibimos null, significa que el cliente cerró el programa de golpe
                        if (mensajeRecibido == null) {
                            System.out.println("El cliente se desconectó inesperadamente.");
                            break; // Rompemos el bucle de este cliente
                        }

                        System.out.println("Comando recibido: " + mensajeRecibido);

                        // Dividimos el mensaje por espacios.
                        // Por ejemplo, si manda "AGREGAR 5", lo partimos en ["AGREGAR", "5"]
                        String[] partesDelComando = mensajeRecibido.split(" ");

                        // La primera palabra siempre es la acción que quiere hacer
                        String accion = partesDelComando[0].toUpperCase();

                        // Variable para guardar la respuesta que le enviaremos de vuelta
                        String respuestaAlCliente = "";

                        // Estructura switch clásica (sin funciones flecha) para decidir qué hacer
                        switch (accion) {
                            case "AGREGAR":
                                // Comprobamos que nos haya enviado el número junto al comando
                                if (partesDelComando.length >= 2) {
                                    try {
                                        // Convertimos el texto del número a un valor decimal (Double)
                                        double numeroNuevo = Double.parseDouble(partesDelComando[1]);
                                        // Lo añadimos a la lista del cliente
                                        listaDeNumeros.add(numeroNuevo);
                                        respuestaAlCliente = "Numero " + numeroNuevo + " agregado correctamente.";
                                    } catch (NumberFormatException errorNumero) {
                                        // Si nos mandó "AGREGAR patata", fallará y entraremos aquí
                                        respuestaAlCliente = "ERROR: '" + partesDelComando[1] + "' no es un número válido.";
                                    }
                                } else {
                                    respuestaAlCliente = "ERROR: Debes indicar un número. Ejemplo: AGREGAR 10";
                                }
                                break;

                            case "MOSTRAR":
                                // Si la lista está vacía, se lo decimos. Si no, le mostramos el contenido.
                                if (listaDeNumeros.isEmpty()) {
                                    respuestaAlCliente = "La lista está vacía.";
                                } else {
                                    respuestaAlCliente = "Tus números: " + listaDeNumeros.toString();
                                }
                                break;

                            case "MEDIA":
                                if (listaDeNumeros.isEmpty()) {
                                    respuestaAlCliente = "ERROR: No hay números para calcular la media.";
                                } else {
                                    double sumaTotal = 0;
                                    // Recorremos todos los números y los sumamos
                                    for (double num : listaDeNumeros) {
                                        sumaTotal = sumaTotal + num;
                                    }
                                    // Dividimos la suma total entre la cantidad de números
                                    double media = sumaTotal / listaDeNumeros.size();
                                    respuestaAlCliente = "La media es: " + media;
                                }
                                break;

                            case "MAXIMO":
                                if (listaDeNumeros.isEmpty()) {
                                    respuestaAlCliente = "ERROR: No hay números para buscar el máximo.";
                                } else {
                                    // Asumimos que el primer número es el más grande para empezar a comparar
                                    double maximo = listaDeNumeros.get(0);
                                    for (double num : listaDeNumeros) {
                                        if (num > maximo) {
                                            maximo = num; // Si encontramos uno mayor, lo actualizamos
                                        }
                                    }
                                    respuestaAlCliente = "El valor máximo es: " + maximo;
                                }
                                break;

                            case "BORRAR":
                                // Vaciamos completamente la lista
                                listaDeNumeros.clear();
                                respuestaAlCliente = "La lista ha sido borrada por completo.";
                                break;

                            case "CERRAR":
                                // Cambiamos la variable para salir del bucle while interno
                                clienteConectado = false;
                                respuestaAlCliente = "Sesión finalizada. ¡Adiós!";
                                System.out.println("El cliente ha solicitado cerrar la sesión.");
                                break;

                            default:
                                respuestaAlCliente = "ERROR: Comando no reconocido.";
                                break;
                        }

                        // Enviamos la respuesta fabricada al cliente (solo si no se ha desconectado)
                        if (clienteConectado) {
                            canalSalida.println(respuestaAlCliente);
                        }
                    }

                } catch (IOException excepcionCliente) {
                    System.err.println("Hubo un problema de conexión con el cliente actual: " + excepcionCliente.getMessage());
                }
            }
        } catch (IOException excepcionServidor) {
            System.err.println("No se pudo iniciar el servidor en el puerto " + puertoDeEscucha);
            excepcionServidor.printStackTrace();
        }
    }
}