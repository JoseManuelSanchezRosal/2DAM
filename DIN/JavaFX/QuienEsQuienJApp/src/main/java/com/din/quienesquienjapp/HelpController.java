package com.din.quienesquienjapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HelpController implements Initializable {

    @FXML private TreeView<String> arbolTemas;
    @FXML private WebView visorAyuda;
    private WebEngine engine;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        engine = visorAyuda.getEngine();
        cargarArbolAyuda();
    }

    private void cargarArbolAyuda() {
        // 1. Nodo Raíz
        TreeItem<String> rootItem = new TreeItem<>("Ayuda Quién es Quién");
        rootItem.setExpanded(true);

        // 2. Ramas Principales
        TreeItem<String> itemIntro = new TreeItem<>("Introducción");
        
        TreeItem<String> itemComoJugar = new TreeItem<>("Cómo Jugar");
        itemComoJugar.getChildren().add(new TreeItem<>("Objetivo del Juego"));
        itemComoJugar.getChildren().add(new TreeItem<>("Controles e Interfaz"));
        itemComoJugar.setExpanded(true); 

        TreeItem<String> itemReglas = new TreeItem<>("Reglas");
        
        // NOTA: Hemos eliminado la rama de FAQ como pediste

        // 3. Añadir al árbol
        rootItem.getChildren().addAll(itemIntro, itemComoJugar, itemReglas);
        arbolTemas.setRoot(rootItem);

        // 4. Evento de selección
        arbolTemas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarPagina(newVal.getValue());
            }
        });
        
        cargarPagina("Introducción");
    }

    private void cargarPagina(String tema) {
        String archivo = "";
        
        switch (tema) {
            case "Introducción": archivo = "index.html"; break;
            case "Objetivo del Juego": archivo = "objetivo.html"; break;
            case "Controles e Interfaz": archivo = "controles.html"; break;
            case "Reglas": archivo = "reglas.html"; break;
            default: return; 
        }

        try {
            // Ruta absoluta a la carpeta resources/help/
            URL url = getClass().getResource("/help/" + archivo);
            if (url != null) {
                engine.load(url.toExternalForm());
            } else {
                engine.loadContent("<html><body><h3>Error</h3><p>No se encuentra " + archivo + "</p></body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}