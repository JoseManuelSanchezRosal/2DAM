module fridgeapp {
    requires javafx.controls;
    requires javafx.fxml;
    exports fridgeapp;
    opens fridgeapp.controller to javafx.fxml;
    opens fridgeapp.model to javafx.base, javafx.fxml;
}
