package com.cine.entradas.config;

import com.cine.entradas.model.*;
import com.cine.entradas.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * CARGADOR DE DATOS (SEEDER)
 * --------------------------
 * Implementa CommandLineRunner: Se ejecuta justo después de que la aplicación arranca.
 * Sirve para rellenar la base de datos con información de prueba.
 */
@Component
@RequiredArgsConstructor
public class CineDataLoader implements CommandLineRunner {

    // Inyectamos todos los repositorios necesarios para guardar datos
    private final DirectorRepository directorRepo;
    private final ActorRepository actorRepo;
    private final PeliculaRepository peliculaRepo;
    private final SalaRepository salaRepo;
    private final FuncionRepository funcionRepo;
    private final UsuarioRepository usuarioRepo;

    private final PasswordEncoder passwordEncoder; // Para encriptar las contraseñas de los usuarios de prueba

    @Override
    public void run(String... args) throws Exception {
        // Evitar duplicados: Si ya hay películas, asumimos que los datos están cargados y no hacemos nada.
        if (peliculaRepo.count() > 0) return;

        System.out.println("--- INICIANDO CARGA DE DATOS REALISTAS DEL CINE ---");

        // 1. Creamos Directores (Padres de las películas)
        Director nolan = directorRepo.save(new Director(null, "Christopher Nolan"));
        Director tarantino = directorRepo.save(new Director(null, "Quentin Tarantino"));
        Director scorsese = directorRepo.save(new Director(null, "Martin Scorsese"));
        Director coppola = directorRepo.save(new Director(null, "Francis Ford Coppola"));
        Director bong = directorRepo.save(new Director(null, "Bong Joon-ho"));

        // 2. Creamos Actores
        Actor dicaprio = actorRepo.save(new Actor(null, "Leonardo DiCaprio"));
        Actor murphy = actorRepo.save(new Actor(null, "Cillian Murphy"));
        Actor bale = actorRepo.save(new Actor(null, "Christian Bale"));
        Actor ledger = actorRepo.save(new Actor(null, "Heath Ledger"));
        // ... (resto de actores)
        Actor travolta = actorRepo.save(new Actor(null, "John Travolta"));
        Actor jackson = actorRepo.save(new Actor(null, "Samuel L. Jackson"));
        Actor thurman = actorRepo.save(new Actor(null, "Uma Thurman"));
        Actor deniro = actorRepo.save(new Actor(null, "Robert De Niro"));
        Actor pacino = actorRepo.save(new Actor(null, "Al Pacino"));
        Actor brando = actorRepo.save(new Actor(null, "Marlon Brando"));
        Actor song = actorRepo.save(new Actor(null, "Song Kang-ho"));

        // 3. Creamos Películas (Vinculando directores y listas de actores)
        Pelicula inception = peliculaRepo.save(new Pelicula(null, "Inception", 148, 12, nolan, Arrays.asList(dicaprio, murphy)));
        Pelicula darkKnight = peliculaRepo.save(new Pelicula(null, "The Dark Knight", 152, 16, nolan, Arrays.asList(bale, ledger, murphy)));
        Pelicula pulpFiction = peliculaRepo.save(new Pelicula(null, "Pulp Fiction", 154, 18, tarantino, Arrays.asList(travolta, jackson, thurman)));
        Pelicula godfather = peliculaRepo.save(new Pelicula(null, "The Godfather", 175, 18, coppola, Arrays.asList(brando, pacino)));
        Pelicula parasite = peliculaRepo.save(new Pelicula(null, "Parasite", 132, 16, bong, Arrays.asList(song)));

        // 4. Creamos Salas (Infraestructura)
        Sala salaImax = salaRepo.save(new Sala(null, "Sala 1 - IMAX Laser", 300));
        Sala salaDolby = salaRepo.save(new Sala(null, "Sala 2 - Dolby Atmos", 200));
        Sala salaVip = salaRepo.save(new Sala(null, "Sala 3 - VIP Experience", 50));
        Sala salaStd1 = salaRepo.save(new Sala(null, "Sala 4 - Standard", 150));
        Sala salaStd2 = salaRepo.save(new Sala(null, "Sala 5 - Family", 100));

        // 5. Creamos Funciones (La cartelera: Quién, Dónde, Cuándo y Cuánto)
        LocalDateTime hoy = LocalDateTime.now();
        LocalDateTime manana = hoy.plusDays(1);
        LocalDateTime pasado = hoy.plusDays(2);

        // Ejemplo: Mañana a las 18:00, Inception en Sala IMAX por 12.50
        funcionRepo.save(new Funcion(null, manana.withHour(18).withMinute(0), 12.50, inception, salaImax));
        funcionRepo.save(new Funcion(null, manana.withHour(21).withMinute(30), 12.50, darkKnight, salaImax));
        // ... (resto de funciones)
        funcionRepo.save(new Funcion(null, manana.withHour(19).withMinute(0), 10.00, pulpFiction, salaDolby));
        funcionRepo.save(new Funcion(null, manana.withHour(22).withMinute(0), 10.00, godfather, salaDolby));
        funcionRepo.save(new Funcion(null, pasado.withHour(20).withMinute(0), 18.00, parasite, salaVip));
        funcionRepo.save(new Funcion(null, pasado.withHour(23).withMinute(0), 18.00, inception, salaVip));
        funcionRepo.save(new Funcion(null, manana.withHour(17).withMinute(30), 8.50, parasite, salaStd1));
        funcionRepo.save(new Funcion(null, pasado.withHour(18).withMinute(0), 8.50, pulpFiction, salaStd2));

        // 6. Creamos Usuarios de prueba
        // IMPORTANTE: Aquí se usa passwordEncoder.encode() para guardar la contraseña hasheada, no en texto plano.
        Usuario pepe = usuarioRepo.save(new Usuario(null, "Pepe", "pepe@cine.com", passwordEncoder.encode("1234")));
        Usuario admin = usuarioRepo.save(new Usuario(null, "Admin", "admin@cine.com", passwordEncoder.encode("admin")));

        System.out.println("--- 🍿 SISTEMA DE CINE REALISTA CARGADO CORRECTAMENTE 🍿 ---");
    }
}