// script.js
document.addEventListener('DOMContentLoaded', () => {
    // 1. SPA Navigation Logic
    const navLinks = document.querySelectorAll('.nav-to');
    const views = document.querySelectorAll('.view');

    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            const href = link.getAttribute('href');
            if (href.startsWith('#view-')) {
                e.preventDefault();
                const targetId = href.substring(1);

                // Highlight active nav tab if it's in the main nav
                if (link.parentElement && link.parentElement.classList.contains('nav-links')) {
                    document.querySelectorAll('.nav-links a').forEach(l => l.classList.remove('active'));
                    link.classList.add('active');
                } else {
                    // Sync up main nav if clicked elsewhere (e.g. from CTA button)
                    document.querySelectorAll('.nav-links a').forEach(l => {
                        if (l.getAttribute('href') === href) {
                            l.classList.add('active');
                        } else {
                            l.classList.remove('active');
                        }
                    });
                }

                // Switch views
                views.forEach(view => {
                    view.classList.remove('active');
                });
                const targetView = document.getElementById(targetId);
                if (targetView) {
                    targetView.classList.add('active');
                    window.scrollTo(0, 0); // Scroll to top when changing views
                }
            }
        });
    });

    // 2. Calendar Logic (Generates mock dates for demonstration)
    const calendarDays = document.getElementById('calendar-days');
    const monthYear = document.getElementById('month-year');

    // Config values
    const daysInMonth = 31; // E.g., October
    const startDayOffset = 3; // Starts on Thursday

    monthYear.innerText = "Octubre 2026";

    // Create empty offset days
    for (let i = 0; i < startDayOffset; i++) {
        const emptyDiv = document.createElement('div');
        emptyDiv.className = 'cal-day empty';
        calendarDays.appendChild(emptyDiv);
    }

    // Generate accurate days
    for (let i = 1; i <= daysInMonth; i++) {
        const dayDiv = document.createElement('div');
        dayDiv.className = 'cal-day';
        dayDiv.innerText = i;

        // Mocking weekend disability or already booked
        if (i % 7 === 4 || i === 15 || i === 22) { // Logic to mark Sundays
            dayDiv.classList.add('disabled');
        } else if (i === 12) {
            dayDiv.classList.add('active'); // Default selected day
        }

        // Add interaction to clickable days
        dayDiv.addEventListener('click', () => {
            if (!dayDiv.classList.contains('disabled') && !dayDiv.classList.contains('empty')) {
                document.querySelectorAll('.cal-day').forEach(d => d.classList.remove('active'));
                dayDiv.classList.add('active');
            }
        });

        calendarDays.appendChild(dayDiv);
    }

    // 3. Time slots toggling
    const timeSlots = document.querySelectorAll('.time-slot');
    timeSlots.forEach(slot => {
        slot.addEventListener('click', () => {
            if (!slot.classList.contains('disabled')) {
                timeSlots.forEach(s => s.classList.remove('active'));
                slot.classList.add('active');
            }
        });
    });

    // 4. Confirm Button Interaction (Micro-animation simulation)
    const confirmBtn = document.querySelector('.btn-confirmar');
    if (confirmBtn) {
        confirmBtn.addEventListener('click', (e) => {
            if (confirmBtn.hasAttribute('disabled')) return;

            const originalContent = confirmBtn.innerHTML;
            const originalBg = confirmBtn.style.background;

            // Success state
            confirmBtn.innerHTML = '¡Cita Confirmada! &nbsp;<i class="las la-check-circle"></i>';
            confirmBtn.style.background = 'var(--matte-black)';
            confirmBtn.style.color = 'var(--white)';
            confirmBtn.style.borderColor = 'var(--matte-black)';
            confirmBtn.style.boxShadow = '0 10px 25px rgba(26, 26, 26, 0.3)';

            // Timeout to reset state and show alert
            setTimeout(() => {
                alert('¡Tu cita ha sido confirmada con éxito! Revisa tu Área de Clientes para más detalles.');

                // Revert state
                confirmBtn.innerHTML = originalContent;
                confirmBtn.style.background = originalBg;
                confirmBtn.style.color = '';
                confirmBtn.style.borderColor = '';
                confirmBtn.style.boxShadow = '';
            }, 2000);
        });
    }

    // 5. Login / Registro Tabs Logic
    const tabLogin = document.getElementById('tab-login');
    const tabRegistro = document.getElementById('tab-registro');
    const formLogin = document.getElementById('form-login');
    const formRegistro = document.getElementById('form-registro');

    if (tabLogin && tabRegistro && formLogin && formRegistro) {
        tabLogin.addEventListener('click', () => {
            // Activar tab login
            tabLogin.classList.add('active');
            tabLogin.style.borderBottomColor = 'var(--gold-metallic)';
            tabLogin.style.color = 'var(--matte-black)';
            tabLogin.style.fontWeight = '500';

            // Desactivar tab registro
            tabRegistro.classList.remove('active');
            tabRegistro.style.borderBottomColor = 'transparent';
            tabRegistro.style.color = 'var(--gray-text)';
            tabRegistro.style.fontWeight = '400';

            // Mostrar form login
            formLogin.style.display = 'flex';
            formRegistro.style.display = 'none';
        });

        tabRegistro.addEventListener('click', () => {
            // Activar tab registro
            tabRegistro.classList.add('active');
            tabRegistro.style.borderBottomColor = 'var(--gold-metallic)';
            tabRegistro.style.color = 'var(--matte-black)';
            tabRegistro.style.fontWeight = '500';

            // Desactivar tab login
            tabLogin.classList.remove('active');
            tabLogin.style.borderBottomColor = 'transparent';
            tabLogin.style.color = 'var(--gray-text)';
            tabLogin.style.fontWeight = '400';

            // Mostrar form registro
            formRegistro.style.display = 'flex';
            formLogin.style.display = 'none';
        });
    }

    // 6. Simulación de Interacciones (Para propósitos de demostración)

    // Simulación de Registro
    const btnRegisterSubmit = document.getElementById('btn-register-submit');
    if (btnRegisterSubmit) {
        btnRegisterSubmit.addEventListener('click', () => {
            alert('Simulación: Cuenta de cliente creada exitosamente. Ahora puedes iniciar sesión con tus datos.');
            // Switch to login tab automatically
            if (tabLogin) tabLogin.click();
        });
    }

    // Simulación de Login y Desbloqueo de Calendario
    const btnLoginSubmit = document.getElementById('btn-login-submit');
    const btnFinalConfirmar = document.getElementById('btn-final-confirmar');
    const lockMessage = document.getElementById('lock-message');

    if (btnLoginSubmit) {
        btnLoginSubmit.addEventListener('click', () => {
            alert('Simulación: Has iniciado sesión correctamente. Ahora puedes seleccionar tu horario y confirmar la reserva.');

            // Desbloquear slots de tiempo
            const disabledSlots = document.querySelectorAll('.time-slots .time-slot.disabled');
            disabledSlots.forEach(slot => {
                // Remove visual disable locks (but keep structure if they shouldn't be clickable for other reasons)
                // In this case, we'll let them stay visually disabled if they represent an occupied slot, 
                // but we will unlock the non-occupied ones that were disabled by default.
            });

            // Unlock all time slots that were universally disabled to prevent interaction
            document.querySelectorAll('.time-slots .time-slot').forEach(slot => {
                slot.removeAttribute('disabled');
                slot.style.cursor = 'pointer';
                slot.style.opacity = '1';

                // If it had the specific "disabled/occupied" style class, keep it looking occupied
                if (slot.classList.contains('disabled')) {
                    slot.style.cursor = 'not-allowed';
                    slot.style.opacity = '0.5';
                    slot.setAttribute('disabled', 'true'); // put it back because it's occupied
                }
            });

            // Ocultar mensaje de candado
            if (lockMessage) {
                lockMessage.style.display = 'none';
            }

            // Desbloquear botón final
            if (btnFinalConfirmar) {
                btnFinalConfirmar.removeAttribute('disabled');
                btnFinalConfirmar.style.cursor = 'pointer';
                btnFinalConfirmar.style.background = 'var(--gold-metallic)';
                btnFinalConfirmar.style.color = 'var(--white)';
            }
        });
    }

    // Attempting to select time slots while locked
    timeSlots.forEach(slot => {
        // We override the click to add an alert if the final button is still disabled (meaning not logged in yet in this simulation)
        slot.addEventListener('click', (e) => {
            if (btnFinalConfirmar && btnFinalConfirmar.hasAttribute('disabled')) {
                alert('Simulación: Debes iniciar sesión en la columna de la izquierda para poder seleccionar un horario.');
                // prevent the default logic of making it active
                e.stopImmediatePropagation();
                slot.classList.remove('active');
            }
        }, true); // Use capture to intercept before the logic defined earlier
    });

    // Simulación de interacción en Catálogo de Servicios
    const serviceCards = document.querySelectorAll('#view-servicios .card-marble');
    serviceCards.forEach(card => {
        card.style.cursor = 'pointer'; // Make them look clickable
        card.addEventListener('click', () => {
            alert('Simulación: En una aplicación funcional, al hacer clic aquí se abriría la pasarela de reserva preseleccionando este servicio específico (' + card.querySelector('h3').innerText + ').');
        });
    });
});
