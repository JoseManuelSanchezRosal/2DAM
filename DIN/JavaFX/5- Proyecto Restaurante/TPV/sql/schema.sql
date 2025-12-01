CREATE TABLE Categoria (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE Producto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60) UNIQUE NOT NULL,
    precio FLOAT(5, 2) UNSIGNED NOT NULL,
    imagen VARCHAR(30) NOT NULL,
    stock TINYINT UNSIGNED NOT NULL,
    id_categoria INT NOT NULL
);

CREATE TABLE Mesa (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero TINYINT UNSIGNED UNIQUE NOT NULL
);

CREATE TABLE Producto_Mesa (
    id_producto INT,
    id_mesa INT,
    cantidad INT UNSIGNED
);

ALTER TABLE Producto
ADD CONSTRAINT fk_producto_idCategoria FOREIGN KEY (id_categoria) REFERENCES Categoria(id)
;

ALTER TABLE Producto_Mesa
ADD CONSTRAINT pk_productoMesa PRIMARY KEY (id_producto, id_mesa),
ADD CONSTRAINT fk_productoMesa_idProducto FOREIGN KEY (id_producto) REFERENCES Producto(id),
ADD CONSTRAINT fk_productoMesa_idMesa FOREIGN KEY (id_mesa) REFERENCES Mesa(id)
;