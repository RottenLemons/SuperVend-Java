module SuperVend {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.jfoenix;
    requires javafx.base;
    requires javafx.media;

    opens sample;
    opens sample.Controller;
    opens sample.View;

}