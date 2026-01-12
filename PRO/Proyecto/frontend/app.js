const API_URL = "http://localhost:8080/api";

async function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const errorMsg = document.getElementById("error-msg");

    // 1. Crear la cabecera de autenticación "Basic Auth"
    // btoa() convierte texto a Base64 (lo que necesita el estándar)
    const authHeader = 'Basic ' + btoa(email + ':' + password);

    try {
        // 2. Intentamos pedir las citas con esa credencial
        const response = await fetch(API_URL + "/citas", {
            method: "GET",
            headers: {
                "Authorization": authHeader,
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            // 3. Si es 200 OK: Guardamos la "llave" y mostramos el panel
            localStorage.setItem("auth", authHeader);
            mostrarPanel();
            cargarCitas(await response.json());
        } else {
            // 4. Si es 401: Mostramos error
            errorMsg.style.display = "block";
        }

    } catch (error) {
        console.error("Error de conexión:", error);
        alert("El servidor parece estar apagado.");
    }
}

function mostrarPanel() {
    document.getElementById("login-section").style.display = "none";
    document.getElementById("panel-citas").style.display = "block";
}

function cargarCitas(citas) {
    const lista = document.getElementById("lista-citas");
    lista.innerHTML = ""; // Limpiar

    citas.forEach(cita => {
        const div = document.createElement("div");
        div.className = "cita-card";
        div.innerHTML = `
            <strong>${cita.nombreServicio}</strong> (${cita.precio}€)<br>
            <small>Cliente: ${cita.nombreCliente}</small><br>
            📅 Inicio: ${new Date(cita.fechaInicio).toLocaleString()}<br>
            🏁 Fin: ${new Date(cita.fechaFin).toLocaleString()}
        `;
        lista.appendChild(div);
    });
}

function logout() {
    localStorage.removeItem("auth");
    location.reload(); // Recargar página
}