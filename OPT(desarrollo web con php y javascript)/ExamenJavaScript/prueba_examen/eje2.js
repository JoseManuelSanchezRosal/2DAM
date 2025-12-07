function comprobarParam(){
    let cuadros=document.getElementsByTagName("input");
    let alertas=document.getElementsByTagName("span");

    for(let i=0 ; i < cuadros.length ; i++){
        if(i==0){
            if(!/^[A-Za-z]*$/.test(cuadros[i].value)){
                cuadros[i].value="";
                alertas[i*2+1].innerHTML="El parametro introducido es incorrecto";
            }else{
                alertas[i*2+1].innerHTML="";
            }
        }else if(i==1){
            if(!/^\d*$/.test(cuadros[i].value)){
                cuadros[i].value="";
                alertas[i*2+1].innerHTML="El parametro introducido es incorrecto";
            }else{
                alertas[i*2+1].innerHTML="";
            }
        }else{
            if(!/^[A-Za-z0-9]*$/.test(cuadros[i].value)){
                cuadros[i].value="";
                alertas[i*2+1].innerHTML="El parametro introducido es incorrecto";
            }else{
                alertas[i*2+1].innerHTML="";
            }
        }
    }

}