package com.dam2.Practica1.service;

import com.dam2.Practica1.models.Pelicula;
import com.dam2.Practica1.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class TareaAsync {
    @Autowired
    private PeliculaRepository peliculaRepository;

    // Tarea 3 - Importar CSV
    @Async("taskExecutor")
    public CompletableFuture<Void> importarCsvAsync(Path fichero) {
        try {
            System.out.println("Procesando CSV: " + fichero + " en " + Thread.currentThread().getName());

            List<Pelicula> lista = new ArrayList<>();

            List<String> lineas = Files.readAllLines(fichero);
            lineas.remove(0); // suponemos encabezado

            for (String linea : lineas) {
                String[] campos = linea.split(";");
                Pelicula p = new Pelicula();
                p.setTitulo(campos[0]);
                p.setDuracion(Integer.parseInt(campos[1]));
                p.setFechaEstreno(LocalDate.parse(campos[2]));
                p.setSinopsis(campos[3]);
                lista.add(p);
            }

            peliculaRepository.saveAll(lista);

            System.out.println("Finalizado CSV: " + fichero);

        } catch (Exception e) {
            System.err.println("Error en CSV " + fichero + ": " + e.getMessage());
        }

        return CompletableFuture.completedFuture(null);
    }

    // Tarea 3 -Importar XML
    @Async("taskExecutor")
    public CompletableFuture<Void> importarXmlAsync(Path fichero) {
        try {
            System.out.println("Procesando XML: " + fichero + " en " + Thread.currentThread().getName());

            List<Pelicula> lista = new ArrayList<>();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(fichero.toFile());
            NodeList nodos = doc.getElementsByTagName("pelicula");

            for (int i = 0; i < nodos.getLength(); i++) {
                Element e = (Element) nodos.item(i);

                Pelicula p = new Pelicula();
                p.setTitulo(e.getElementsByTagName("titulo").item(0).getTextContent());
                p.setDuracion(Integer.parseInt(e.getElementsByTagName("duracion").item(0).getTextContent()));
                p.setFechaEstreno(LocalDate.parse(e.getElementsByTagName("fechaEstreno").item(0).getTextContent()));
                p.setSinopsis(e.getElementsByTagName("sinopsis").item(0).getTextContent());

                lista.add(p);
            }
            peliculaRepository.saveAll(lista);

            System.out.println("Finalizado XML: " + fichero);

        } catch (Exception e) {
            System.err.println("Error en XML " + fichero + ": " + e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
    }

    // Ejercicio 1.4 - Reproducir @Async
    @Async("taskExecutor")
    public CompletableFuture<String> tareaLenta2Async(String titulo) {
        try {
            System.out.println("Iniciando " + titulo + " en " + Thread.currentThread().getName());
            Thread.sleep(3000);
            System.out.println("Terminando " + titulo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture("Procesada " + titulo);
    }

    // Ejercicio 4 - Votacion a Oscars
    private final Random random = new Random();
    /**
     * Metodo para lanzar hilos (jurados) a votar con ThreadPoolExecutor
     * @param votos es nuestro Mapa de acumulacion de votos
     * @param juradoId identificador del Jurado
     * @return el CompetableFuture null para decirle al orquestador que la tarea ha terminado
     * @throws InterruptedException
     */

    @Async("threadsJurado")
    public CompletableFuture<Void> votar(Map<String, Integer> votos, int juradoId) throws InterruptedException {
        String[] peliculas = {"Interestellar", "Avatar", "The Avengers"};

        for (String pelicula : peliculas) {
            int puntos = random.nextInt(11); // 0-10 puntos
            synchronized (votos) {
                votos.put(pelicula, votos.get(pelicula) + puntos);
            }
            System.out.println("Jurado nº:" + juradoId + " vota: " + puntos + " puntos a " + pelicula);
        }
        return CompletableFuture.completedFuture(null);
    }
}