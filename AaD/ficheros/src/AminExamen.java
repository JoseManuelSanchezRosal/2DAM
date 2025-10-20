/*Crear un programa que implemente las siguientes funcionalidades (Menú):
1 - Añadir alumnos
2 - Devolver el ID de un alumno (por nombre y apellido)
3 - Insertar notas
4 - Calcular la media de notas de un alumno

        1 - Añadir alumnos
Permite introducir por pantalla una lista de alumnos con la siguiente sintaxis:
Introduzca el nombre:
Introduzca los apellidos:
Introduzca la fecha de nacimiento (dd-mm-aaaa):
Introduzca la clase del alumno:
Al finalizar cada inserción:
Pulsa 1 si quieres insertar otro alumno, o 0 para salir.
(si el usuario pulsa 1, se repite el proceso. Si por el contrario pulsa 0, se sale de la opción).
Los alumnos se almacenarán en el fichero Alumnos.txt (crearlo si no existe) con el siguiente
formato:
ID|Nombre|Apellidos|Fecha de nacimiento|Clase;
Ejemplo de almacenamiento en el fichero Alumnos.txt si hemos insertado 3 alumnos:
—--------------------
1|Pepe|Perez|01-01-2000|1º DAM;
2|Antonio|López|02-02-2000|2º DAM;
3|Jorge|Garcia|03-03-2000|1º SMR;
—--------------------
El ID será autonumérico y consecutivo, continuando el último existente en el fichero.Si
añadimos un nuevo alumno, el programa debe asignar automáticamente el siguiente ID:
—--------------------
1|Pepe|Perez|01-01-2000|1º DAM;
2|Antonio|López|02-02-2000|2º DAM;
3|Jorge|Garcia|03-03-2000|1º SMR;
4|Antonio|Alvarez|04-04-2000|1º SMR;
—--------------------
        2 - Devolver el ID de un alumno (por nombre y apellido)
El programa solicitará el nombre y apellidos y buscará el alumno en Alumnos.txt. -
Si lo encuentra, devolverá su ID. -
Si no existe, lanzará una excepción controlada con el mensaje: "El alumno con
nombre <nombre> y apellido <apellido> no se encuentra en el archivo."
        3 - Permite añadir notas a un alumno en el archivo notas.txt (crearlo si no existe).
Primero se pide el nombre y apellidos, se obtiene el ID (usando la función anterior), y se
solicitan las notas separadas por ;.
Introduzca el nombre del alumno:
Antonio
Introduzca los apellidos del alumno:
López
        (internamente buscamos el ID - es el ID 2)
Introduzca las notas para el alumno 2(separados por ;):
        7.5;5;10;9;8.6
Resultado final del archivo notas.txt
—--------------------
        2|7.5;5;10;9;8.6
        —--------------------
Las notas son acumulativas: se pueden añadir más registros para el mismo alumno sin
eliminar los anteriores.
4 - Pide el nombre y apellidos, obtiene su ID y calcula la media de todas las notas asociadas
a ese alumno en notas.txt.
Reglas de tiempo y calificación
● Si entregas en ≤ 1h → 10.
        ● Si entregas en ≤ 1h 30’ → 7,5.
        ● Si entregas en ≤ 2h → 5.
*/

import java.io.File;
public class AminExamen {
    public static void main(String[] args) {
        File archivo;

    }
}
