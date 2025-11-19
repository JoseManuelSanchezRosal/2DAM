function cambio(){
    let tabla, filas, celdas;

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