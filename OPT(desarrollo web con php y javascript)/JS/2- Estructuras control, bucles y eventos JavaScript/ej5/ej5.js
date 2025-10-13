// LA FUNCION ONLOAD SE EJECUTA CUANDO LA PAGINA TERMINA DE CARGARSE POR COMPLETO, ésta llama a la función para crear los divs
window.onload = function(){
    crearDivs();
}

// Función encargada de crear y dar color a los divs
function crearDivs(){
    let contenedor = document.body; // Seleccionamos el BODY como contenedor donde añadiremos los divs

    for (let i = 0; i < 255; i++){ 
        let div = document.createElement("div") // En cada iteración creamos un div. a cada div le decimos que pertenecerá a la clase colorDivs.
        div.className = "colorDivs";

        //Asignar el color rgb usando la propia iteracion de la variable i a cada div creado en cada vuelta
        let rojo = i;
        let verde = i;
        let azul = i;

        // Aplicamos el color de fondo usando el formato "rgb(rojo,verder,azul)"
        div.style.backgroundColor = "rgb(" + rojo + "," + verde + "," + azul + ")";


        contenedor.appendChild(div); //Apendamos el div creado al contenedor padre (BODY)
    }

}