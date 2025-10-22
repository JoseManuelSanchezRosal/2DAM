package recuperacionFicheros;

/*Crear un programa Java que gestione pedidos simples usando ficheros de texto.
Se trabajará con dos archivos:
        - productos.txt (catálogo de productos)
- pedidos.txt (pedidos realizados)
1. Cargar productos (2 puntos)
Crea o actualiza el fichero productos.txt con el siguiente formato:
ID|Nombre|Precio|Stock;
Se pedirán todos los datos por teclado. En caso de que el ID del producto exista - se
lanzará una excepción controlada llamada StockCollitionException (definida por el usuario).
        2 - Cargar Pedidos (2,5 puntos)
Cada pedido se guarda en pedidos.txt con el formato:
IDPedido|IDProducto|Cantidad|Fecha(DD-MM-AAAA);
- Validar que exista el ID de producto.
- Verificar que haya suficiente stock antes de registrar.
        - Si el pedido es válido, restar la cantidad al stock del producto en productos.txt.
- Si no hay stock suficiente → lanzar excepción controlada - StockCollitionException
con mensaje "Stock insuficiente para el producto <nombre>."
        - No se puede repetir el ID del pedido. En caso de introducir un ID de pedido existente
se lanzará una excepción controlada llamada StockCollitionException.
3 - Borrar cliente (2,5 puntos)
Borrar cliente se encargará de borrar todos los pedidos asociados a ese cliente.
4 - Calcular ingresos (3 puntos)
Calcula para cada producto los ingresos totales (unidades vendidas x precio). La salida
tiene el siguiente formato:
ID - Nombre producto : Ingresos €
Ejemplo:
productos.txt: 1|Teclado|10|20;
pedidos.txt:
        1|1|4|21-10-2025
        2|1|4|21-10-2025
        —--------------
Salida:
Ingresos totales:
        1 - Teclado : 80 €*/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// El 1 bien hecho
// El 2 bien hecho excepto la parte de restar stock
// El 3 solo borro los pedidos
// El 4 no me ha dado tiempo

public class examen {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("-------------MENU-------------\n" +
                    "1- Cargar productos\n" +
                    "2- Cargar pedidos\n" +
                    "3- Borrar cliente\n" +
                    "4- Calcular ingresos\n" +
                    "0- Salir del programa\n" +
                    "-------------------------------");
            System.out.println("Ingrese una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion){
                case 1:
                    cargarProductos();
                    break;
                case 2:
                    cargarPedidos("ficheros/recuperacionFicheros/predidos.txt");
                    break;
                case 3:
                    System.out.println("Ingrese ID de producto a borrar: ");
                    int idProducto = sc.nextInt();
                    sc.nextLine();
                    borrarProducto(idProducto);
                    break;
                case 4:
                    //Metemos ruta productos, ruta pedidos
                    calcularIngresos("ficheros/recuperacionFicheros/productos.txt", "ficheros/recuperacionFicheros/predidos.txt");
                    break;
                case 0:
                    System.out.println("Saliendo del programa............");
                    break;
                default:
                    System.out.println("Ingrese una opcion valida");
            }

        }while(opcion != 0);

    }

    private static void calcularIngresos(String s, String s1) {
    }


    private static void cargarPedidos(String ruta) {
        File pedidos = new File(ruta);
        File productos = new File("ficheros/recuperacionFicheros/productos.txt");
        Scanner sss = new Scanner(System.in);


        System.out.println("Ingrese ID pedido: ");
        String id = sss.nextLine();
        System.out.println("Ingrese ID producto: ");
        String idProducto = sss.nextLine();
        System.out.println("Ingrese Cantidad: ");
        String cantidad = sss.nextLine();
        System.out.println("Ingrese fecha (dd-mm-aaaa)");
        String fecha = sss.nextLine();

        try {
            BufferedReader br = new BufferedReader(new FileReader(productos));
            BufferedWriter bw = new BufferedWriter(new FileWriter(pedidos, true));

            String linea;
            boolean hayProducto = false;
            while ((linea = br.readLine())!=null){
                String[] palabras = linea.trim().split("//|");
                if(palabras[0].equals(idProducto)){
                    hayProducto = true;
                }
            }
            //IDPedido|IDProducto|Cantidad|Fecha(DD-MM-AAAA);
            if(hayProducto){
                bw.write(id + "|" + idProducto + "|" + cantidad + "|" + fecha);
                System.out.println("pedido realizado correctamente");
                bw.newLine();
            }else {
                throw new Exception("No hay id de producto, no se puede realizar el pedido");
            }
            bw.close();
            br.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private static void borrarProducto(int id) {
        File archivo = new File("ficheros/recuperacionFicheros/productos.txt");
        File archivoModificado = new File("ficheros/recuperacionFicheros/productosModificado.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivoModificado, true));

            String linea;
            while ((linea = br.readLine())!=null){
                String[] datos = linea.trim().split("//|");
                for(int i = 0; i < datos.length; i++) {
                    if (Integer.parseInt(datos[0]) == id) {
                    } else {
                        bw.write(datos[i]);
                    }
                }
                bw.newLine();
            }
            bw.close();
            br.close();



        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private static void cargarProductos() {
        File archivo = new File("ficheros/recuperacionFicheros/productos.txt");
        Scanner ss = new Scanner(System.in);

        System.out.println("Introduzca ID: ");
        String id = ss.nextLine().trim();
        System.out.println("Introduzca nombre del producto: ");
        String nombre = ss.nextLine().trim();
        System.out.println("Introduzca precio: ");
        String precio = ss.nextLine().trim();
        System.out.println("Introduzca stock: ");
        String stock = ss.nextLine().trim();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
            BufferedReader br = new BufferedReader(new FileReader(archivo));

            String linea;
            boolean idRepetida = false;
            while ((linea = br.readLine())!=null){
                String[] mismaID = linea.trim().split("//|");
                for(int i = 0; i < mismaID.length; i++){
                    if(mismaID[0].equals(id)){
                        idRepetida = true;
                    }
                }
            }
            if(idRepetida){
                throw new Exception("El producto ya existe");
            }else{
                bw.write(id + "|" + nombre + "|" + precio + "|" + stock);
                bw.newLine();
                bw.close();
                System.out.println("Producto anadido");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}