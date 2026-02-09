module com.din.quienesquienjapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web; 

    opens com.din.quienesquienjapp to javafx.fxml;
    exports com.din.quienesquienjapp;
}