package com.dam2.Practica1.service;

import com.dam2.Practica1.dto.PeliculaDTO.ImportarPeliculaDto;
import com.dam2.Practica1.dto.PeliculaDTO.PeliculaCreateUpdateDTO;
import com.dam2.Practica1.dto.PeliculaDTO.PeliculaDTO;
import com.dam2.Practica1.dto.CriticaDTO.CriticaDTO; // Asegúrate de tener este import
import com.dam2.Practica1.models.Critica; // Importante
import com.dam2.Practica1.models.Pelicula;
import com.dam2.Practica1.repository.CriticaRepository; // Importante
import com.dam2.Practica1.repository.PeliculaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
@Getter
public class PeliculaService {
    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private CriticaRepository criticaRepository; // Necesario para guardar las reviews importadas
    @Autowired
    private TareaAsync tareaAsync;
    @Autowired
    private TmdbService tmdbService;

    // Para sacar info de nuestro Executor de hilos @Async
    @Autowired
    @Qualifier("threadsJurado")
    private ThreadPoolTaskExecutor threadsJurado;

    // --- MAPEO A DTO ---
    private PeliculaDTO toDTO(Pelicula p){
        // Mapeamos las críticas
        List<CriticaDTO> criticasDTO = new ArrayList<>();
        if (p.getListaCriticas() != null) {
            criticasDTO = p.getListaCriticas().stream()
                    .map(c -> new CriticaDTO(
                            c.getId(),
                            c.getComentario(),
                            c.getNota(),
                            c.getFecha(),
                            c.getUsuario() != null ? c.getUsuario().getUserName() : "Anónimo"
                    ))
                    .toList();
        }

        return new PeliculaDTO(
                p.getId(),
                p.getTitulo(),
                p.getDuracion(),
                p.getFechaEstreno(),
                p.getSinopsis(),
                p.getValoracion(),
                p.getPosterPath(),
                p.getBackdropPath(),
                p.getTrailerKey(),
                criticasDTO
        );
    }

    //--------------CRUD DTO----------------------------------
    @Transactional(readOnly = true)
    public List<PeliculaDTO> listar() {
        return peliculaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PeliculaDTO buscarPorId(Long id){
        return peliculaRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    @Transactional
    public PeliculaDTO agregar(@RequestBody @Valid PeliculaCreateUpdateDTO peliculaDto) {
        Pelicula p = new Pelicula();
        p.setTitulo(peliculaDto.getTitulo());
        p.setDuracion(peliculaDto.getDuracion());
        p.setFechaEstreno(peliculaDto.getFechaEstreno());
        p.setSinopsis(peliculaDto.getSinopsis());
        p.setValoracion(peliculaDto.getValoracion());
        p.setPosterPath(peliculaDto.getPosterPath());
        p.setBackdropPath(peliculaDto.getBackdropPath());
        peliculaRepository.save(p);
        return toDTO(p);
    }

    @Transactional
    public PeliculaDTO actualizar(@PathVariable Long id,@RequestBody @Valid PeliculaCreateUpdateDTO peliculaDto) {
        Optional<Pelicula> optionalPelicula = peliculaRepository.findById(id);
        if (!optionalPelicula.isPresent()){
            throw new RuntimeException("Pelicula no encontrada");
        }
        Pelicula p = optionalPelicula.get();
        p.setTitulo(peliculaDto.getTitulo());
        p.setDuracion(peliculaDto.getDuracion());
        p.setFechaEstreno(peliculaDto.getFechaEstreno());
        p.setSinopsis(peliculaDto.getSinopsis());
        p.setValoracion(peliculaDto.getValoracion());
        p.setPosterPath(peliculaDto.getPosterPath());
        p.setBackdropPath(peliculaDto.getBackdropPath());
        peliculaRepository.save(p);

        return toDTO(p);
    }

    @Transactional
    public void eliminar(Long id) {
        peliculaRepository.deleteById(id);
    }

    // --- MÉTODOS ASÍNCRONOS Y DE IMPORTACIÓN --- (Se mantienen igual)
    // ... (tareaLentaSync, procesarPeliculas, procesarPeliculasAsync, reproducir, importarPeliculas, realizarVotaciones, rankingOrdenado) ...
    // Te dejo el resto de métodos aquí resumidos para no ocupar 500 líneas, pero ASEGÚRATE DE MANTENERLOS
    // Si copias y pegas, asegúrate de no borrar tus métodos de Tarea 1, 2, 3...

    // (Pega aquí tus métodos de TareaAsync, Votaciones, etc. si los borraste al copiar)
    // Para simplificar, aquí pongo SOLO EL QUE CAMBIA:

    /**
     * IMPORTACIÓN DESDE TMDB + CRÍTICAS REALES
     */
    @Transactional
    public String importarPeliculasDeTMDB() {
        int contador = 0;
        // Recorremos las páginas 1 a 5 de TMDB
        for (int i = 1; i <= 5; i++) {

            List<JsonNode> resultados = tmdbService.obtenerPeliculasPopulares(i);

            for (JsonNode nodo : resultados) {
                String titulo = nodo.path("title").asText();

                // Evitamos duplicados
                boolean existe = peliculaRepository.findAll().stream()
                        .anyMatch(p -> p.getTitulo().equalsIgnoreCase(titulo));

                if (!existe) {
                    Pelicula p = new Pelicula();
                    p.setTitulo(titulo);

                    String sinopsis = nodo.path("overview").asText();
                    p.setSinopsis(sinopsis.isEmpty() ? "Sin descripción disponible." : sinopsis);

                    p.setFechaEstreno(nodo.path("release_date").asText());
                    p.setPosterPath(nodo.path("poster_path").asText());
                    p.setBackdropPath(nodo.path("backdrop_path").asText());

                    double voto = nodo.path("vote_average").asDouble();
                    p.setValoracion((int) Math.round(voto));

                    // Trailer
                    int tmdbId = nodo.path("id").asInt();
                    String trailerKey = tmdbService.obtenerTrailer(tmdbId);
                    p.setTrailerKey(trailerKey);

                    // Duración simulada
                    p.setDuracion(90 + (int)(Math.random() * 60));

                    // --- CAMBIO CLAVE PARA CRÍTICAS ---
                    // 1. Guardamos la película PRIMERO para que tenga ID en BBDD
                    p = peliculaRepository.save(p);

                    // 2. Ahora buscamos sus críticas en TMDB
                    List<Critica> criticasReales = tmdbService.obtenerCriticasReales(tmdbId);

                    for (Critica c : criticasReales) {
                        c.setPelicula(p); // Asociamos la peli guardada
                        // c.setUsuario(null); // Es null por defecto
                        criticaRepository.save(c);
                    }

                    contador++;
                }
            }
        }
        return "Importación finalizada. " + contador + " películas añadidas con sus críticas reales.";
    }

    // AÑADE AQUÍ EL RESTO DE MÉTODOS QUE TENÍAS (sincronizarImagenes, reproducirAsync, tareaLentaSync, etc.)
    // Si no los tienes a mano, dímelo y te paso el archivo ENTERO con todo fusionado.
    public String tareaLentaSync(String titulo) {
        try {
            System.out.println("Iniciando tarea para " + titulo + " en " + Thread.currentThread().getName());
            Thread.sleep(3000);
            System.out.println("Terminando tarea para " + titulo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Procesada " + titulo;
    }

    public String procesarPeliculas() {
        long inicio = System.currentTimeMillis();
        this.tareaLentaSync("Interstellar");
        this.tareaLentaSync("The Dark Knight");
        this.tareaLentaSync("Soul");
        long fin = System.currentTimeMillis();
        return "Tiempo total: " + (fin - inicio) + " ms";
    }

    public CompletableFuture<String> procesarPeliculasAsync() {
        long inicio = System.currentTimeMillis();
        var t1 = this.tareaAsync.tareaLenta2Async("🍿 Interstellar");
        var t2 = this.tareaAsync.tareaLenta2Async("🦇 The Dark Knight");
        var t3 = this.tareaAsync.tareaLenta2Async("🎵 Soul");
        CompletableFuture.allOf(t1, t2, t3).join();
        long fin = System.currentTimeMillis();
        return CompletableFuture.completedFuture("Tiempo total (asíncrono): " + (fin - inicio) + " ms");
    }

    public String reproducirAsync() {
        long inicio = System.currentTimeMillis();
        var t1 = this.reproducir("🍿 Interstellar");
        var t2 = this.reproducir("🦇 The Dark Knight");
        var t3 = this.reproducir("🎵 Soul");
        CompletableFuture.allOf(t1, t2, t3).join();
        long fin = System.currentTimeMillis();
        return "Tiempo total (asíncrono): " + (fin - inicio) + " ms";
    }

    @Async("taskExecutor")
    public CompletableFuture<String> reproducir(String titulo) {
        Long inicio = System.currentTimeMillis();
        try {
            System.out.println("Reproduciendo " + titulo + " en " + Thread.currentThread().getName());
            int timeX = (int) (Math.random() * 5000) + 1;
            Thread.sleep(timeX);
            System.out.println("Terminando " + titulo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Long fin = System.currentTimeMillis();
        return CompletableFuture.completedFuture("Procesada " + titulo + " en " + ((fin - inicio)/1000) + " segundos");
    }

    public CompletableFuture<String> importarPeliculas(String rutaCarpeta) throws IOException {
        long inicio = System.currentTimeMillis();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        try (Stream<Path> paths = Files.list(Paths.get(rutaCarpeta))) {
            paths.filter(Files::isRegularFile).forEach(path -> {
                String nombre = path.toString().toLowerCase();
                if (nombre.endsWith(".csv") || nombre.endsWith(".txt")) {
                    futures.add(this.tareaAsync.importarCsvAsync(path));
                } else if (nombre.endsWith(".xml")) {
                    futures.add(this.tareaAsync.importarXmlAsync(path));
                }
            });
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        long fin = System.currentTimeMillis();
        return CompletableFuture.completedFuture("Importación completa en " + (fin - inicio) + " ms");
    }

    public Map<String, Integer> realizarVotaciones(int numJurados) throws InterruptedException {
        Map<String, Integer> resultados = new HashMap<>();
        resultados.put("Avatar", 0);
        resultados.put("Interestellar", 0);
        resultados.put("The Avengers", 0);
        List<CompletableFuture<Void>> futuros = new ArrayList<>();
        Long inicio = System.currentTimeMillis();
        for (int i = 1; i <= numJurados; i++) {
            futuros.add(tareaAsync.votar(resultados, i));
        }
        CompletableFuture.allOf(futuros.toArray(new CompletableFuture[0])).join();
        Long fin = System.currentTimeMillis();
        Long tiempo = fin - inicio;
        System.out.println();
        System.out.println("Hilos Activos:" + threadsJurado.getCorePoolSize() + ". Numero Jurados: " + numJurados + ". Tiempo: " + tiempo + " ms");
        return rankingOrdenado(resultados);
    }

    private Map<String, Integer> rankingOrdenado(Map<String, Integer> resultados) {
        Map<String, Integer> ranking = new LinkedHashMap<>();
        resultados.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(entry -> ranking.put(entry.getKey(), entry.getValue()));
        return ranking;
    }

    @Transactional
    public String sincronizarImagenes() {
        List<Pelicula> peliculas = peliculaRepository.findAll();
        int contador = 0;
        for (Pelicula p : peliculas) {
            if (p.getPosterPath() == null || p.getPosterPath().isEmpty()) {
                TmdbService.ImagenesPeliculaDTO imagenes = tmdbService.buscarImagenes(p.getTitulo());
                if (imagenes != null) {
                    p.setPosterPath(imagenes.posterPath());
                    p.setBackdropPath(imagenes.backdropPath());
                    peliculaRepository.save(p);
                    contador++;
                }
            }
        }
        return "Proceso finalizado. Se han actualizado las imágenes de " + contador + " películas.";
    }
}