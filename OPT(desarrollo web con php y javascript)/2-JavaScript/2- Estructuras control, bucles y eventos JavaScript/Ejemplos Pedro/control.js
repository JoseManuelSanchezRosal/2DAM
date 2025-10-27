function condicional1(){
    let x;
    x = 10;

    if(x>8){
        alert("X es mayor que 8");
    }else{
        alert("X es menor que 8");
    }
}

function condicional2(){
    let x;
    x = 3;

    switch(x){
        case 1:
            alert("X vale 1");
            break;
        case 1:
            alert("X vale 2");
            break;
        case 1:
            alert("X vale 3");
            break;
        default:
            alert("X vale mas de 3");
            break;
    }

}

function bucles1(){
    let i,cont;
    cont = 1;
    for(i=0 ; i<5 ; i++){
        cont*=2;
    }

    document.getElementById("salida").innerHTML = cont;
}

function bucles2(){
    let i,cont;
    cont = 1;
    while(i<5){
        i++;
        cont*=2;
    }

    document.getElementById("salida").innerHTML = cont;
}

function bucles3(){
    let i,cont;
    cont = 1;
    do{
        i++;
        cont*=2;
    }while(i<5)

    document.getElementById("salida").innerHTML = cont;
}