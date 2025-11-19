let segundo, minuto, hora;
segundo=minuto=hora=0;
let semaforo;

function incrementarCont(){
    segundo++;
    if(segundo===60){
        segundo=0;
        minuto++;
    }else if(minuto===60){
        minuto=0;
        hora++;
    }

    document.getElementById("demo").innerHTML= segundo + " segundos, " + minuto + " minutos, " + hora + " horas";

}

function iniciarCrono(){
    if(!semaforo){
        semaforo=setInterval(incrementarCont,1000);
    }
    
}

function pararCrono(){
    clearInterval(semaforo);
    let bloque=document.getElementById("vueltas");
    let ciclo=document.createElement("p");
    ciclo.textContent=segundo + " segundos, " + minuto + " minutos, " + hora + " horas";
    bloque.appendChild(ciclo);

    semaforo=null;
}

function reiniciarCrono(){
    segundo=minuto=hora=0;
    document.getElementById("demo").innerHTML= "El cronommetro esta vacio";
    document.getElementById("vueltas").innerHTML="";
}