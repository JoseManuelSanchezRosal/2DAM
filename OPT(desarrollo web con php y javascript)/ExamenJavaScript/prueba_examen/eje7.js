class asignatura{
    constructor(nombreA, cred, numA){
        this.nombre=nombreA;
        this.creditos=cred;
        this.numMa=numA;
    }

    getNombre(){
        return this.nombre;
    }

    setNombre(nombreA){
        this.nombre=nombreA;;
    }
    
    getCreditos(){
        return this.creditos;
    }

    setCreditos(credA){
        this.creditos=credA;
    }

    getNumMax(){
        return this.numMa;
    }

    setNumMax(nmA){
        this.numMa=nmA;
    }
}

class curso{
    constructor(nombC="", asig=[]){
        this.nombreCurso=nombC;
        this.asignaturas=asig;
    }

    AnyadirAsignatura(asig){
        let control=0;

        for(let i=0 ; i<this.asignaturas.length ; i++){
            if(this.asignaturas[i].getNombre()===asig.getNombre()){
                control=1;
            }
        }

        if(control==0){
            this.asignaturas.push(asig);
        }else{
            alert("La asignatura ya existe");
        }
        
    }

    EliminarAsignatura(nAsig){
        for(let i=0; i<this.asignaturas.length; i++){
            if(this.asignaturas[i].getNombre()===nAsig){
                this.asignaturas.splice(i,1);
                break;
            }
        }
    }

    MostrarCurso(){
        document.getElementById("curso").innerHTML="Curso: " + this.nombreCurso + " y tiene " + this.asignaturas.length + " asignaturas";
    }

    ListaAsignaturas(){
        let contenedor=document.getElementById("lista");
        contenedor.innerHTML="";
     
        let asig;

        for(let i=0; i<this.asignaturas.length; i++){

            let parrafo=document.createElement("p");

            parrafo.textContent="Nombre de la asignatura: " + this.asignaturas[i].getNombre() + " Nº de creditos: " + this.asignaturas[i].getCreditos() + " Nº maximo de alumnos: " + this.asignaturas[i].getNumMax();
            contenedor.appendChild(parrafo);
        }
    
    }

    MostrarAsigatura(nAsig){

        let control=0;
        for(let i=0; i<this.asignaturas.length; i++){
            if(this.asignaturas[i].getNombre()===nAsig){

                document.getElementById("asignatura").innerHTML="Nombre de la asignatura: " + this.asignaturas[i].getNombre() + " Nº de creditos: " + this.asignaturas[i].getCreditos() + " Nº maximo de alumnos: " + this.asignaturas[i].getNumMax();

                control=1;
                break;
            }
        }
        
        if(control==0){
            document.getElementById("asignatura").innerHTML="Asignatura no encontrada";
        }
    }
}
let arraAsig=[];
let curso1;

function cargarDatos(){
    let asig1= new asignatura("Acceso a datos", 6, 40);
    let asig2= new asignatura("Diseño de interfaces", 6, 40);
    let asig3= new asignatura("Programacion web", 6, 40);
    let asig4= new asignatura("Programacion multimedia", 6, 40);

    arraAsig.push(asig1);
    arraAsig.push(asig2);
    arraAsig.push(asig3);
    arraAsig.push(asig4);

    curso1= new curso("2º DAM", arraAsig);

    introducirAsig();
    introducirAsig();

    curso1.EliminarAsignatura("Acceso a datos II");
}

function introducirAsig(){
    let nAsig= new asignatura("Acceso a datos II", 6, 40);
    curso1.AnyadirAsignatura(nAsig);
}

function buscarAsig(){
    let nAsig=document.getElementById("cadena").value;
    curso1.MostrarAsigatura(nAsig);
}