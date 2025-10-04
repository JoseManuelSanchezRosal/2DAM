package Barbero1;

public class DemoDeadlock {
    public static void main(String[] args) {
        Sillon sillon = new Sillon();

        // Primer cliente: bloquea primero las sillas, luego el sillón
        Thread primCliente = new Thread(() -> {
            synchronized (sillon.getSillas()) {
                System.out.println("🧍‍♂️ [Cliente A] ha bloqueado las sillas de espera");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("🧍‍♂️ [Cliente A] intenta acceder al sillón del barbero...");
                System.out.println("⚠️ [Cliente A] está esperando porque el [Cliente B] tiene bloqueado el sillón");

                synchronized (sillon) {
                    System.out.println("✅ [Cliente A] ha conseguido el sillón y está siendo atendido");
                }
            }
        });

        // Segundo cliente: bloquea primero el sillón, luego las sillas
        Thread clienteB = new Thread(() -> {
            synchronized (sillon) {
                System.out.println("🧍‍♂️ [Cliente B] ha bloqueado el sillón del barbero");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("🧍‍♂️ [Cliente B] intenta acceder a las sillas de espera...");
                System.out.println("⚠️ [Cliente B] está esperando porque el [Cliente A] tiene bloqueadas las sillas");

                synchronized (sillon.getSillas()) {
                    System.out.println("✅ [Cliente B] ha conseguido las sillas de espera");
                }
            }
        });

        // Iniciamos los hilos que provocan el interbloqueo
        primCliente.start();
        clienteB.start();

        // Opcional: hilos normales del barbero y cliente para contexto
        Barbero barbero = new Barbero(sillon);
        Cliente cliente = new Cliente(sillon);
        barbero.start();
        cliente.start();
    }
}
