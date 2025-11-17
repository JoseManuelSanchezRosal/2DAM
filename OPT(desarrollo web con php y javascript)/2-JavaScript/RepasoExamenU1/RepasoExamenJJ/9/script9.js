const { useState } = require("react");

const $busqueda = document.getElementById('busqueda');
const $resultado = document.getElementById('resultado');

class Producto {
    #nombre;
    #unidades;

    constructor(nombre, unidades) {
        this.#nombre = nombre;
        this.#unidades = unidades;
    }

    getNombre() {
        return this.#nombre;
    }

    getUnidades() {
        return this.#unidades;
    }
}

class Compra {
    #productos;
    #fecha;

    constructor(productos, fecha) {
        this.#productos = productos;
        this.#fecha = fecha;
    }

    getProductos() {
        return this.#productos;
    }

    getFecha() {
        return this.#fecha;
    }
}

const compra = new Compra(
    [
        new Producto('A', 10),
        new Producto('B', 20),
        new Producto('C', 30)
    ],
    '2025-11-17 20:00:00'
);

$busqueda.addEventListener('keyup', ({ target, key }) => {
    if (key !== 'Enter') {
        return;
    }
    const { value: productoBuscado } = target;
    const productos = compra.getProductos();
    for (const producto of productos) {
        if (productoBuscado === producto.getNombre()) {
            $resultado.textContent = `Se encontró el producto ${productoBuscado} con ${producto.getUnidades()} unidades`;
            return;
        } else {
            $resultado.textContent = `No se encontró el producto`;
        }
    }
});