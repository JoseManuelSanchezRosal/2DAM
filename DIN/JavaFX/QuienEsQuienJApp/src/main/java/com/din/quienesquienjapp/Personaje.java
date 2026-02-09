package com.din.quienesquienjapp;

/**
 * Clase que representa a cada carta/personaje del juego.
 * Modificada para incluir si es dibujo animado o real.
 */
public class Personaje {
    private String nombre;
    private String imagen; 
    private boolean tieneGafas;
    private boolean tieneSombrero;
    private boolean tieneBarba;
    private boolean esDibujo; // NUEVO ATRIBUTO: Diferencia clave en tu juego
    private Genero genero;
    private ColorPelo colorPelo;

    public enum Genero { HOMBRE, MUJER }
    public enum ColorPelo { RUBIO, CASTANO, NEGRO, PELIRROJO, CALVO, BLANCO }

    public Personaje(String nombre, String imagen, boolean gafas, boolean sombrero, 
                     boolean barba, boolean esDibujo, Genero genero, ColorPelo pelo) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.tieneGafas = gafas;
        this.tieneSombrero = sombrero;
        this.tieneBarba = barba;
        this.esDibujo = esDibujo;
        this.genero = genero;
        this.colorPelo = pelo;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getImagen() { return imagen; }
    public boolean isTieneGafas() { return tieneGafas; }
    public boolean isTieneSombrero() { return tieneSombrero; }
    public boolean isTieneBarba() { return tieneBarba; }
    public boolean isEsDibujo() { return esDibujo; } // Nuevo Getter
    public Genero getGenero() { return genero; }
    public ColorPelo getColorPelo() { return colorPelo; }
}