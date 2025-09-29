module org.example.pruebaintellijjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.pruebaintellijjavafx to javafx.fxml;
    exports org.example.pruebaintellijjavafx;
}