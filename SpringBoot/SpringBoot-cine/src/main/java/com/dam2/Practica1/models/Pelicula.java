package com.dam2.Practica1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity // Indica a Hibernate que esta clase es una tabla en la base de datos
@Table(name = "peliculas") // Fuerza el nombre de la tabla en plural
@Data  // LOMBOK genera Getters, Setters, toString, Equals y HasCode automaticamente
@AllArgsConstructor // LOMBOK genera un constructor con todos los argumentos
@NoArgsConstructor // LOMBOK genera un constructor vacio (obligatorio para que Hibernate cree instancias de la clase
public class Pelicula {

    @Id // Para marcar el atributo como PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTOINCREMENT para MySQL, IDENTITY para SQL Server
    private Long id;
    @Column(nullable = false, length = 120)
    private String titulo;
    private int duracion;              // minutos
    @Column(name = "fecha_estreno") // Mapea camelCase a snake_case para la columna SQL
    private String fechaEstreno;
    private String sinopsis;
    private int valoracion;

    //----------------------------------INTERRELACIONES-----------------------------------------

    /**
     * RELACIÓN 1:N con CRITICA (Uno a Muchos)
     * - mappedBy = "pelicula": Indica que NO tenemos la FK aquí. La FK está en la clase Critica, atributo 'pelicula'.
     * - cascade = CascadeType.ALL: Si borras la Película, borras sus críticas (Integridad).
     * - orphanRemoval = true: Si quitas una crítica de esta lista, se borra de la BBDD.
     */
    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("pelicula")
    private List<Critica> listaCriticas = new ArrayList<>();


    /**
     * RELACIÓN N:1 con DIRECTOR (Muchos a Uno)
     * - Diagrama E-R (1,1) en lado Película: Una peli TIENE que tener director.
     * - nullable = true: Lo has puesto a true para permitir guardar borradores, aunque el E-R dice (1,1).
     * Si quieres ser estricto con el diagrama, pon nullable = false.
     */
    @ManyToOne // NO SE PONE CASCADE, para no borrar al director al borrar la pelicula.
    @JoinColumn(name = "director_id", nullable = true) // FK en PELICULA (nullable a true para dejar meter pelis sin director)
    @JsonIgnoreProperties("peliculas") // MUESTRA EL DIRECTOR, PERO OCULTA SU LISTA DE PELICULAS
    private Director director;

    /**
     * RELACIÓN N:M con ACTOR (Muchos a Muchos)
     * - mappedBy = "peliculas": 'Actor' es el dueño (tiene el @JoinTable). Aquí solo reflejamos.
     */
    @ManyToMany(mappedBy = "peliculas") // NOTA>> Actor es el dueno
    @JsonIgnoreProperties("peliculas") // Evita bucle infinito
    private List<Actor> actores = new ArrayList<>();

    /**
     * RELACIÓN N:M con CATEGORIA (Dueño de la relación)
     * - CascadeType.PERSIST/MERGE: Si guardo peli, guardo sus categorías nuevas. NO DELETE (borrar peli no borra la categoría).
     * - @JoinTable: Define la tabla intermedia física 'pelicula_categoria'.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "pelicula_categoria",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    @JsonIgnoreProperties("peliculas")
    private List<Categoria> categorias = new ArrayList<>();

    // PELICULA--N:M--PLATAFORMA
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "pelicula_plataforma",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "plataforma_id"))
    @JsonIgnoreProperties("peliculas")
    private List<Plataforma> plataformas = new ArrayList<>();

    // PELICULA--N:M--IDIOMA
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "pelicula_idioma",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "idioma_id"))
            @JsonIgnoreProperties("peliculas")
    private List<Idioma> idiomas = new ArrayList<>();


    //----------------------HELPERS (SINCRONIZACION EN MEMORIA-----------------------

    // 1- Metodo Helper ADD CATEGORIA
    public void addCategoria(Categoria c){
        // Paso A: proteccion defensiva contra NULOS y DUPLICADOS
        // evita anadir duplicados si ya existen en la lista
        if(c == null || this.categorias.contains(c)){
            return;
        }
        // Paso B: Actualizar el lado "YO" (pelicula)
        // Anado la categoria a mi lista interna
        this.categorias.add(c);

        // Paso C: Actualizo el lado "OTRO" (categoria)
        // Cojo el objeto categoria y le digo: "Oye, agregame a mi (this) a tu lista de Peliculas
        c.getPeliculas().add(this);
    }

    // 2 Metodo Helper REMOVE CATEGORIA
    public void removeCategoria(Categoria c){
        if(c == null){
            return;
        }
        // Paso A: Me quito la categoria a mi mismo (Pelicula)
        this.categorias.remove(c);

        // Paso B: Le digo a categoria que me olvide a mi (Pelicula)
        // Si no hacemos esto, la categoria sigue teniendo en memoria una referencia a esta pelicula
        c.getPeliculas().remove(this);
    }

    // Metodo Helper ADD PLATAFORMA
    public void addPlataforma(Plataforma p){
        // 1 Evita anadir plataformas duplicadas
        if(p == null || this.plataformas.contains(p)){
            return;
        }
        // 2 Anado esta plataforma a mi lista
        this.plataformas.add(p);

        // Me anado a la lista de peliculas en el objeto Plataforma
        p.getPeliculas().add(this);
    }

    // Metodo Helper REMOVE PLATAFORMA
    public void removePlataforma(Plataforma p){
        // 1 Me quito la plataforma a mi mismo (Pelicula)
        this.plataformas.remove(p);

        // 2 Le digo a Plataforma que se olvide de mi (Pelicula)
        p.getPeliculas().remove(this);
    }

    // Metodo Helper ADD IDIOMA
    public void addIdioma(Idioma i){
        if(i == null || this.idiomas.contains(i)){
            return;
        }
        this.idiomas.add(i);
        i.getPeliculas().add(this);
    }

    // Metodo Helper REMOVE IDIOMA
    public void removeIdioma(Idioma i){
        this.idiomas.remove(i);
        i.getPeliculas().remove(this);
    }
}