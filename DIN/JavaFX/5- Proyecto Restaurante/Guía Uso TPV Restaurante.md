Guía Uso TPV Restaurante (.jar y zip)



Para poder abrir la aplicación, es necesario tener una base de datos. La configuración de la base de datos usada para este proyecto ha sido la siguiente:

* Usuario: tpv
* Contraseña: 1234
* Nombre de la base de datos: tpv
* Host: localhost o 127.0.0.1
* Puerto: 3306



En caso de que se quieran cambiar las credenciales anteriores, se debe de hacer en el fichero .env que está en raíz del proyecto.

Una vez que se tenga la base de datos configurada, hay que importar los archivos SQL ubicados en la carpeta sql en la raíz del proyecto:

* schema.sql: contiene el esquema de la base de datos.
* data.sql: contiene los datos de la base de datos.



Los ficheros deben importarse en el orden mencionado anteriormente.



Para este proyecto, se ha utilizado la versión 25 de Java.



En el caso de abrir la aplicación con el código fuente o el .zip, antes se deben descargar las librerías con Maven, ya sea haciéndolo desde el IDE o instalando Maven en el ordenador y usar el comando "mvn dependency:sources".

