package ejercicios;

public class AbrirNotepad extends Thread{
    public void run(){
        try {
            ProcessBuilder notepad = new ProcessBuilder("notepad");
            Process proceso = notepad.start();
            System.out.println("Se abrio notepad");

            proceso.waitFor();
            System.out.println("Cerraste notepad");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        AbrirNotepad notepad1 = new AbrirNotepad();
        notepad1.start();    }
}