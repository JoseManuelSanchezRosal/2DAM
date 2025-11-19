let segundo,minuto;
segundo=0;
minuto=0;

function incrementarCont(){
    if(segundo===60){
        segundo=0;
        minuto++;
    }else{
        segundo++;
    }

    document.getElementById("demo").innerHTML="La pagina lleva abierta " + minuto + " y " + segundo + " segundos";
}