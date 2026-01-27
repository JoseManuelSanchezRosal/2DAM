package com.cine.entradas.config;

import com.cine.entradas.model.*;
import com.cine.entradas.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CineDataLoader implements CommandLineRunner {

    private final DirectorRepository directorRepo;
    private final ActorRepository actorRepo;
    private final PeliculaRepository peliculaRepo;
    private final SalaRepository salaRepo;
    private final FuncionRepository funcionRepo;
    private final UsuarioRepository usuarioRepo;

    @Override
    public void run(String... args) throws Exception {
        // Evitar duplicados si la BD ya tiene datos
        if (peliculaRepo.count() > 0) return;

        System.out.println("--- INICIANDO CARGA DE DATOS REALISTAS DEL CINE ---");

        // ==========================================
        // 1. DIRECTORES (Leyendas del cine)
        // ==========================================
        Director nolan = directorRepo.save(new Director(null, "Christopher Nolan"));
        Director tarantino = directorRepo.save(new Director(null, "Quentin Tarantino"));
        Director scorsese = directorRepo.save(new Director(null, "Martin Scorsese"));
        Director coppola = directorRepo.save(new Director(null, "Francis Ford Coppola"));
        Director bong = directorRepo.save(new Director(null, "Bong Joon-ho"));

        // ==========================================
        // 2. ACTORES (Reparto estelar)
        // ==========================================
        // Nolanverse
        Actor dicaprio = actorRepo.save(new Actor(null, "Leonardo DiCaprio"));
        Actor murphy = actorRepo.save(new Actor(null, "Cillian Murphy"));
        Actor bale = actorRepo.save(new Actor(null, "Christian Bale"));
        Actor ledger = actorRepo.save(new Actor(null, "Heath Ledger"));

        // Tarantino's
        Actor travolta = actorRepo.save(new Actor(null, "John Travolta"));
        Actor jackson = actorRepo.save(new Actor(null, "Samuel L. Jackson"));
        Actor thurman = actorRepo.save(new Actor(null, "Uma Thurman"));

        // Classics & Others
        Actor deniro = actorRepo.save(new Actor(null, "Robert De Niro"));
        Actor pacino = actorRepo.save(new Actor(null, "Al Pacino"));
        Actor brando = actorRepo.save(new Actor(null, "Marlon Brando"));
        Actor song = actorRepo.save(new Actor(null, "Song Kang-ho")); // Parasite

        // ==========================================
        // 3. PELÍCULAS (5 Obras Maestras)
        // ==========================================
        Pelicula inception = peliculaRepo.save(new Pelicula(null, "Inception", 148, 12, nolan, Arrays.asList(dicaprio, murphy)));
        Pelicula darkKnight = peliculaRepo.save(new Pelicula(null, "The Dark Knight", 152, 16, nolan, Arrays.asList(bale, ledger, murphy)));
        Pelicula pulpFiction = peliculaRepo.save(new Pelicula(null, "Pulp Fiction", 154, 18, tarantino, Arrays.asList(travolta, jackson, thurman)));
        Pelicula godfather = peliculaRepo.save(new Pelicula(null, "The Godfather", 175, 18, coppola, Arrays.asList(brando, pacino)));
        Pelicula parasite = peliculaRepo.save(new Pelicula(null, "Parasite", 132, 16, bong, Arrays.asList(song)));

        // ==========================================
        // 4. SALAS (5 Ambientes distintos)
        // ==========================================
        Sala salaImax = salaRepo.save(new Sala(null, "Sala 1 - IMAX Laser", 300));
        Sala salaDolby = salaRepo.save(new Sala(null, "Sala 2 - Dolby Atmos", 200));
        Sala salaVip = salaRepo.save(new Sala(null, "Sala 3 - VIP Experience", 50));
        Sala salaStd1 = salaRepo.save(new Sala(null, "Sala 4 - Standard", 150));
        Sala salaStd2 = salaRepo.save(new Sala(null, "Sala 5 - Family", 100));

        // ==========================================
        // 5. FUNCIONES (Programación Realista)
        // ==========================================
        // Definimos una fecha base: Mañana
        LocalDateTime hoy = LocalDateTime.now();
        LocalDateTime manana = hoy.plusDays(1);
        LocalDateTime pasado = hoy.plusDays(2);

        // --- SALA IMAX (Grandes producciones) ---
        // Mañana: Inception (18:00) y Dark Knight (21:30)
        funcionRepo.save(new Funcion(null, manana.withHour(18).withMinute(0), 12.50, inception, salaImax));
        funcionRepo.save(new Funcion(null, manana.withHour(21).withMinute(30), 12.50, darkKnight, salaImax));

        // --- SALA DOLBY (Cine de culto con buen sonido) ---
        // Mañana: Pulp Fiction (19:00) y The Godfather (22:00)
        funcionRepo.save(new Funcion(null, manana.withHour(19).withMinute(0), 10.00, pulpFiction, salaDolby));
        funcionRepo.save(new Funcion(null, manana.withHour(22).withMinute(0), 10.00, godfather, salaDolby));

        // --- SALA VIP (Exclusiva) ---
        // Pasado mañana: Parasite (20:00) - Precio más alto
        funcionRepo.save(new Funcion(null, pasado.withHour(20).withMinute(0), 18.00, parasite, salaVip));
        // Pasado mañana: Inception (23:00) - Sesión golfa
        funcionRepo.save(new Funcion(null, pasado.withHour(23).withMinute(0), 18.00, inception, salaVip));

        // --- SALA STANDARD (Rotación) ---
        // Mañana: Parasite (17:30)
        funcionRepo.save(new Funcion(null, manana.withHour(17).withMinute(30), 8.50, parasite, salaStd1));
        // Pasado: Pulp Fiction (18:00)
        funcionRepo.save(new Funcion(null, pasado.withHour(18).withMinute(0), 8.50, pulpFiction, salaStd2));

        // ==========================================
        // 6. USUARIOS (Clientes de prueba)
        // ==========================================
        Usuario pepe = usuarioRepo.save(new Usuario(null, "Pepe Cliente", "pepe@cine.com"));
        Usuario maria = usuarioRepo.save(new Usuario(null, "Maria Cinefila", "maria@cine.com"));
        Usuario admin = usuarioRepo.save(new Usuario(null, "Admin Sistema", "admin@cine.com"));

        System.out.println("--- 🍿 SISTEMA DE CINE REALISTA CARGADO CORRECTAMENTE 🍿 ---");
        System.out.println("Usuarios Test: ID " + pepe.getId() + " (Pepe), ID " + maria.getId() + " (Maria)");
    }
}