let imagenes=[];
let frases=[];

function cargarDatos(){
    for (let i=0 ; i < 10 ; i++){
        imagenes.push("images/"+i+".jpg");
    }

    frases.push("0 dias sin accidentes");
    frases.push("1 COD al año no hace daño");
    frases.push("2 semanas para terminar el trimestre");
    frases.push("3 reyes magos");
    frases.push("4 esquinas tiene mi cama");
    frases.push("5 dedos tiene cada mano");
    frases.push("6 meses es la mitad de un año");
    frases.push("7 bolas de dragon");
    frases.push("8 es bodas y un funeral");
    frases.push("9 y no se me ocurre nada");

}

function cambio(){
    let numeroAleatorio = Math.floor(Math.random() * 10);
    imagen=document.getElementById("imagen");
    imagen.src=imagenes[numeroAleatorio];

    document.getElementById("frase").innerHTML=frases[numeroAleatorio];

}