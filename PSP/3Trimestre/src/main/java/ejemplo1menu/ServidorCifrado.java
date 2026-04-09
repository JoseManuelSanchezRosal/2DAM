package ejemplo1menu;

import java.io.*;
import java.net.*;

public class ServidorCifrado {
    public static void main(String[] args) {
        System.out.println("Servidor arrancado y esperando...");
        try (
                ServerSocket server = new ServerSocket(1234);
                Socket cliente = server.accept();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(cliente.getInputStream()));
                PrintWriter out = new PrintWriter(
                        cliente.getOutputStream(), true);
        ) {
            String operacion;
            while ((operacion = in.readLine()) != null && !operacion.equals("3")) {
                String cadena = null;

                switch (operacion) {
                    case "1":
                    case "2":
                        out.println("Pasame la cadena");
                        cadena = in.readLine();
                        break;
                    default:
                        out.println("Operación no permitida");
                        continue;
                }

                out.println(cifrar_descifrar(cadena, operacion, 3));
            }

            out.println("ADIOS");

        } catch (IOException e) {
            System.err.println("Error de conexión");
        } catch (ArithmeticException a) {
            System.err.println("División sobre 0" + a.getMessage());
        }
    }

    public static String cifrar_descifrar(String cadena, String operacion, int desplazamiento) {
        int rango = ('z' - 'a') + 1;
        String resultado = "";
        char c;

        switch (operacion) {
            case "1":
                for (int i = 0; i < cadena.length(); i++) {
                    c = cadena.charAt(i);

                    if (c >= 'a' && c <= 'z') {
                        resultado += (char) (((c - 'a') + desplazamiento) % rango + 'a');
                    } else if (c >= 'A' && c <= 'Z') {
                        resultado += (char) (((c - 'A') + desplazamiento) % rango + 'A');
                    } else {
                        resultado += c;
                    }
                }
                break;

            case "2":
                for (int i = 0; i < cadena.length(); i++) {
                    c = cadena.charAt(i);

                    if (c >= 'a' && c <= 'z') {
                        resultado += (char) (((c - 'a') - desplazamiento + rango) % rango + 'a');
                    } else if (c >= 'A' && c <= 'Z') {
                        resultado += (char) (((c - 'A') - desplazamiento + rango) % rango + 'A');
                    } else {
                        resultado += c;
                    }
                }
                break;
        }
        return resultado;
    }
}