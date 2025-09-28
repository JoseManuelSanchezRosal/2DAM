package ejercicios;

public class AbrirCalc extends Thread{
    public void run(){
        try {
            ProcessBuilder calculadora = new ProcessBuilder("notepad");
            Process proceso = calculadora.start();
            System.out.println("Se abrio notepad");

            proceso.waitFor();
            System.out.println("Cerraste notepad");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        AbrirCalc abrir1 = new AbrirCalc();
        abrir1.start();
    }
}