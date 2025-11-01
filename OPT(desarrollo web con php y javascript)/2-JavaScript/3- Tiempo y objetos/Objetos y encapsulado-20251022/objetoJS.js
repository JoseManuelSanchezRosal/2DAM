//Objeto literales, se puede acceder a sus variables internas
let persona1 = {
    edad: 20,
    nombre: "Alvaro",
    apellidos: "Conde Bazan",
    nombre_completo: {nomb: "Alvaro", apll: "Conde Bazan"},

    salidaNombre: function(){
        return "Mi nombre es: " + this.nombre + " " + this.apellidos;
    },

    salidaNombre2: function(){
        return "Mi nombre es: " + this.nombre_completo.nomb + " " + this.nombre_completo.apll;
    }

}

function nombrePersona(){
    document.getElementById("demo").innerHTML=persona1.salidaNombre();
}

//Constructor de objetos
function personaDeclarada(){
    let nombreO; //Estas variables son privadas
    let apellidosO;
    let edadO;
    this.variablePublica = "Publica"; //Esta variable es pública

    //Estas funciones son públicas
    this.getNombre = function(){
        return nombreO;
    }

    this.getApellidos = function(){
        return apellidosO;
    }

    //Funciones privadas
    setNombre = function(nomb){
        nombreO=nomb;
    }

    setApellido = function(apll){
        apellidosO=apll;
    }

    this.setNombreCompleto = function(nomb, apll){
        setNombre(nomb);
        setApellido(apll);
    }

}

let persona2 = new personaDeclarada();

function rellenarNombre(){
    let nomb, apll;
    nomb = document.getElementById("tnombre").value;
    apll = document.getElementById("tapellidos").value;

    /*
    persona2.setApellido(apll);
    persona2.setNombre(nomb);
    */
    persona2.setNombreCompleto(nomb,apll);
}

function mostrarNombreObjeto(){
    let nombrecompleto= "Hola me llamo " + persona2.getNombre() + " " + persona2.getApellidos();
    document.getElementById("demo2").innerHTML=nombrecompleto;
}