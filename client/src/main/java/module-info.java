module org.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.logging;

    opens org.client to javafx.fxml;
    exports org.client;
    exports controllers;
    opens controllers to javafx.fxml;
}
