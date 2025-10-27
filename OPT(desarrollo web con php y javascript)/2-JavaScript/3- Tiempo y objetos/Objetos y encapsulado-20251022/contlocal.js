function contadorLocal(indice){
    let contador=0;

    function incrementar(){
        contador++;
        document.getElementById("cont").innerHTML=contador+"";
    }

    function restablecer(){
        contador=0;
        document.getElementById("cont").innerHTML=contador+"";
    }

    if(indice == 1){
        incrementar();
    }else{
        restablecer();
    }

}
