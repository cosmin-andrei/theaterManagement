module ro.iss2024.theatermanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens ro.iss2024.theatermanagement to javafx.fxml;
    exports ro.iss2024.theatermanagement;
}