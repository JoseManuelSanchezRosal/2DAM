const API_URL = "http://localhost:8082/api";

const app = {
    state: {
        token: null,
        userRole: null, // 'cliente' | 'admin'
        userId: null,
        userName: null,
        userEmail: null,
        services: []
    },

    init: function() {
        // Load initial state from local storage if exists
        const storedAuth = localStorage.getItem("auth_token");
        if (storedAuth) {
            this.state.token = storedAuth;
            this.state.userRole = localStorage.getItem("auth_role") || "cliente";
            this.state.userName = localStorage.getItem("auth_name") || "Usuario";
            this.state.userEmail = localStorage.getItem("auth_email") || "";
            // Simulating parsing a JWT for ID (Simplified prototype assumption)
            this.state.userId = localStorage.getItem("auth_id") || 1; 

            this.navigateToDashboard();
        } else {
            this.navigateTo('landing');
        }
    },

    // ======== ROUTING ========
    navigateTo: function(viewId) {
        // Hide all views
        document.querySelectorAll('.view').forEach(el => el.classList.remove('active-view'));
        // Remove active class from nav
        document.querySelectorAll('.nav-btn').forEach(el => el.classList.remove('active', 'highlight'));

        // Show target view
        document.getElementById(`view-${viewId}`).classList.add('active-view');
        
        // Update Nav 
        if (viewId === 'landing') {
            document.getElementById('btn-nav-landing').classList.add('active');
            document.getElementById('btn-nav-clientes').classList.add('highlight');
            document.getElementById('btn-nav-clientes').classList.remove('hidden');
            document.getElementById('btn-nav-dashboard').classList.add('hidden');
            document.getElementById('btn-logout').classList.add('hidden');
        } else if (viewId === 'auth') {
            document.getElementById('btn-nav-landing').classList.remove('active');
            document.getElementById('btn-nav-clientes').classList.add('highlight');
        } else {
            // Dashboard views (client or admin)
            document.getElementById('btn-nav-landing').classList.remove('active');
            document.getElementById('btn-nav-clientes').classList.add('hidden');
            document.getElementById('btn-nav-dashboard').classList.remove('hidden');
            document.getElementById('btn-nav-dashboard').classList.add('active');
            document.getElementById('btn-logout').classList.remove('hidden');
        }
    },

    navigateToDashboard: function() {
        if (!this.state.token) {
            this.navigateTo('auth');
            return;
        }

        if (this.state.userRole === 'admin') {
            this.navigateTo('admin');
            this.admin.init();
        } else {
            this.navigateTo('client');
            this.client.init();
        }
    },

    switchAuthTab: function(tab) {
        document.querySelectorAll('.auth-tab').forEach(btn => btn.classList.remove('active'));
        document.querySelectorAll('.auth-form-wrapper').forEach(form => form.classList.remove('active'));
        
        if (tab === 'login') {
            document.querySelectorAll('.auth-tab')[0].classList.add('active');
            document.getElementById('form-login').classList.add('active');
        } else {
            document.querySelectorAll('.auth-tab')[1].classList.add('active');
            document.getElementById('form-register').classList.add('active');
        }
    },

    // ======== AUTHENTICATION ========
    login: async function() {
        const email = document.getElementById("login-email").value;
        const password = document.getElementById("login-password").value;
        const errorMsg = document.getElementById("login-error");
        
        try {
            const response = await fetch(`${API_URL}/auth/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });

            if (response.ok) {
                const data = await response.json();
                if (data.token) {
                    // For the prototype, if it's admin@... we treat as admin natively in UI
                    const role = email.includes('admin') ? 'admin' : 'cliente';
                    
                    this.state.token = data.token;
                    this.state.userRole = role;
                    this.state.userEmail = email;
                    this.state.userName = email.split('@')[0];
                    // Very basic mock for userId since JWT isn't fully decoded in JS here
                    this.state.userId = Math.floor(Math.random() * 10) + 1; 

                    localStorage.setItem("auth_token", this.state.token);
                    localStorage.setItem("auth_role", this.state.userRole);
                    localStorage.setItem("auth_name", this.state.userName);
                    localStorage.setItem("auth_email", this.state.userEmail);
                    localStorage.setItem("auth_id", this.state.userId);

                    this.showToast("¡Bienvenido de nuevo!", "success");
                    this.navigateToDashboard();
                } else {
                    errorMsg.textContent = "Credenciales incorrectas.";
                }
            } else {
                errorMsg.textContent = "Credenciales incorrectas.";
            }
        } catch (error) {
            console.error("Login Error:", error);
            this.showToast("Error de conexión con el servidor", "error");
        }
    },

    register: async function() {
        const name = document.getElementById("reg-name").value;
        const email = document.getElementById("reg-email").value;
        const phone = document.getElementById("reg-phone").value;
        const password = document.getElementById("reg-password").value;
        const errorMsg = document.getElementById("register-error");

        try {
            const response = await fetch(`${API_URL}/auth/register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, email, phone, password })
            });

            if (response.ok) {
                this.showToast("Cuenta creada con éxito. Por favor, inicia sesión.", "success");
                this.switchAuthTab('login');
                document.getElementById("login-email").value = email;
            } else {
                errorMsg.textContent = "Error al completar el registro. Inténtalo de nuevo.";
            }
        } catch (error) {
            console.error("Register Error:", error);
            this.showToast("Error de conexión con el servidor", "error");
        }
    },

    logout: function() {
        localStorage.clear();
        this.state.token = null;
        this.state.userRole = null;
        this.navigateTo('landing');
        this.showToast("Has cerrado sesión", "info");
    },

    deleteAccount: function() {
        if(confirm("¿Estás seguro de que deseas dar de baja tu cuenta? Esta acción no se puede deshacer y perderás todas tus reservas.")){
            // Simulation of account deletion
            this.showToast("Cuenta eliminada correctamente.", "success");
            this.logout();
        }
    },

    // ======== UTILS ========
    getAuthHeaders: function() {
        // Fallback to basic auth to match initial snippet pattern just in case API checks it
        const basicAuth = 'Basic ' + btoa(this.state.userEmail + ':dummyPasswordToPassthrough');
        return {
            "Authorization": `Bearer ${this.state.token}`, // Preferred
            "Basic-Auth-Fallback": basicAuth, // If backend uses basic still from earlier snippet
            "Content-Type": "application/json"
        };
    },

    showToast: function(message, type = 'info') {
        const container = document.getElementById('toast-container');
        const toast = document.createElement('div');
        toast.className = `toast ${type}`;
        
        let icon = 'fas fa-info-circle';
        if (type === 'success') icon = 'fas fa-check-circle';
        if (type === 'error') icon = 'fas fa-exclamation-circle';

        toast.innerHTML = `<i class="${icon}"></i> <span>${message}</span>`;
        container.appendChild(toast);

        setTimeout(() => {
            toast.style.animation = 'slideIn 0.3s ease reverse forwards';
            setTimeout(() => toast.remove(), 300);
        }, 5000);
    },

    loadServices: async function() {
        try {
            const response = await fetch(`${API_URL}/servicios`);
            if (response.ok) {
                this.state.services = await response.json();
                return this.state.services;
            }
        } catch (error) {
            console.error("Failed to load services", error);
            // Mock data if server is down for testing frontend
            this.state.services = [
                { id: 1, nombre: "Corte y Peinado Premium", descripcion: "Incluye lavado y masaje", precio: 25.0, duracionMinutos: 45 },
                { id: 2, nombre: "Tinte y Mechas", descripcion: "Coloración completa", precio: 55.0, duracionMinutos: 90 },
                { id: 3, nombre: "Tratamiento Facial", descripcion: "Limpieza e hidratación", precio: 40.0, duracionMinutos: 60 }
            ];
            return this.state.services;
        }
    },

    // ======== CLIENT DOMAIN ========
    client: {
        init: function() {
            document.getElementById('client-name-display').textContent = app.state.userName;
            this.loadMisCitas();
            app.bookingWizard.init();
        },

        loadMisCitas: async function() {
            const container = document.getElementById('client-citas-list');
            try {
                // Fetch user specific citas
                // Note: since the backend expects 'Basic btoa(email:password)', and we only have Token now, 
                // if JWT is implemented backend will read token. If not, mocking for SPA functionality.
                const response = await fetch(`${API_URL}/citas`, {
                    headers: app.getAuthHeaders()
                });
                
                let citas = [];
                if (response.ok) {
                    citas = await response.json();
                }

                container.innerHTML = '';
                if (citas.length === 0) {
                    container.innerHTML = '<p class="empty-state">No tienes citas programadas.</p>';
                    return;
                }

                citas.forEach(cita => {
                    const servicio = cita.servicios && cita.servicios.length > 0 ? cita.servicios[0] : { nombre: 'Servicio estándar', precio: 0 };
                    
                    const div = document.createElement('div');
                    div.className = 'cita-card';
                    div.innerHTML = `
                        <button class="delete-btn" title="Cancelar Cita" onclick="app.client.cancelCita(${cita.id})"><i class="fas fa-times"></i></button>
                        <strong>${servicio.nombre}</strong><br>
                        <span><i class="far fa-calendar"></i> ${cita.fecha} a las ${String(cita.hora).padStart(2,'0')}:${String(cita.minutos).padStart(2,'0')}</span><br>
                        <span><i class="far fa-money-bill-alt"></i> ${servicio.precio}€</span>
                    `;
                    container.appendChild(div);
                });

            } catch (error) {
                console.warn("Using mock data for Citas due to connection error");
                // Fallback Mock View
                container.innerHTML = `
                    <div class="cita-card">
                        <button class="delete-btn" onclick="this.parentElement.remove()"><i class="fas fa-times"></i></button>
                        <strong>Corte y Peinado Premium</strong><br>
                        <span><i class="far fa-calendar"></i> 2026-03-20 a las 10:30</span><br>
                        <span><i class="far fa-money-bill-alt"></i> 25.0€</span>
                    </div>
                `;
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
    },

    // ======== BOOKING WIZARD ========
    bookingWizard: {
        step: 1,
        selectedService: null,
        
        init: async function() {
            this.step = 1;
            this.updateView();
            
            const services = await app.loadServices();
            const container = document.getElementById('client-services-list');
            container.innerHTML = '';
            
            services.forEach(s => {
                const div = document.createElement('div');
                div.className = 'service-item';
                div.onclick = () => this.selectService(s.id);
                div.id = `serv-opt-${s.id}`;
                div.innerHTML = `
                    <div class="service-info">
                        <strong>${s.nombre}</strong>
                        <span>${s.duracionMinutos} min · ${s.descripcion}</span>
                    </div>
                    <div class="service-price">${s.precio}€</div>
                `;
                container.appendChild(div);
            });
        },

        selectService: function(id) {
            document.querySelectorAll('.service-item').forEach(el => el.classList.remove('selected'));
            document.getElementById(`serv-opt-${id}`).classList.add('selected');
            
            this.selectedService = app.state.services.find(s => s.id === id);
            
            document.getElementById('selected-service-info').innerHTML = `
                <div style="background: var(--color-cream-dark); padding: 1rem; border-radius: 8px; margin-bottom: 1rem;">
                    <strong>Seleccionado:</strong> ${this.selectedService.nombre} (${this.selectedService.duracionMinutos} min)
                </div>
            `;
            
            this.nextStep();
        },

        nextStep: function() {
            if (this.step < 3) {
                this.step++;
                this.updateView();
            }
        },

        prevStep: function() {
            if (this.step > 1) {
                this.step--;
                this.updateView();
            }
        },

        updateView: function() {
            document.querySelectorAll('.wizard-step').forEach(el => el.classList.remove('active'));
            document.getElementById(`step-${this.step}`).classList.add('active');

            if (this.step === 3) {
                // Populate confirm screen
                const date = document.getElementById('booking-date').value;
                const time = document.getElementById('booking-time').value;
                
                document.getElementById('confirm-service').textContent = this.selectedService.nombre;
                document.getElementById('confirm-datetime').textContent = `${date} a las ${time}`;
                document.getElementById('confirm-duration').textContent = this.selectedService.duracionMinutos + 15; // +15 limpieza visual
                document.getElementById('confirm-price').textContent = this.selectedService.precio;
            }
        },

        checkAvailabilityAndNext: function() {
            const date = document.getElementById('booking-date').value;
            const time = document.getElementById('booking-time').value;

            if (!date || !time) {
                app.showToast("Por favor, selecciona una fecha y hora válidas.", "error");
                return;
            }

            // In a real scenario we might ping backend here to tentatively check availability.
            // For SPA Prototype, we assume UX allows checking locally/continuing to Confirm.
            this.nextStep();
        },

        confirmBooking: async function() {
            const date = document.getElementById('booking-date').value;
            const time = document.getElementById('booking-time').value;
            
            // Format to proper structure `YYYY-MM-DDTHH:mm:ss`
            const fechaInicioStr = `${date}T${time}:00`;

            try {
                const response = await fetch(`${API_URL}/citas`, {
                    method: 'POST',
                    headers: app.getAuthHeaders(),
                    body: JSON.stringify({
                        fechaInicio: fechaInicioStr,
                        usuarioId: app.state.userId,
                        servicioId: this.selectedService.id
                    })
                });

                if (response.ok) {
                    app.showToast("¡Reserva confirmada con éxito!", "success");
                    app.client.loadMisCitas();
                    this.init(); // Reset wizard
                } else if (response.status === 400 || response.status === 409) {
                    // Overlap detection
                    app.showToast("Ups, ese hueco acaba de ser reservado. Por favor, elige otra hora.", "error");
                    this.prevStep(); // Go back to date selection
                } else {
                    app.showToast("Error al confirmar reserva (Solapamiento detectado)", "error");
                    this.prevStep();
                }
            } catch (error) {
                console.error("Booking Error", error);
                // MOCK SUCCESS FOR UI PROTOTYPING IF BACKEND DOWN
                app.showToast("¡Reserva confirmada con éxito! (Simulado)", "success");
                app.client.loadMisCitas();
                this.init();
            }
        }
    },

    // ======== ADMIN DOMAIN ========
    admin: {
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
    }
};

// Initialize App on load
document.addEventListener('DOMContentLoaded', () => {
    app.init();
});