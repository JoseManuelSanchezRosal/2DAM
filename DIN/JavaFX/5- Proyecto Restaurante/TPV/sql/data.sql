INSERT INTO Categoria (id, nombre) VALUES
(1, 'Bebidas'),
(2, 'Entrantes'),
(3, 'Carnes'),
(4, 'Pescados')
;

INSERT INTO Mesa (numero) VALUES
(1),
(2),
(3),
(4)
;

INSERT INTO Producto (nombre, precio, imagen, stock, id_categoria) VALUES
('Agua', 1, 'agua.jpg', 5, 1),
('Cerveza', 2.5, 'cerveza.jpeg', 5, 1),
('Zumo', 2, 'zumo.jpg', 5, 1),
('Ribera', 4, 'ribera.jpg', 5, 1),
('Refresco', 2, 'refresco.jpg', 5, 1),
('Tinto', 2, 'tinto.png', 5, 1),
('Ensaladilla', 4, 'ensaladilla.jpg', 5, 2),
('Gambas', 5, 'gambas.jpeg', 5, 2),
('Jamón', 5, 'jamon.jpg', 5, 2),
('Bravas', 4, 'bravas.jpg', 5, 2),
('Queso', 5, 'queso.jpg', 5, 2),
('Croquetas', 6, 'croquetas.jpg', 5, 2),
('Solomillo', 14, 'solomillo.jpg', 5, 3),
('Pollo', 15, 'pollo.jpg', 5, 3),
('Pluma', 12, 'pluma.jpg', 5, 3),
('Chipirones', 12, 'chipirones.jpg', 5, 4),
('Pulpo', 14, 'pulpo.jpg', 5, 4),
('Lubina', 18, 'lubina.jpg', 5, 4)
;

INSERT INTO Producto_Mesa (id_producto, id_mesa, cantidad) VALUES
(1, 2, 3),
(1, 1, 5),
(2, 2, 1)
;