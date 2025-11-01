class persona{

    constructor(nombre, apellido, edad){
        this.nombre=nombre;
        this.apellido=apellido;
        this.edad=edad;
    }

    getNombre(){
        return this.nombre;
    }

    getApellido(){
        return this.apellido;
    }

    getEdad(){
        return this.edad;
    }

    setNombre(nombre){
        this.nombre=nombre;
    }

    setApellido(apellido){
        this.apellido=apellido;
    }

    setEdad(edad){
        this.edad=edad;
    }

    nombreCompleto(){
        return "Mi nombre es: " + this.nombre + " " + this.apellido;
    }
}

let individuo = new persona();

function rellenarPersona(nomb, apll, edad){
    
    let nom=document.getElementById(nomb).value;
    let apl=document.getElementById(apll).value;
    let ed=document.getElementById(edad).value;

    individuo.setApellido(apl);
    individuo.setNombre(nom);
    individuo.setEdad(ed);

    document.getElementById("nombreE").innerHTML=individuo.nombreCompleto();
    document.getElementById("edad").innerHTML=individuo.getEdad();

}