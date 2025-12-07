function persona(nom="", ed="", tel="" , domi=""){
    let nombre=nom;
    let edad=ed;
    let telefono=tel;
    let domicilio=domi;

    this.introducirPersona = function(nom2, ed2, tel2 , domi2){
        if(nombre!=""){
            alert("La persona anterior esta eliminada");
            nombre=nom2;
            edad=ed2;
            telefono=tel2;
            domicilio=domi2;
        }else{
            nombre=nom2;
            edad=ed2;
            telefono=tel2;
            domicilio=domi2;
        }
    }

    this.mostarPersona = function(){
        document.getElementById("demo").innerHTML="El nombre de la persona es: " +nombre+ " su edad: "+edad;
    }

    this.eliminarPersona=function(){
        nombre="";
        edad="";
        telefono="";
        domicilio="";
    }
}

let persona1= new persona("Pedro","28","56432168","Calle de la piruleta");

function cambiarPersona(){
    persona1.introducirPersona("Mateo","50","652819561","Residencia de Springfield");
}
