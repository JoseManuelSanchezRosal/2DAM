function cambioDivisa(){
    let cantidad=document.getElementById("moneda").value;
    let tipo1=document.getElementById("tipo1").value;
    let tipo2=document.getElementById("tipo2").value;

    if(tipo1 === tipo2){
        document.getElementById("demo").innerHTML="Son del mismo tipo"
    }else if(tipo1 == 1 && tipo2 == 2){
        let cambio=parseFloat(cantidad)*0.88;
        document.getElementById("demo").innerHTML= cantidad + " euros son " + cambio + " libras";
    }else if(tipo1 == 2 && tipo2 == 1){
        let cambio=parseFloat(cantidad)/0.88;
        document.getElementById("demo").innerHTML= cantidad + " libras son " + cambio + " euros";
    }else if(tipo1 == 1 && tipo2 == 3){
        let cambio=parseFloat(cantidad)*1.15;
        document.getElementById("demo").innerHTML= cantidad + " euros son " + cambio + " $ USA";
    }else if(tipo1 == 3 && tipo2 == 1){
        let cambio=parseFloat(cantidad)/1.15;
        document.getElementById("demo").innerHTML= cantidad + " $ USA son " + cambio + " euros";
    }else if(tipo1 == 2 && tipo2 == 3){
        let cambio=parseFloat(cantidad)*1.31;
        document.getElementById("demo").innerHTML= cantidad + " libras son " + cambio + " $ USA";
    }else if(tipo1 == 3 && tipo2 == 2){
        let cambio=parseFloat(cantidad)/1.31;
        document.getElementById("demo").innerHTML= cantidad + " $ USA son " + cambio + " libras";
    }
}