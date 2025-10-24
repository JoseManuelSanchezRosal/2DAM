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

        // Antes declaraba las tre variables con los tres colores y lo igualaba a i. Lo he borrado y directamente en el RGB le paso el valor de i

        // Aplicamos el color de fondo usando el formato "rgb(rojo,verder,azul)=(i, i, i)"
        
        div.style.backgroundColor = "rgb(" + i + "," + i + "," + i + ")";

        contenedor.appendChild(div); //Apendamos el div creado al contenedor padre (BODY)
    }
}