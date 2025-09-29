package ejercicios;

public class AbrirCalc1 extends Thread{
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
        AbrirCalc1 abrir1 = new AbrirCalc1();
        abrir1.start();
    }
}