let imagenes = ["imagenes/homer.gif", "imagenes/desaparece.gif", "imagenes/hunde.gif"];

let indice=0;
let semaforo=null;

function iniciarBanner(){
    if(!semaforo){
       semaforo=setInterval(cambioBanner,3000); 
    }else{
        clearInterval(semaforo);
        semaforo=null;
    }
    
}

function cambioBanner(){

    indice++;
    if (indice===3){
        indice=0;
    }

    document.getElementById("imagen").src=imagenes[indice];
}

