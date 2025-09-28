import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class lecturaFichero {
    public static void main(String[] args) {
        File archivo = null;
        FileReader reader = null;
        BufferedReader buffer = null;
        try {
            archivo = new File("src/archivo.txt");
            reader = new FileReader (archivo);
            buffer = new BufferedReader(reader);
            String linea;
            while((linea=buffer.readLine()) != null) {
                System.out.println(linea);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if( null != reader ){
                    reader.close();//SOLO SE CIERRA EL FILEREADER?
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }
}