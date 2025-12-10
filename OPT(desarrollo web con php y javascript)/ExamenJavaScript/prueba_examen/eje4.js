let crono=0;
let semaforo;
let control=0;

function iniciarCrono(){
    if(control==0){
        control=1;
        semaforo=setInterval(avanzarCrono,1000);
    }  
}

function iniciarCronoInv(){
    if(control==0){
        control=1;
        semaforo=setInterval(retrocederCrono,1000);
    }  
}

function retrocederCrono(){
    if(crono>0){
        crono--;
        document.getElementById("cont").innerHTML="Contador: " + crono;
    }
}

function avanzarCrono(){
    if(crono<100){
        crono++;
        document.getElementById("cont").innerHTML="Contador: " + crono;
    }
    
}

function pararCrono(){
    clearInterval(semaforo);
    control=0;

    
}



