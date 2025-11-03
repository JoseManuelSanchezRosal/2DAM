
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

    subir(pasajeros) {
        if (this.pasajerosBus + pasajeros > this.capacidad) {
            const mensaje = "No pueden subir al bus esa cantidad de pasajeros (supera la capacidad máxima)";
            document.getElementById("salidaInfo").textContent = mensaje;
            document.getElementById("pasajeros-arriba").value = "";
            return;
        }
        this.pasajerosBus += pasajeros;
        const mensaje2 = `Se han subido ${pasajeros} pasajeros al bus`;
        document.getElementById("salidaInfo").textContent = mensaje2;
        document.getElementById("pasajeros-arriba").value = "";
    };

    bajar(pasajeros){
        if (pasajeros > this.pasajerosBus){
            const mensaje = "No pueden bajar del bus mas pasajeros de los que hay";
            document.getElementById("salidaInfo").textContent = mensaje;
            document.getElementById("pasajeros-abajo").value = "";
            return;
        }
        this.pasajerosBus -= pasajeros;
        const mensaje2 = `Se han bajado ${pasajeros} pasajeros del bus`;
        document.getElementById("salidaInfo").textContent = mensaje2;
    };

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

// LOS EVENTOS EN EL CLICK SE RECOMIENDA HACERLOSS CON ADDEVENTLISTENER ASOCIADOS A UNA ID DE UN BOTON POR EJEMPLO CON ESTA ESTRUCTURA:
// 1- VER INFO
const info = document.getElementById("verInfo");
info.addEventListener("click", function(){
    document.getElementById("salidaInfo").innerHTML = bus1.toString();
});

// 2- SUBIR PASAJEROS:
let subir = document.getElementById("subirPasajeros");
subir.addEventListener("click", function(){
    const valor = document.getElementById("pasajeros-arriba").value.trim();

    // Validar si está vacío o no es un número
    if (valor === "" || isNaN(valor) || parseInt(valor) <= 0) {
        document.getElementById("mensaje-subir").textContent = "Introduce un número válido de pasajeros.";
        return;
    }

    document.getElementById("mensaje-subir").textContent = ""; // limpiar mensaje
    const pasajeros = parseInt(valor);
    bus1.subir(pasajeros);
});

// 3- BAJAR PASAJEROS:
let bajar = document.getElementById("bajarPasajeros");
bajar.addEventListener("click", function(){
    const valor = document.getElementById("pasajeros-abajo").value.trim();

    // Validar si el campo está vacío, no es número o es <= 0
    if (valor === "" || isNaN(valor) || parseInt(valor) <= 0) {
        document.getElementById("mensaje-bajar").textContent = "Introduce un número válido de pasajeros.";
        return;
    }

    // Limpiar mensaje si el valor es correcto
    document.getElementById("mensaje-bajar").textContent = "";

    const pasajeros = parseInt(valor);
    bus1.bajar(pasajeros);

    // (opcional) limpiar el input tras bajar pasajeros
    document.getElementById("pasajeros-abajo").value = "";
});