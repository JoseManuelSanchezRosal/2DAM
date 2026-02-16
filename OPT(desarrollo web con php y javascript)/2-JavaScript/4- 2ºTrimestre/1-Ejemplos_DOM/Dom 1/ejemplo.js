function realizarOperacion(){
    //Tomamos los valores de la página
    let opcion=document.getElementById("op");
    let numero1=document.getElementById("num1").value;
    let numero2=document.getElementById("num2").value;

    //recogemos el nodo de la tabla
    let tablahtml=document.getElementById("top");

    let resultado;

    //Creamos los elementos de la tabla
    let fila=document.createElement("tr");
    let col1=document.createElement("td");
    let col2=document.createElement("td");
    let col3=document.createElement("td");
    let col4=document.createElement("td");

    //introducimos la informacion el las celdas que acabamos de crear
    col1.textContent=numero1;
    col3.textContent=numero2;

    switch(opcion.value){
        case "1":
            resultado=parseFloat(numero1)+parseFloat(numero2);
            col2.textContent="+";
            col4.textContent=resultado;
            break;
        case "2":
            resultado=parseFloat(numero1)-parseFloat(numero2);
            col2.textContent="-";
            col4.textContent=resultado;
            break;
        case "3":
            resultado=parseFloat(numero1)/parseFloat(numero2);
            col2.textContent="/";
            col4.textContent=resultado;
            break;
        case "4":
            resultado=parseFloat(numero1)*parseFloat(numero2);
            col2.textContent="*";
            col4.textContent=resultado;
    }

    fila.appendChild(col1);
    fila.appendChild(col2);
    fila.appendChild(col3);
    fila.appendChild(col4);

    tablahtml.appendChild(fila);
}