import java.io.*;

public class escrituraBinario {
    public static void main(String[] args) {
        //DUDA...EL FILE SE CREA DENTRO O FUERA DEL TRY???
        try {
            //EJEMPLO 1: POR PARTES
            //File file = new File("src\\binario.bin");
            //FileOutputStream fos = new FileOutputStream(file);
            //DataOutputStream dos2 = new DataOutputStream(fos);
            //EJEMPLO 2: EN UNA SOLA LINEA
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File("src\\binario.bin")));

            dos.writeInt(26);
            dos.writeDouble(3.14);
            dos.writeUTF("Hola clase 2DAM");

            System.out.println("Datos escritos correctamente");

            dos.close();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
           //FileInputStream fis = new FileInputStream("src/binario.bin");
           DataInputStream dis = new DataInputStream(new FileInputStream("src/binario.bin"));

            int edad = dis.readInt();
            double pi =dis.readDouble();
            String saludo = dis.readUTF();

            dis.close();
            System.out.println("Archivo cerrado correctamente");
            //fis.close();

            System.out.println("entero leido: "+ edad);
            System.out.println("doble leido: "+ pi);
            System.out.println("cadena leida: "+ saludo);
            System.out.println("Archivos mostrados correctamente");

        } catch (Exception e){
        System.out.println(e.getMessage());
        }
    }
}