let imagenes = ["imagenes/homer.gif", "imagenes/desaparece.gif", "imagenes/hunde.gif"];

let indice=0;

function cambioBanner(){

    indice++;
    if (indice===3){
        indice=0;
    }

    document.getElementById("imagen").src=imagenes[indice];
}