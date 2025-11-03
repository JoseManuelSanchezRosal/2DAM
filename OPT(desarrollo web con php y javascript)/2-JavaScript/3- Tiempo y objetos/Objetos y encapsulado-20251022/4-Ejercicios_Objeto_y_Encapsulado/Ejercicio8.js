
// Clase Conductor
class Conductor{
    constructor(nombre, licencia){
        this.nombre = nombre;
        this.licencia = licencia;
    }
    toString(){
        return `${this.nombre}<br>
                Licencia: ${this.licencia}<br>`
    }
}

// Clase Bus
class Bus{
    constructor(capacidad, pasajerosBus, conductor){
        if(capacidad < 0){
            throw new Exception("No puedes indicar un bus con capacidad < 0 pasajeros")
        };
        
        if(pasajerosBus < 0){
            throw new Exception("No puedes indicar un bus con pasajeros < 0")
        };
            
        this.capacidad = capacidad;
        this.pasajerosBus = pasajerosBus;
        this.conductor = conductor;
        
    }

    subir(pasajeros){
        debugger
        if (! pasajeros + (this.pasajerosBus > this.capacidad)){
            const mensaje = "No pueden subir al bus esa cantidad de pasajeros";
            document.getElementById("salidaInfo").textContent = mensaje;
            return;
        }

        this.pasajerosBus += pasajeros;
        const mensaje2 = `Se han subido ${pasajeros} pasajeros al bus`;
        document.getElementById("salidaInfo").textContent = mensaje2;
    }

    bajar(pasajeros){
        if (pasajeros > this.pasajerosBus){
            const mensaje = "No pueden bajar del bus mas pasajeros de los que hay";
            document.getElementById("salidaInfo").textContent = mensaje;
            return;
        }

        this.pasajerosBus -= pasajeros;
        const mensaje2 = `Se han bajado ${pasajeros} pasajeros del bus`;
        document.getElementById("salidaInfo").textContent = mensaje2;
    }
    toString(){
        return `Bus con capacidad para ${this.capacidad}<br>
                Pasajeros actuales: ${this.pasajerosBus}<br>
                Conductor: ${this.conductor}`
    }
}

// Variables con CONST >> no se puede reasignar otro valor a esa variable
const conductor1 = new Conductor("Pepito", 1);

// Creamos un bus:
let bus1 = new Bus(51, 0, conductor1);

const info = document.getElementById("verInfo");
info.addEventListener("click", function(){
    document.getElementById("salidaInfo").innerHTML = bus1.toString();
});

let subir = document.getElementById("subirPasajeros");
subir.addEventListener("click", function(){
    const pasajeros = parseInt(document.getElementById("pasajeros-arriba").value);
    bus1.subir(pasajeros);
    document.getElementById("mensaje-subir").textContent = `Se han subido ${pasajeros} pasajeros`;
    

});

let bajar = document.getElementById("bajarPasajeros");
bajar.addEventListener("click", function(){
    const pasajeros = parseInt(document.getElementById("pasajeros-abajo").value);
    bus1.bajar(pasajeros);
    document.getElementById("mensaje-bajar").textContent = `Se han bajado ${pasajeros} pasajeros`;
});