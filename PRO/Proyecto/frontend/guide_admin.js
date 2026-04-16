app.startAdminGuide = function() {
    if (app.adminGuideActive) return;
    app.adminGuideActive = true;
    app.adminGuideStep = 1;

    const styleId = 'guide-admin-animations-style';
    if (!document.getElementById(styleId)) {
        const style = document.createElement('style');
        style.id = styleId;
        style.textContent = `
            @keyframes guide-pulse-glow {
                0% { box-shadow: 0 0 0 9999px rgba(35, 30, 25, 0.7), 0 0 15px rgba(212, 175, 55, 0.2) inset, 0 0 10px rgba(212, 175, 55, 0.3); }
                50% { box-shadow: 0 0 0 9999px rgba(35, 30, 25, 0.8), 0 0 25px rgba(212, 175, 55, 0.45) inset, 0 0 20px rgba(212, 175, 55, 0.6); }
                100% { box-shadow: 0 0 0 9999px rgba(35, 30, 25, 0.7), 0 0 15px rgba(212, 175, 55, 0.2) inset, 0 0 10px rgba(212, 175, 55, 0.3); }
            }
            @keyframes guide-float {
                0% { transform: translateY(0px); }
                50% { transform: translateY(-6px); }
                100% { transform: translateY(0px); }
            }
        `;
        document.head.appendChild(style);
    }

    const highlight = document.createElement('div');
    highlight.id = 'guide-admin-highlight';
    highlight.style.cssText = 'position:fixed;z-index:9990;background:transparent;border:3px solid var(--color-gold);border-radius:14px;pointer-events:none;transition:top 0.4s cubic-bezier(0.25, 1, 0.5, 1), left 0.4s cubic-bezier(0.25, 1, 0.5, 1), width 0.4s cubic-bezier(0.25, 1, 0.5, 1), height 0.4s cubic-bezier(0.25, 1, 0.5, 1);animation:guide-pulse-glow 2.5s infinite ease-in-out;';

    const tooltip = document.createElement('div');
    tooltip.id = 'guide-admin-tooltip';
    tooltip.style.cssText = 'position:fixed;z-index:9992;background:#fff;padding:30px;border-radius:14px;border:1px solid rgba(212, 175, 55, 0.3);width:90%;max-width:350px;box-shadow:0 20px 50px rgba(0,0,0,0.15);transition:top 0.4s cubic-bezier(0.25, 1, 0.5, 1), left 0.4s cubic-bezier(0.25, 1, 0.5, 1);animation:guide-float 3s infinite ease-in-out;display:flex;flex-direction:column;gap:5px;';

    document.body.appendChild(highlight);
    document.body.appendChild(tooltip);

    let currentTargetEl = null;

    app.advanceAdminGuideStep = function(step) {
        if (!app.adminGuideActive) return;
        app.adminGuideStep = step;
        updateAdminGuideUI();
    };

    const blockClicksFn = function(e) {
        if (!app.adminGuideActive) return;
        if (tooltip.contains(e.target)) return;
        if (currentTargetEl && currentTargetEl.contains(e.target)) return;
        
        e.stopPropagation();
        e.preventDefault();
        
        highlight.style.borderColor = 'red';
        setTimeout(() => highlight.style.borderColor = 'var(--color-gold)', 200);
    };

    document.addEventListener('click', blockClicksFn, true);

    const updateAdminGuideUI = () => {
        let text = '';
        
        if (app.adminGuideStep === 1) {
            currentTargetEl = document.getElementById('tab-btn-catalog');
            text = '<h3 style="margin:0;color:var(--color-dark);font-family:var(--font-heading);font-size:1.4rem;"><i class="fas fa-crown" style="color:var(--color-gold);margin-right:8px;"></i>Paso 1: El Catálogo</h3><p style="margin:0 0 10px 0;color:var(--color-light-text);line-height:1.6;font-size:0.95rem;">Empieza explorando tus servicios.</p><strong style="color:var(--color-gold); font-size:1.1rem;"><i class="fas fa-mouse-pointer" style="margin-right:5px; animation: guide-float 2s infinite;"></i>Pulsa en "Catálogo de Servicios"</strong>';
        } else if (app.adminGuideStep === 2) {
            // El botón de + Nuevo Servicio
            currentTargetEl = document.querySelector('#admin-tab-catalog .btn-primary');
            text = '<h3 style="margin:0;color:var(--color-dark);font-family:var(--font-heading);font-size:1.4rem;"><i class="fas fa-plus" style="color:var(--color-gold);margin-right:8px;"></i>Paso 2: Añadir</h3><p style="margin:0 0 10px 0;color:var(--color-light-text);line-height:1.6;font-size:0.95rem;">Abre el editor para ampliar tu catálogo.</p><strong style="color:var(--color-gold); font-size:1.1rem;"><i class="fas fa-mouse-pointer" style="margin-right:5px; animation: guide-float 2s infinite;"></i>Pulsa "Nuevo Servicio"</strong>';
        } else if (app.adminGuideStep === 3) {
            currentTargetEl = document.getElementById('btn-cancel-edit');
            text = '<h3 style="margin:0;color:var(--color-dark);font-family:var(--font-heading);font-size:1.4rem;"><i class="fas fa-edit" style="color:var(--color-gold);margin-right:8px;"></i>Paso 3: El Formulario</h3><p style="margin:0 0 10px 0;color:var(--color-light-text);line-height:1.6;font-size:0.95rem;">Aquí detallas nombre, precio y tiempos de tus tratamientos.</p><strong style="color:var(--color-gold); font-size:1.1rem;"><i class="fas fa-mouse-pointer" style="margin-right:5px; animation: guide-float 2s infinite;"></i>Para continuar, pulsa "Ocultar"</strong>';
        } else if (app.adminGuideStep === 4) {
            currentTargetEl = document.querySelector('#admin-services-list .btn-outline');
            text = '<h3 style="margin:0;color:var(--color-dark);font-family:var(--font-heading);font-size:1.4rem;"><i class="fas fa-pencil-alt" style="color:var(--color-gold);margin-right:8px;"></i>Paso 4: Editar</h3><p style="margin:0 0 10px 0;color:var(--color-light-text);line-height:1.6;font-size:0.95rem;">También puedes modificar los servicios ya existentes.</p><strong style="color:var(--color-gold); font-size:1.1rem;"><i class="fas fa-mouse-pointer" style="margin-right:5px; animation: guide-float 2s infinite;"></i>Busca abajo y pulsa en "Editar" sobre algún servicio.</strong>';
        } else if (app.adminGuideStep === 5) {
            currentTargetEl = document.getElementById('btn-cancel-edit');
            text = '<h3 style="margin:0;color:var(--color-dark);font-family:var(--font-heading);font-size:1.4rem;"><i class="fas fa-save" style="color:var(--color-gold);margin-right:8px;"></i>Paso 5: Guardar</h3><p style="margin:0 0 10px 0;color:var(--color-light-text);line-height:1.6;font-size:0.95rem;">El formulario recupera sus datos. Aquí cambiarías el precio y presionarías Guardar.</p><strong style="color:var(--color-gold); font-size:1.1rem;"><i class="fas fa-mouse-pointer" style="margin-right:5px; animation: guide-float 2s infinite;"></i>Para continuar, pulsa "Ocultar".</strong>';
        } else if (app.adminGuideStep === 6) {
            currentTargetEl = document.getElementById('tab-btn-calendar');
            text = '<h3 style="margin:0;color:var(--color-dark);font-family:var(--font-heading);font-size:1.4rem;"><i class="fas fa-calendar-alt" style="color:var(--color-gold);margin-right:8px;"></i>Paso 6: Las Reservas</h3><p style="margin:0 0 10px 0;color:var(--color-light-text);line-height:1.6;font-size:0.95rem;">Pasemos a la gestión del día a día.</p><strong style="color:var(--color-gold); font-size:1.1rem;"><i class="fas fa-mouse-pointer" style="margin-right:5px; animation: guide-float 2s infinite;"></i>Pulsa en "Calendario de Citas"</strong>';
        } else if (app.adminGuideStep === 7) {
            // El botón de Añadir cita en la columna derecha
            currentTargetEl = document.querySelector('#admin-tab-calendar .btn-primary');
            text = '<h3 style="margin:0;color:var(--color-dark);font-family:var(--font-heading);font-size:1.4rem;"><i class="fas fa-calendar-plus" style="color:var(--color-gold);margin-right:8px;"></i>Paso 7: Forzar Citas</h3><p style="margin:0 0 10px 0;color:var(--color-light-text);line-height:1.6;font-size:0.95rem;">Puedes crear citas para clientes o bloquear huecos como administrador.</p><strong style="color:var(--color-gold); font-size:1.1rem;"><i class="fas fa-mouse-pointer" style="margin-right:5px; animation: guide-float 2s infinite;"></i>Pulsa en "Añadir"</strong>';
        } else if (app.adminGuideStep === 8) {
            currentTargetEl = document.getElementById('admin-cita-modal').querySelector('.modal-content');
            text = '<h3 style="margin:0;color:var(--color-dark);font-family:var(--font-heading);font-size:1.4rem;"><i class="fas fa-flag-checkered" style="color:var(--color-gold);margin-right:8px;"></i>¡Completado!</h3><p style="margin:0 0 10px 0;color:var(--color-light-text);line-height:1.6;font-size:0.95rem;">Cerrando este formulario, completas la creación. ¡Estás listo para gestionar TuTurno!</p>';
        }

        if (!currentTargetEl) currentTargetEl = document.body;

        tooltip.innerHTML = text;

        const controls = document.createElement('div');
        controls.style.cssText = 'display:flex;justify-content:space-between;align-items:center;margin-top:15px;border-top:1px solid rgba(0,0,0,0.05);padding-top:10px;';

        const btnSkip = document.createElement('button');
        btnSkip.className = 'btn btn-outline btn-small';
        
        if (app.adminGuideStep === 8) {
            btnSkip.textContent = '¡Empezar a Trabajar!';
            btnSkip.style.cssText = 'padding:8px 30px;border-radius:25px;font-weight:600;background:var(--color-gold);border:none;box-shadow:0 4px 15px rgba(212,175,55,0.4);color:#fff;width:100%;';
        } else {
            btnSkip.textContent = 'Abandonar Guía';
            btnSkip.style.cssText = 'border:none;background:transparent;color:#adb5bd;font-weight:500;padding:5px;width:100%;text-align:center;';
        }
        
        btnSkip.onclick = () => finishAdminGuide();

        controls.appendChild(btnSkip);
        tooltip.appendChild(controls);

        recalcPositions();
        setTimeout(recalcPositions, 100); 
    };

    const recalcPositions = () => {
        if (!app.adminGuideActive || !currentTargetEl) return;
        
        const rect = currentTargetEl.getBoundingClientRect();
        
        highlight.style.top = (rect.top - 12) + 'px';
        highlight.style.left = (rect.left - 12) + 'px';
        highlight.style.width = (rect.width + 24) + 'px';
        highlight.style.height = (rect.height + 24) + 'px';

        let spaceRight = window.innerWidth - rect.right;
        let spaceLeft = rect.left;

        let tTop, tLeft;
        
        if (spaceRight > 380 && window.innerWidth > 768) {
            tTop = Math.max(20, rect.top + 20);
            tLeft = rect.right + 30;
        } else if (spaceLeft > 380 && window.innerWidth > 768) {
            tTop = Math.max(20, rect.top + 20);
            tLeft = rect.left - 360;
        } else {
            tTop = Math.max(20, window.innerHeight / 2 - 120);
            tLeft = Math.max(20, window.innerWidth / 2 - 175);
        }

        let tooltipH = tooltip.offsetHeight || 200;
        let tooltipW = tooltip.offsetWidth || 350;

        tTop = Math.min(tTop, window.innerHeight - tooltipH - 20);
        tLeft = Math.min(tLeft, window.innerWidth - tooltipW - 20);

        tTop = Math.max(20, tTop);
        tLeft = Math.max(20, tLeft);

        tooltip.style.top = tTop + 'px';
        tooltip.style.left = tLeft + 'px';
    };

    const finishAdminGuide = async () => {
        document.removeEventListener('click', blockClicksFn, true);
        window.removeEventListener('resize', recalcPositions);
        window.removeEventListener('scroll', recalcPositions, true);
        
        if (document.body.contains(highlight)) document.body.removeChild(highlight);
        if (document.body.contains(tooltip)) document.body.removeChild(tooltip);
        
        let styleEl = document.getElementById(styleId);
        if (styleEl && document.head.contains(styleEl)) document.head.removeChild(styleEl);

        app.adminGuideActive = false;
        app.state.hasSeenGuide = true;
        localStorage.setItem(`auth_hasSeenGuide_${app.state.userEmail}`, "true");

        // Cerrar modal de cita si quedó abierto por el paso 6
        document.getElementById('admin-cita-modal').classList.remove('active');

        try {
            await fetch(`${API_URL}/usuarios/onboarding`, {
                method: 'PATCH',
                headers: app.getAuthHeaders()
            });
        } catch (e) {}
    };

    window.scrollTo({ top: 0, behavior: 'smooth' });
    
    app._adminGuideUpdateInterval = setInterval(() => {
        if (!app.adminGuideActive) {
            clearInterval(app._adminGuideUpdateInterval);
            return;
        }
        recalcPositions();
    }, 50);

    setTimeout(() => {
        updateAdminGuideUI();
        window.addEventListener('resize', recalcPositions);
        window.addEventListener('scroll', recalcPositions, true);
    }, 300);
};
