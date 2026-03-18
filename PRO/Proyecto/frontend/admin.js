// ======== ADMIN DOMAIN ========
app.admin = {
    init: async function() {
        // Default today
        document.getElementById('admin-date-filter').value = new Date().toISOString().split('T')[0];
        this.loadCitas();
        this.loadServices();
    },

    loadCitas: async function() {
        const dateStr = document.getElementById('admin-date-filter').value;
        const container = document.getElementById('admin-citas-list');
        container.innerHTML = 'Cargando citas...';

        try {
            const response = await fetch(`${API_URL}/citas`, {
                headers: app.getAuthHeaders()
            });

            if (response.ok) {
                const allCitas = await response.json();
                
                // Filter by selected date locally for prototype
                const filterDate = dateStr;
                const citasDia = allCitas.filter(c => c.fecha === filterDate);
                
                container.innerHTML = '';
                if (citasDia.length === 0) {
                    container.innerHTML = '<p class="empty-state">No hay citas para este día.</p>';
                    return;
                }

                // Sort by time
                citasDia.sort((a, b) => a.hora !== b.hora ? a.hora - b.hora : a.minutos - b.minutos);

                citasDia.forEach(cita => {
                    const servicio = cita.servicios && cita.servicios.length > 0 ? cita.servicios[0] : { nombre: 'Desconocido', duracionMinutos: 0 };
                    const cCliente = cita.usuario ? cita.usuario.nombre : "Cliente Anónimo";
                    
                    const minTotal = cita.minutos + servicio.duracionMinutos + 15;
                    const hrFin = cita.hora + Math.floor(minTotal / 60);
                    const minFin = minTotal % 60;

                    const div = document.createElement('div');
                    div.className = 'cita-card';
                    div.innerHTML = `
                        <button class="delete-btn" title="Eliminar Global" onclick="app.admin.deleteCita(${cita.id})"><i class="fas fa-trash"></i></button>
                        <strong>${String(cita.hora).padStart(2,'0')}:${String(cita.minutos).padStart(2,'0')} - ${String(hrFin).padStart(2,'0')}:${String(minFin).padStart(2,'0')}</strong>
                        <div style="margin-top: 5px;">
                            <span style="display:inline-block; background:#222; color:#fff; padding:2px 6px; border-radius:4px; font-size:0.8rem">${cCliente}</span>
                            <span>${servicio.nombre}</span>
                        </div>
                    `;
                    container.appendChild(div);
                });
            }
        } catch (error) {
            // MOCK FOR UI Testing
            container.innerHTML = `
                <div class="cita-card">
                    <button class="delete-btn" onclick="this.parentElement.remove()"><i class="fas fa-trash"></i></button>
                    <strong>09:00 - 10:00</strong>
                    <div style="margin-top: 5px;">
                        <span style="display:inline-block; background:#222; color:#fff; padding:2px 6px; border-radius:4px; font-size:0.8rem">María G.</span>
                        <span>Tinte y Mechas</span>
                    </div>
                </div>
            `;
        }
    },

    deleteCita: async function(id) {
        if(confirm("¿Eliminar esta cita del cuadrante?")) {
            try {
                await fetch(`${API_URL}/citas/${id}`, { method: 'DELETE', headers: app.getAuthHeaders() });
                app.showToast("Cita eliminada", "success");
                this.loadCitas();
            } catch(e) {
                app.showToast("Error", "error");
            }
        }
    },

    loadServices: async function() {
        const services = await app.loadServices();
        const container = document.getElementById('admin-services-list');
        container.innerHTML = '';
        
        services.forEach(s => {
            const div = document.createElement('div');
            div.className = 'service-item';
            div.innerHTML = `
                <div style="width: 100%; display: flex; justify-content: space-between;">
                    <strong>${s.nombre}</strong>
                    <span class="service-price">${s.precio}€</span>
                </div>
                <span style="font-size:0.9rem; color:var(--color-light-text)">${s.duracionMinutos} min · ${s.descripcion}</span>
                <div class="admin-actions">
                    <button class="btn btn-outline btn-small" onclick='app.admin.editService(${JSON.stringify(s)})'>Editar</button>
                    <button class="btn btn-danger btn-small" onclick="app.admin.deleteService(${s.id})">Borrar</button>
                </div>
            `;
            container.appendChild(div);
        });
    },

    editService: function(s) {
        document.getElementById('srv-id').value = s.id;
        document.getElementById('srv-name').value = s.nombre;
        document.getElementById('srv-price').value = s.precio;
        document.getElementById('srv-desc').value = s.descripcion;
        document.getElementById('srv-duration').value = s.duracionMinutos;
        
        document.getElementById('btn-save-service').textContent = "Actualizar Cambios";
        document.getElementById('btn-cancel-edit').classList.remove('hidden');
        window.scrollTo({ top: 0, behavior: 'smooth' });
    },

    resetServiceForm: function() {
        document.getElementById('serviceForm').reset();
        document.getElementById('srv-id').value = "";
        document.getElementById('btn-save-service').textContent = "Guardar Nuevo";
        document.getElementById('btn-cancel-edit').classList.add('hidden');
    },

    saveService: async function() {
        const id = document.getElementById('srv-id').value;
        const payload = {
            nombre: document.getElementById('srv-name').value,
            precio: parseFloat(document.getElementById('srv-price').value),
            descripcion: document.getElementById('srv-desc').value,
            duracionMinutos: parseInt(document.getElementById('srv-duration').value)
        };

        const method = id ? 'PUT' : 'POST';
        const url = id ? `${API_URL}/servicios/${id}` : `${API_URL}/servicios`;

        try {
            const response = await fetch(url, {
                method: method,
                headers: app.getAuthHeaders(),
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                app.showToast(id ? "Servicio actualizado." : "Nuevo servicio añadido.", "success");
                this.resetServiceForm();
                this.loadServices(); // reload
            } else {
                app.showToast("Error al guardar el servicio.", "error");
            }
        } catch (error) {
            // MOCK SUCCESS
            app.showToast(id ? "Servicio actualizado (Simulado)." : "Servicio añadido (Simulado).", "success");
            this.resetServiceForm();
        }
    },

    deleteService: async function(id) {
        if(confirm("¿Eliminar este servicio del catálogo?")) {
            try {
                await fetch(`${API_URL}/servicios/${id}`, { method: 'DELETE', headers: app.getAuthHeaders() });
                app.showToast("Servicio eliminado.", "success");
                this.loadServices();
            } catch(e) {
                app.showToast("Simulado: Servicio eliminado.", "success");
            }
        }
    }
};
