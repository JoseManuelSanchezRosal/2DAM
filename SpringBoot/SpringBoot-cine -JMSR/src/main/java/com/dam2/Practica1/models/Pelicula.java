package com.dam2.Practica1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "peliculas")
@Data  // ✅ Lombok genera getters, setters, toString, equals, hashCode
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titulo;

    private int duracion;              // minutos

    @Column(name = "fecha_estreno")
    private LocalDate fechaEstreno;

    private String sinopsis;

    private int valoracion;

    @OneToOne
    @JoinColumn(name = "ficha_id") // FK en la tabla Pelicula
    private FichaTecnica fichaTecnica;

    @ManyToOne
    @JoinColumn(name = "director_id", nullable = true) // FK en PELICULA (nullable a true para dejar meter pelis sin director)
    @JsonManagedReference // Lado principal para Json
    private Director director;

    @ManyToMany(mappedBy = "peliculas")
    private List<Actor> actors = new ArrayList<>();
    @JsonIgnore // No saque actores

    public Pelicula(Long id, String titulo, int duracion, LocalDate fechaEstreno, String sinopsis, int valoracion, FichaTecnica fichaTecnica, Director director, List<Actor> actors) {
        this.id = id;
        this.titulo = titulo;
        this.duracion = duracion;
        this.fechaEstreno = fechaEstreno;
        this.sinopsis = sinopsis;
        this.valoracion = valoracion;
        this.fichaTecnica = fichaTecnica;
        this.director = director;
        this.actors = actors;
    }

    public Pelicula() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(LocalDate fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public FichaTecnica getFichaTecnica() {
        return fichaTecnica;
    }

    public void setFichaTecnica(FichaTecnica fichaTecnica) {
        this.fichaTecnica = fichaTecnica;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
