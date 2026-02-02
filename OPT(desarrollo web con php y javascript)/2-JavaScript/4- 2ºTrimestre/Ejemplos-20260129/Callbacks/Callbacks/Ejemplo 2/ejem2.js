function error(mensaje){
    alert("Ha fallado " + mensaje);
}

function mostrarResultado(numero){
    alert("El resultado es: " + numero);
}

function sumar(callback1,callback2){
    let num1=document.getElementById("ent1");
    let num2=document.getElementById("ent2");

    if(isNaN(num1.value)){
        callback2("la entrada numero 1 no es un numero");
        num1.value="";
    }else if(isNaN(num2.value)){
        callback2("la entrada numero 2 no es un numero");
        num2.value="";
    }else{
        let resultado = parseFloat(num1.value)+parseFloat(num2.value);
        callback1(resultado+"")
    }
}

function llamadaSuma(){
    sumar(mostrarResultado,error);
}