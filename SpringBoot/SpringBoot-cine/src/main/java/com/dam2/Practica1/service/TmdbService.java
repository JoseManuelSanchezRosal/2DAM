package com.dam2.Practica1.service;

import com.dam2.Practica1.models.Critica; // <--- NUEVO IMPORT NECESARIO
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service // Indica a Spring que esto es un componente de lógica de negocio
public class TmdbService {

    // Leemos la API KEY de tu archivo application.yml
    @Value("${tmdb.api.key}")
    private String apiKey;

    // Leemos la URL base de tu archivo application.yml
    @Value("${tmdb.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public TmdbService() {
        // RestTemplate es la herramienta de Spring para hacer peticiones HTTP (GET, POST...)
        this.restTemplate = new RestTemplate();
    }

    /**
     * Busca una película en la API externa y devuelve sus rutas de imagen.
     */
    public ImagenesPeliculaDTO buscarImagenes(String titulo) {
        try {
            // 1. Construimos la URL de búsqueda.
            String url = String.format("%s/search/movie?api_key=%s&query=%s", apiUrl, apiKey, titulo);

            // 2. Hacemos la petición GET a internet y recibimos el JSON como texto
            String jsonResponse = restTemplate.getForObject(url, String.class);

            // 3. Usamos Jackson (ObjectMapper) para leer el JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode results = root.path("results");

            // 4. Si hay resultados, cogemos el primero (el más probable)
            if (results.isArray() && results.size() > 0) {
                JsonNode firstMovie = results.get(0);

                String poster = firstMovie.path("poster_path").asText();
                String backdrop = firstMovie.path("backdrop_path").asText();

                // Devolvemos nuestro paquetito de datos con las dos rutas
                return new ImagenesPeliculaDTO(poster, backdrop);
            }

        } catch (Exception e) {
            // Si algo falla (internet caído, clave mal...), imprimimos error pero no rompemos la app
            System.err.println("Error buscando en TMDB: " + e.getMessage());
        }
        return null; // Si no encontramos nada
    }

    // Un "Record" es una clase inmutable para transportar datos simples (Java 14+)
    public record ImagenesPeliculaDTO(String posterPath, String backdropPath) {}

    // Anadir dinamismo a pagina (importando peliculas de TMDB)
    /**
     * Obtiene una lista de películas populares de TMDB por número de página.
     * Cada página suele traer 20 películas.
     */
    public List<JsonNode> obtenerPeliculasPopulares(int pagina) {
        List<JsonNode> peliculas = new ArrayList<>();
        try {
            // URL para obtener populares: /movie/popular?page=X
            String url = String.format("%s/movie/popular?api_key=%s&language=es-ES&page=%d", apiUrl, apiKey, pagina);

            String jsonResponse = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode results = root.path("results");

            if (results.isArray()) {
                for (JsonNode node : results) {
                    peliculas.add(node);
                }
            }
        } catch (Exception e) {
            System.err.println("Error importando página " + pagina + ": " + e.getMessage());
        }
        return peliculas;
    }

    /**
     * Busca el trailer. Primero intenta en Español (es-ES).
     * Si no encuentra nada, lo intenta en Inglés (en-US) para no dejar la peli vacía.
     */
    public String obtenerTrailer(int idTmdb) {
        // 1. Primer intento: ESPAÑOL
        String trailerKey = buscarTrailerEnApi(idTmdb, "es-ES");

        // 2. Si es null, Segundo intento: INGLÉS (Fallback)
        if (trailerKey == null) {
            System.out.println("⚠️ No hay trailer en español para ID " + idTmdb + ". Buscando en inglés...");
            trailerKey = buscarTrailerEnApi(idTmdb, "en-US");
        }

        return trailerKey;
    }

    // Método auxiliar privado para no repetir código
    private String buscarTrailerEnApi(int idTmdb, String idioma) {
        try {
            // Añadimos &language=... a la URL
            String url = String.format("%s/movie/%d/videos?api_key=%s&language=%s", apiUrl, idTmdb, apiKey, idioma);
            String jsonResponse = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode results = root.path("results");

            if (results.isArray()) {
                for (JsonNode video : results) {
                    // Buscamos solo de YouTube y que sea Trailer
                    if (video.path("site").asText().equals("YouTube") &&
                            video.path("type").asText().equals("Trailer")) {
                        return video.path("key").asText();
                    }
                }
            }
        } catch (Exception e) {
            // Silenciamos el error para permitir que el flujo continúe al siguiente idioma
        }
        return null;
    }

    /**
     * NUEVO MÉTODO: Obtiene las reseñas reales de TMDB para una película
     */
    public List<Critica> obtenerCriticasReales(int idTmdb) {
        List<Critica> listaCriticas = new ArrayList<>();
        try {
            // URL: /movie/{id}/reviews
            // OJO: No filtramos por idioma para que traiga aunque sean en inglés, si no muchas vendrían vacías.
            String url = String.format("%s/movie/%d/reviews?api_key=%s", apiUrl, idTmdb, apiKey);
            String jsonResponse = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode results = root.path("results");

            if (results.isArray()) {
                // Limitamos a 5 críticas por peli para no saturar tu BBDD local
                int limite = 0;
                for (JsonNode nodo : results) {
                    if (limite >= 5) break;

                    Critica c = new Critica();

                    // 1. Contenido
                    String contenido = nodo.path("content").asText();
                    // Cortamos si es muy largo (seguridad para BBDD)
                    if (contenido.length() > 2000) {
                        contenido = contenido.substring(0, 1997) + "...";
                    }
                    c.setComentario(contenido);

                    // 2. Nota (Rating)
                    // TMDB guarda la nota dentro de "author_details" -> "rating"
                    int nota = 5; // Nota por defecto si es null
                    JsonNode ratingNode = nodo.path("author_details").path("rating");
                    if (!ratingNode.isMissingNode() && !ratingNode.isNull()) {
                        nota = ratingNode.asInt();
                        // Ajustamos rango si viene raro (TMDB permite null o hasta 10)
                        if (nota > 10) nota = 10;
                        if (nota < 0) nota = 0;
                    }
                    c.setNota(nota);

                    // 3. Fecha
                    // Formato TMDB: "2023-07-21T14:30:00.000Z" -> Cortamos para quedarnos con "2023-07-21"
                    String fecha = nodo.path("created_at").asText();
                    if (fecha != null && fecha.length() >= 10) {
                        c.setFecha(fecha.substring(0, 10));
                    } else {
                        c.setFecha(java.time.LocalDate.now().toString());
                    }

                    // El usuario se asignará como NULL en el servicio de importación (Anónimo)

                    listaCriticas.add(c);
                    limite++;
                }
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo reviews para ID " + idTmdb + ": " + e.getMessage());
        }
        return listaCriticas;
    }
}