function compararNum(){
    let entrada=document.getElementById("ent").value;

    if(!/^[0-9]*$/.test(entrada)){ //^[0-9] puede sustituirse tambien por \d
        alert("La cadena introducida no es un numero");
    }else{
        alert("La cadena introducida es un numero");
    }
}