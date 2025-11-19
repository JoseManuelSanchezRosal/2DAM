function cambio(){
    let tabla, filas, celdas;
    clearInterval(semaforo);
    tabla=document.createElement("table");

    for(let i=0; i<5 ; i++){
            filas=document.createElement("tr");
        for(let j=0; j<5;j++){
            celdas=document.createElement("td");
            celdas.textContent="Fila: " + i + " colummna: " +j;
            filas.appendChild(celdas);
        }
        tabla.appendChild(filas);
    }
    
    document.getElementById("cont").innerHTML="";
    document.getElementById("cont").appendChild(tabla);

}
let semaforo;
let movimiento=0;
function moverGif(){
    semaforo=setInterval(mover,100);
}

function mover(){
    let imagen=document.getElementById("img1");
    imagen.style.marginLeft=movimiento+"px";
    movimiento+=10;
}