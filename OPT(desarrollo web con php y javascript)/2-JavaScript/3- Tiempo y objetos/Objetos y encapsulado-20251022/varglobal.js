let contador=0;

function incrementar(){
    contador++;
    document.getElementById("cont").innerHTML=contador+"";//como la variable contador es INT, hacemos el =contador+"" para castear a string
}

function restablecer(){
    contador=0;
    document.getElementById("cont").innerHTML=contador+"";
}
