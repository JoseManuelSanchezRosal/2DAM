import java.io.*;

public class escrituraFichero {
    public static void main(String[] args)
    {
        File fichero = null;
        FileWriter writer = null;
        PrintWriter pw = null;
        try
        {
            fichero = new File("src/archivo2.txt");
            writer = new FileWriter(fichero);
            pw = new PrintWriter(writer);
            for (int i = 0; i < 10; i++) {
                pw.println("Linea " + i);
            }
            System.out.println("Se han escrito 10 lineas correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    writer.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //AHORA VAMOS A LEER LO QUE HAY EN EL ARCHIVO2.TXT:
        try {
            File lector = new File("src/archivo2.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}