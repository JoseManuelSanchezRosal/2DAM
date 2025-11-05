
function aplicarDescuento(precio, descuento){
    //Obtenemos los valores de los input precio y descuento y los parseamos a decimal
    this.precio = precio;
    this.descuento = descuento;

    // Construimos el objeto articulo con precio, descuento y metodo neto:
    this.precioNeto = function(){
        return this.precio - ((this.precio * this.descuento) / 100);
    }
}

function mostrarDescuento(){
    let p = document.getElementById("precio").value;
    let d = document.getElementById("descuento").value;
    const articulo = new aplicarDescuento(p, d);
    document.getElementById("inputPrecioFinal").value = articulo.precioNeto()
}
