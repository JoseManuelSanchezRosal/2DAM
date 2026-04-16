package TriviaConIA;

import java.util.List;

public class Pregunta {
    private String enunciado;
    private List<String> opciones;
    private String correcta;

    public Pregunta(String enunciado, List<String> opciones, String correcta) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.correcta = correcta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public String getCorrecta() {
        return correcta;
    }
}