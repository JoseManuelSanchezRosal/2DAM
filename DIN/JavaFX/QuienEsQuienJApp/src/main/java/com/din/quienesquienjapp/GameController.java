package com.din.quienesquienjapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameController {

    @FXML private TilePane tablero;
    @FXML private ComboBox<String> comboPreguntas;
    @FXML private Label lblFeedback;
    @FXML private VBox vboxHistorial; // Panel izquierdo para el historial

    private List<Personaje> personajes;
    private List<Personaje> personajesActivos;
    private Personaje personajeOculto;
    private Random random = new Random();

    @FXML
    public void initialize() {
        cargarPersonajes();
        reiniciarJuego(); // Inicia el juego y carga preguntas
    }

    private void cargarPersonajes() {
        personajes = new ArrayList<>();
        // Tus personajes personalizados
        personajes.add(new Personaje("Dante", "dante.png", false, false, false, false, Personaje.Genero.HOMBRE, Personaje.ColorPelo.BLANCO));
        personajes.add(new Personaje("Heidi", "heidi.jpg", false, false, false, true, Personaje.Genero.MUJER, Personaje.ColorPelo.NEGRO));
        personajes.add(new Personaje("Ironman", "ironman.jpeg", false, true, false, false, Personaje.Genero.HOMBRE, Personaje.ColorPelo.CALVO));
        personajes.add(new Personaje("Bob Esponja", "sponjebob.jpg", false, false, false, true, Personaje.Genero.HOMBRE, Personaje.ColorPelo.RUBIO));
        personajes.add(new Personaje("Torrente", "torrente.jpg", true, false, true, false, Personaje.Genero.HOMBRE, Personaje.ColorPelo.NEGRO));
        personajes.add(new Personaje("Yola", "yolaberrocal.jpg", false, false, false, false, Personaje.Genero.MUJER, Personaje.ColorPelo.RUBIO));
    }

    // Método para resetear las preguntas disponibles
    private void cargarPreguntasEstrategicas() {
        comboPreguntas.getItems().clear();
        comboPreguntas.getItems().addAll(
            "¿Es un dibujo animado?",
            "¿Es una persona de carne y hueso?",
            "¿Es Hombre?", 
            "¿Es Mujer?", 
            "¿Es Rubio o Amarillo?", 
            "¿Tiene el pelo Moreno o Negro?", 
            "¿Tiene el pelo Blanco?",
            "¿Es calvo o lleva casco completo?",
            "¿Lleva gafas?", 
            "¿Tiene barba o perilla?",
            "¿Es un niño o niña?",          
            "¿Es un superhéroe?",           
            "¿Vive debajo del mar?"         
        );
    }

    @FXML
    private void reiniciarJuego() {
        // 1. Resetear lógica interna
        personajesActivos = new ArrayList<>(personajes);
        personajeOculto = personajes.get(random.nextInt(personajes.size()));
        
        // 2. Resetear Interfaz Visual
        cargarPreguntasEstrategicas();       // Volver a poner todas las preguntas
        vboxHistorial.getChildren().clear(); // Limpiar el panel izquierdo
        lblFeedback.setText("¡Nuevo juego! Intenta adivinar quién soy.");
        
        System.out.println("DEBUG - Personaje Oculto: " + personajeOculto.getNombre());
        renderizarTablero();
    }

    private void renderizarTablero() {
        tablero.getChildren().clear();
        
        for (Personaje p : personajes) {
            VBox carta = new VBox(5);
            carta.setPrefSize(120, 160);

            if (personajesActivos.contains(p)) {
                // --- PERSONAJE ACTIVO (Vivo) ---
                carta.setStyle("-fx-background-color: white; -fx-border-color: #2196F3; -fx-border-width: 3; -fx-alignment: center; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0, 0, 5);");
                
                try {
                    String imagePath = "/img/" + p.getImagen();
                    Image img = new Image(getClass().getResourceAsStream(imagePath));
                    ImageView imgView = new ImageView(img);
                    imgView.setFitHeight(100); imgView.setFitWidth(100);
                    imgView.setPreserveRatio(true);
                    carta.getChildren().add(imgView);
                } catch (Exception e) {
                    carta.getChildren().add(new Label(p.getNombre()));
                }

                Label nombre = new Label(p.getNombre());
                nombre.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                carta.getChildren().add(nombre);
                
                // Clic para intentar ganar
                carta.setOnMouseClicked(e -> verificarVictoria(p));

            } else {
                // --- PERSONAJE DESCARTADO (Muerto) ---
                carta.setStyle("-fx-background-color: #444444; -fx-border-color: #222; -fx-alignment: center;");
                
                Label lblEliminado = new Label("X");
                lblEliminado.setStyle("-fx-text-fill: red; -fx-font-size: 60px; -fx-font-weight: bold;");
                carta.getChildren().add(lblEliminado);
                
                Label nombre = new Label(p.getNombre());
                nombre.setStyle("-fx-text-fill: #888888; -fx-strikethrough: true;");
                carta.getChildren().add(nombre);
            }

            tablero.getChildren().add(carta);
        }
    }

    @FXML
    private void realizarPregunta() {
        String pregunta = comboPreguntas.getValue();
        if (pregunta == null) return;

        // 1. Comprobar si es cierto
        boolean respuesta = obtenerRespuestaLogica(personajeOculto, pregunta);

        // 2. Feedback inferior y color
        if (respuesta) {
            lblFeedback.setText("¡SÍ! " + pregunta);
            lblFeedback.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;"); // Verde
        } else {
            lblFeedback.setText("NO. " + pregunta);
            lblFeedback.setStyle("-fx-text-fill: #F44336; -fx-font-weight: bold;"); // Rojo
        }
        
        // 3. AÑADIR AL HISTORIAL (IZQUIERDA)
        agregarAlHistorial(pregunta, respuesta);

        // 4. ELIMINAR DEL DESPLEGABLE (Para no repetir)
        comboPreguntas.getItems().remove(pregunta);
        comboPreguntas.getSelectionModel().clearSelection();
        
        // 5. Filtrar cartas
        filtrarPersonajes(pregunta, respuesta);
    }

    // Método para crear las etiquetas bonitas en el historial
    private void agregarAlHistorial(String pregunta, boolean respuesta) {
        Label lbl = new Label();
        lbl.setWrapText(true);
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.setPadding(new javafx.geometry.Insets(5));
        
        if (respuesta) {
            lbl.setText("✅ SÍ - " + pregunta);
            // Estilo verde claro
            lbl.setStyle("-fx-background-color: #dff0d8; -fx-text-fill: #3c763d; -fx-border-color: #d6e9c6; -fx-border-radius: 3; -fx-background-radius: 3;");
        } else {
            lbl.setText("❌ NO - " + pregunta);
            // Estilo rojo claro
            lbl.setStyle("-fx-background-color: #f2dede; -fx-text-fill: #a94442; -fx-border-color: #ebccd1; -fx-border-radius: 3; -fx-background-radius: 3;");
        }
        
        // Añadir al principio de la lista (índice 0)
        vboxHistorial.getChildren().add(0, lbl); 
    }

    // Lógica de respuesta de la máquina
    private boolean obtenerRespuestaLogica(Personaje p, String pregunta) {
        switch (pregunta) {
            case "¿Es un dibujo animado?": return p.isEsDibujo();
            case "¿Es una persona de carne y hueso?": return !p.isEsDibujo();
            case "¿Es Hombre?": return p.getGenero() == Personaje.Genero.HOMBRE;
            case "¿Es Mujer?": return p.getGenero() == Personaje.Genero.MUJER;
            case "¿Es Rubio o Amarillo?": return p.getColorPelo() == Personaje.ColorPelo.RUBIO;
            case "¿Tiene el pelo Moreno o Negro?": return p.getColorPelo() == Personaje.ColorPelo.NEGRO;
            case "¿Tiene el pelo Blanco?": return p.getColorPelo() == Personaje.ColorPelo.BLANCO;
            case "¿Es calvo o lleva casco completo?": return p.getColorPelo() == Personaje.ColorPelo.CALVO;
            case "¿Lleva gafas?": return p.isTieneGafas();
            case "¿Tiene barba o perilla?": return p.isTieneBarba();
            case "¿Es un niño o niña?": return p.getNombre().equals("Heidi");
            case "¿Es un superhéroe?": return p.getNombre().equals("Ironman");
            case "¿Vive debajo del mar?": return p.getNombre().equals("Bob Esponja");
            default: return false;
        }
    }

    private void filtrarPersonajes(String pregunta, boolean respuestaAfirmativa) {
        personajesActivos.removeIf(p -> {
            boolean tieneLaCaracteristica = obtenerRespuestaLogica(p, pregunta);
            // Si dijo SÍ, quitamos a los que NO la tienen.
            // Si dijo NO, quitamos a los que SÍ la tienen.
            return respuestaAfirmativa ? !tieneLaCaracteristica : tieneLaCaracteristica;
        });
        
        renderizarTablero();
        
        // Victoria automática si solo queda uno
        if (personajesActivos.size() == 1) {
            lblFeedback.setText("¡Solo queda uno! Es " + personajesActivos.get(0).getNombre() + ". Haz clic para ganar.");
        }
    }
    
    private void verificarVictoria(Personaje p) {
        if (p == personajeOculto) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡VICTORIA!");
            alert.setHeaderText("¡Lo has adivinado!");
            alert.setContentText("Efectivamente, era: " + p.getNombre());
            alert.showAndWait();
            reiniciarJuego();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("¡FALLASTE!");
            alert.setHeaderText("Ese no es...");
            alert.setContentText("Has perdido. El personaje era: " + personajeOculto.getNombre());
            alert.showAndWait();
            reiniciarJuego();
        }
    }

    @FXML
    private void abrirAyuda() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("help.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ayuda");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.NONE); 
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}