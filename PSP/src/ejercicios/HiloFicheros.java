package ejercicios;

import java.io.*;
//SIN ACABAR!!!!!!!!!!!
public class HiloFicheros {
    public static void main(String[] args) {
        //RUTAS ARCHIVOS DONDE GUARDAREMOS LAS FALLAS.TXT
        File ruta1 = new File("src/ejercicios/fallos1.txt");
        File ruta2 = new File("src/ejercicios/fallos2.txt");

        try {
            //ESCRIBIMOS EN RUTA1 Y RUTA 2, CON UN BR PARA CADA UNO.
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(ruta1));
            bw1.write("INFO;No arranca\n");
            bw1.write("INFO;Sin cable\n");
            bw1.write("WARN;Luz averia\n");
            bw1.write("ERROR;Pal desguace\n");
            bw1.close();

            BufferedWriter bw2 = new BufferedWriter(new FileWriter(ruta2));
            bw2.write("INFO;Falta llave\n");
            bw2.write("INFO;Falta papeles\n");
            bw2.write("WARN;No abre\n");
            bw2.write("WARN;Puerta abierta\n");
            bw2.write("WARN;Rueda pinchada\n");
            bw2.write("ERROR;Sin carnet\n");
            bw2.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Runnable secuencial = new Runnable() {
            @Override
            public void run() {
                File ruta1 = new File("src/ejercicios/fallos1.txt");
                int contador1 = 0;
                int info = 0;
                int warn = 0;
                int error= 0;

                try {
                    BufferedReader br1 = new BufferedReader(new FileReader(ruta1));

                    String linea;
                    while ((linea = br1.readLine()) !=null){
                        contador1++;
                        String[] errores;
                        errores = linea.split(";");
                        if(errores[0].equalsIgnoreCase("info")){
                            info++;
                        } else if (errores[0].equalsIgnoreCase("warn")) {
                            warn++;
                        }else {
                            error++;
                        }
                    }
                    br1.close();
                    System.out.println("En el archivo de la ruta1 hay: " + contador1 +" lineas");
                    System.out.println("Info aparece: " + info + "\n" + "Warn aparece " + warn + "\n" + "Error aparece: " + error);

                    } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                File ruta2 = new File("src/ejercicios/fallos2.txt");
                int contador2 = 0;

                try {
                    BufferedReader br2 = new BufferedReader(new FileReader(ruta2));
                    while (br2.readLine() !=null){
                        contador2++;

                    }
                    br2.close();
                    System.out.println("En el archivo de la ruta2 hay: " + contador2 +" lineas");

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread fichero11 = new Thread(secuencial);
        fichero11.start();
    }
}