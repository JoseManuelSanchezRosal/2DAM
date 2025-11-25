package com.dam2.Practica1.service;

import com.dam2.Practica1.dto.PeliculaDTO.PeliculaDTO;
import com.dam2.Practica1.models.Pelicula;
import com.dam2.Practica1.repository.PeliculaRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;
import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
@Getter
public class PeliculaService {
    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private TareaAsync tareaAsync;

    // Para sacar info de nuestro Executor de hilos @Async
    @Autowired
    @Qualifier("threadsJurado")
    private ThreadPoolTaskExecutor threadsJurado;

    private PeliculaDTO toDTO(Pelicula p){
        return new PeliculaDTO(
                p.getId(),
                p.getTitulo(),
                p.getDuracion(),
                p.getFechaEstreno(),
                p.getSinopsis(),
                p.getValoracion()
        );
    }


    private final List<Pelicula> peliculas = new ArrayList<>();
    /*private final PeliculaRepository peliculaRepository;*/

    public List<Pelicula> mejores_peliculas(int valoracion){
        List<Pelicula> peliculas_aux= new ArrayList<>();
        for (Pelicula p : peliculas) {
            if (p.getValoracion()>=valoracion) {
                peliculas_aux.add(p);
            }
        }
        return peliculas_aux;
    }


    public List<PeliculaDTO> listar() {
        return peliculaRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Pelicula buscarPorId(Long id) {
        for (Pelicula p : peliculas) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
        /*
        * return peliculas.stream()                 // convierte la lista en un flujo de datos
        .filter(p -> p.getId().equals(id)) // se queda solo con las películas cuyo id coincide
        .findFirst()                       // toma la primera coincidencia (si existe)
        .orElse(null);                     // devuelve esa película o null si no hay
        * */
    }

    public void agregar(Pelicula pelicula) {
        peliculas.add(pelicula);
    }

    // Ejercicio 1.1 Metodo Sync
    public String tareaLentaSync(String titulo) {
        try {
            System.out.println("Iniciando tarea para " + titulo + " en " + Thread.currentThread().getName());
            Thread.sleep(3000); // simula proceso lento (3 segundos)
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

    // La tareaLenta2Async se tiene que usar en otra clase diferente porque las anotaciones solo se ejecutan cuando son ejecutados los métodos desde otra clase distinta
    public CompletableFuture<String> procesarPeliculasAsync() {
        long inicio = System.currentTimeMillis();

        var t1 = this.tareaAsync.tareaLenta2Async("🍿 Interstellar");
        var t2 = this.tareaAsync.tareaLenta2Async("🦇 The Dark Knight");
        var t3 = this.tareaAsync.tareaLenta2Async("🎵 Soul");
        var t4 = this.tareaAsync.tareaLenta2Async("🎵 Soul");
        var t5 = this.tareaAsync.tareaLenta2Async("🎵 Soul");
        var t6 = this.tareaAsync.tareaLenta2Async("🎵 Soul");
        //var t7 = service.tareaLenta2("🎵 Soul");

        // Ejercicio 1.6 Espera a que terminen todas las tareas
        CompletableFuture.allOf(t1, t2, t3,t4,t5,t6).join();

        // Ejercicio 1.7 Calcular tiempo total
        long fin = System.currentTimeMillis();
        return CompletableFuture.completedFuture("Tiempo total (asíncrono): " + (fin - inicio) + " ms");
    }

    public String reproducirAsync() {
        long inicio = System.currentTimeMillis();

        var t1 = this.reproducir("🍿 Interstellar");
        var t2 = this.reproducir("🦇 The Dark Knight");
        var t3 = this.reproducir("🎵 Soul");

        // Espera a que terminen todas las tareas
        CompletableFuture.allOf(t1, t2, t3).join();

        long fin = System.currentTimeMillis();
        return "Tiempo total (asíncrono): " + (fin - inicio) + " ms";
    }

    // Ejercicio 1.4

    // Ejercicio 2 Crear metodo Async que simule reproduccion de peliculas con tiempo aleatorio entre 1 y 5 sg
    @Async("taskExecutor")
    public CompletableFuture<String> reproducir(String titulo) {
        Long inicio = System.currentTimeMillis();
        try {
            System.out.println("Reproduciendo " + titulo + " en " + Thread.currentThread().getName());
            // Generamos el random entre 1 y 5 segundos
            int timeX = (int) (Math.random() * 5000) + 1;

            Thread.sleep(timeX);

            System.out.println("Terminando " + titulo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Calculamos tiempo de reproduccion
        Long fin = System.currentTimeMillis();
        return CompletableFuture.completedFuture("Procesada " + titulo + " en " + ((fin - inicio)/1000) + " segundos");
    }

    // Ejercicio 3
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
        // Esperar a que terminen todas las tareas asíncronas
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        long fin = System.currentTimeMillis();
        return CompletableFuture.completedFuture("Importación completa en " + (fin - inicio) + " ms");
    }

    // Ejercicio 4 - Premios Oscar
    /**
     * Metodo para ejecutar la votacion concurrente
     * @param numJurados indica el numero de Jurados (obtenido de la ruta)
     * @return el Map de Ranking de Peliculas la suma de Votos
     * @throws InterruptedException
     */
    public Map<String, Integer> realizarVotaciones(int numJurados) throws InterruptedException {

        // Inicializamos Mapa a 0 puntos
        Map<String, Integer> resultados = new HashMap<>();
        resultados.put("Avatar", 0);
        resultados.put("Interestellar", 0);
        resultados.put("The Avengers", 0);

        // Lista para guardar las referencias a las tareas futuras
        List<CompletableFuture<Void>> futuros = new ArrayList<>();

        Long inicio = System.currentTimeMillis();
        // Lanzamos los hilos (cada Jurado realiza 3 votaciones, una por pelicula)
        for (int i = 1; i <= numJurados; i++) {
            futuros.add(tareaAsync.votar(resultados, i));
        }
        // Una vez todos los hilos hayan terminado, se completara el Future
        CompletableFuture.allOf(futuros.toArray(new CompletableFuture[0])).join();
        Long fin = System.currentTimeMillis();

        // Y retornamos el Ranking (ordenado con el metodo rankingOrdenado())
        Long tiempo = fin - inicio;
        // Para ver numero de hilos activos
        System.out.println();
        System.out.println("Hilos Activos:" + threadsJurado.getCorePoolSize() + ". Numero Jurados: " + numJurados + ". Tiempo: " + tiempo + " ms");
        return rankingOrdenado(resultados);
    }
    // Ordena las películas por puntuación descendente (GPT)
    private Map<String, Integer> rankingOrdenado(Map<String, Integer> resultados) {
        Map<String, Integer> ranking = new LinkedHashMap<>();
        resultados.entrySet()
                .stream()
                // compara por valor y revierte cadena:
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                // asegura que los datos del nuevo mapa se introduzcan en el mismo orden que el original:
                .forEachOrdered(entry -> ranking.put(entry.getKey(), entry.getValue()));
        return ranking;
    }
}