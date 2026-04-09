package practica1sockets;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Servidor {

    // Coleccion interna en memoria para validar usuarios
    private static final Map<String, String> credenciales = new HashMap<>();
    static {
        credenciales.put("admin", "1234");
    }

    public static void main(String[] args) {
        System.out.println("Servidor arrancado y esperando...");
        try (
                ServerSocket server = new ServerSocket(1234);
                Socket cliente = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
        ) {
            // FASE 1: AUTENTICACION (Max 3 intentos)
            boolean autenticado = false;
            for (int intentos = 0; intentos < 3; intentos++) {
                // El servidor toma el control y solicita los datos paso a paso
                out.println("REQ_USER");
                String user = in.readLine();

                out.println("REQ_PASS");
                String pass = in.readLine();

                if (credenciales.containsKey(user) && credenciales.get(user).equals(pass)) {
                    out.println("AUTH_OK");
                    autenticado = true;
                    break;

                } else {
                    out.println("AUTH_FAIL");
                }
            }
            // Si falla 3 veces, cierra el flujo
            if (!autenticado) {
                out.println("EXIT_AUTH");
                return;
            }

            // FASE 2: MENU DE OPERACIONES
            String operacion;
            while ((operacion = in.readLine()) != null && !operacion.equals("5")) {
                String inputData = null;

                // El servidor indica al cliente los datos que necesita segun la opcion
                switch (operacion) {
                    case "1":
                    case "2":
                        out.println("Dame 2 numeros (separados por coma)");
                        inputData = in.readLine();
                        break;
                    case "3":
                        out.println("Dame la cadena");
                        inputData = in.readLine();
                        break;
                    case "4":
                        out.println("Dame el numero");
                        inputData = in.readLine();
                        break;
                    default:
                        // Aqui no pedimos nada por teclado al USUARIO, de esta manera mantenemos conectados CLIENTE-SERVIDOR sin errores
                        out.println("Operacion no permitida");
                        continue;
                }
                // Delega el calculo a un metodo externo para mantener el main limpio
                out.println(procesarOperacion(operacion, inputData));
            }
            out.println("ADIOS");

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    // Metodo que centraliza la logica de negocio
    private static String procesarOperacion(String operacion, String datos) {
        try {
            switch (operacion) {
                case "1": // Sumar
                    String[] nums = datos.split(",");
                    return String.valueOf(Integer.parseInt(nums[0].trim()) + Integer.parseInt(nums[1].trim()));

                /*case "2": // Contador (Secuencia de A hasta B)
                    String[] limites = datos.split(",");
                    int inicio = Integer.parseInt(limites[0].trim());
                    int fin = Integer.parseInt(limites[1].trim());
                    StringBuilder contadorStr = new StringBuilder();
                    for (int i = inicio; i <= fin; i++) {
                        contadorStr.append(i).append(" ");
                    }
                    return contadorStr.toString().trim();*/
                case "2": // Contador huecos (desde A hasta B)
                    String[] tramo = datos.split(",");
                    Integer huecos = Integer.parseInt(tramo[1])-Integer.parseInt(tramo[0]);
                    return huecos.toString();

                case "3": // Invierte
                    return new StringBuilder(datos).reverse().toString();

                case "4":
                    int num = Integer.parseInt(datos.trim());
                    if (num <= 1) return "No es primo";
                    if (num == 2) return "Es primo";
                    if (num % 2 == 0) return "No es primo";

                    for (int i = 3; i <= Math.sqrt(num); i += 2) {
                        if (num % i == 0) return "No es primo";
                    }
                    return "Es primo";

                default:
                    return "Error interno";
            }
        } catch (Exception e) {
            return "Error: Formato de datos incorrecto.";
        }
    }
}