function saludar(nombre){
    alert("Hola " + nombre);
}

function llamada(callback){
    let nombre=document.getElementById("name").value;
    callback(nombre);
 }

function llamada2(){
    let nombre=document.getElementById("name").value;
    return nombre;
}