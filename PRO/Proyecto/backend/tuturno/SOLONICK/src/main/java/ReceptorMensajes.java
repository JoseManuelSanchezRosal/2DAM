import java.io.BufferedReader;

public class ReceptorMensajes extends Thread {
    private BufferedReader in;

    public ReceptorMensajes (BufferedReader in){
        this.in = in;
    }
    @Override
    public void run() {
        try {
            String mensaje;
            while ((mensaje = in.readLine())!= null){
                System.out.println(mensaje);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.exit(0);
        }
    }
}