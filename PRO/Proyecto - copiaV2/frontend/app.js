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
        // Inicializamos el wizard para TODO el mundo (público y privado)
        if (this.bookingWizard) this.bookingWizard.init();
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
        const viewEl = document.getElementById(`view-${viewId}`);
        if(viewEl) viewEl.classList.add('active-view');
        
        // Update Nav 
        const btnId = `btn-nav-${viewId}`;
        const btnEl = document.getElementById(btnId);
        if(btnEl) btnEl.classList.add('active');

        // Session controls
        if (app.state.token) {
            const adminBtn = document.getElementById('btn-nav-dashboard');
            if (app.state.userRole === 'admin') {
                if(adminBtn) adminBtn.classList.remove('hidden');
            } else {
                if(adminBtn) adminBtn.classList.add('hidden');
            }
            const logoutBtn = document.getElementById('btn-logout');
            if(logoutBtn) logoutBtn.classList.remove('hidden');
        } else {
            const adminBtn = document.getElementById('btn-nav-dashboard');
            if(adminBtn) adminBtn.classList.add('hidden');
            const logoutBtn = document.getElementById('btn-logout');
            if(logoutBtn) logoutBtn.classList.add('hidden');
            
            const clientZoneBtn = document.getElementById('btn-nav-client-zone');
            if(clientZoneBtn) clientZoneBtn.classList.add('highlight');
        }

        if (viewId === 'client-zone') {
            if (app.bookingWizard) app.bookingWizard.init();
            
            const clientGrid = document.querySelector('.client-grid');
            
            if (app.state.token && app.state.userRole !== 'admin') {
                document.getElementById('client-logged-in-header').style.display = 'block';
                const loggedOutHeader = document.getElementById('client-logged-out-header');
                if (loggedOutHeader) loggedOutHeader.style.display = 'none';
                document.getElementById('my-appointments-card').style.display = 'block';
                if(clientGrid) clientGrid.classList.add('logged-in-grid');
                if(app.client) app.client.initAuthClient();
            } else {
                document.getElementById('client-logged-in-header').style.display = 'none';
                const loggedOutHeader = document.getElementById('client-logged-out-header');
                if (loggedOutHeader) loggedOutHeader.style.display = 'block';
                document.getElementById('my-appointments-card').style.display = 'none';
                if(clientGrid) clientGrid.classList.remove('logged-in-grid');
            }
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
            this.navigateTo('client-zone');
        }
    },

    // ======== UTILS ========
    getAuthHeaders: function() {
        return {
            "Authorization": `Bearer ${this.state.token}`,
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
    }
};

// Initialize App on load
document.addEventListener('DOMContentLoaded', () => {
    app.init();
});