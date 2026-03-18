// ======== CLIENT DOMAIN ========

app.client = {
    initAuthClient: function() {
        if(document.getElementById('client-name-display')) {
            document.getElementById('client-name-display').textContent = app.state.userName || 'Cliente';
        }
        this.loadMisCitas();
    },

    loadMisCitas: async function() {
        const container = document.getElementById('client-citas-list');
        container.innerHTML = '<p class="empty-state">Buscando citas...</p>';

        try {
            console.log("Solicitando citas para el usuario...");
            const response = await fetch(`${API_URL}/citas`, {
                headers: app.getAuthHeaders()
            });
            
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }

            const citas = await response.json();
            console.log("Citas recibidas del servidor:", citas);

            container.innerHTML = '';
            
            if (!Array.isArray(citas) || citas.length === 0) {
                container.innerHTML = '<p class="empty-state">No tienes citas programadas.</p>';
                return;
            }

            citas.forEach(cita => {
                const servicio = (cita.servicios && cita.servicios.length > 0) 
                    ? cita.servicios[0] 
                    : { nombre: 'Servicio estándar', precio: 0 };
                
                const div = document.createElement('div');
                div.className = 'cita-card';
                div.innerHTML = `
                    <button class="delete-btn" title="Cancelar Cita" onclick="app.client.cancelCita(${cita.id})">
                        <i class="fas fa-times"></i>
                    </button>
                    <strong>${servicio.nombre}</strong><br>
                    <span><i class="far fa-calendar"></i> ${cita.fecha} a las ${String(cita.hora).padStart(2,'0')}:${String(cita.minutos).padStart(2,'0')}</span><br>
                    <span><i class="far fa-money-bill-alt"></i> ${servicio.precio}€</span>
                `;
                container.appendChild(div);
            });

        } catch (error) {
            console.error("Fallo completo en loadMisCitas:", error);
            container.innerHTML = '<p class="error-msg">No se han podido cargar tus citas.</p>';
        }
    },

    cancelCita: async function(id) {
        if(confirm("¿Estás seguro de cancelar esta reserva?")) {
            try {
                const response = await fetch(`${API_URL}/citas/${id}`, {
                    method: 'DELETE',
                    headers: app.getAuthHeaders()
                });
                if (response.ok || response.status === 204) {
                    app.showToast("Cita cancelada correctamente", "success");
                    this.loadMisCitas();
                }
            } catch (e) {
                app.showToast("Error al cancelar la cita", "error");
            }
        }
    }
};
