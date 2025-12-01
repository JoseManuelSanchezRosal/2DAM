module com.josemanuel.tpv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.dotenv;


    opens com.josemanuel.tpv.controllers to javafx.fxml;
    opens com.josemanuel.tpv.components to javafx.fxml;
    opens com.josemanuel.tpv.dto to javafx.base;
    exports com.josemanuel.tpv;
    exports com.josemanuel.tpv.controllers;
    exports com.josemanuel.tpv.components;
}