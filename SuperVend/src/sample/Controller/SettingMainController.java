package sample.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingMainController implements Initializable {
    @FXML
    private AnchorPane sidePane;
    @FXML
    private JFXButton homeBtn;
    @FXML
    private AnchorPane iconPane;
    @FXML
    private Hyperlink aboutHyperlink;
    @FXML
    private JFXButton lightBtn;
    @FXML
    private JFXButton darkBtn;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private Rectangle r;
    @FXML
    private Rectangle r2;
    @FXML
    private Rectangle r3;
    private static String filename;

    @FXML
    private void homeBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource(filename));
        Stage stage = (Stage) homeBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void aboutHyperlinkOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/about.fxml"));
        Stage stage = new Stage();
        stage.setTitle("About SuperVend");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }

    public static void setFile(String filename) {
        SettingMainController.filename = filename;
    }

    @FXML
    private void themeBtnOnClick(ActionEvent event) {
        if (event.getSource().equals(darkBtn)) {
            SettingController.dark = true;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    FillTransition f = new FillTransition(Duration.millis(500), r, Color.web("#eee"), Color.web("#303030"));
                    FillTransition f1 = new FillTransition(Duration.millis(500), r2, Color.web("#eee"), Color.web("#252525"));
                    FillTransition f2 = new FillTransition(Duration.millis(500), r3, Color.web("#ddd"), Color.web("#353535"));
                    f.setCycleCount(1);
                    f1.setCycleCount(1);
                    f2.setCycleCount(1);
                    ParallelTransition parallelTransition = new ParallelTransition();
                    parallelTransition.getChildren().addAll(f, f1, f2);
                    parallelTransition.setCycleCount(1);
                    darkBtn.setStyle("-fx-border-color: #fff; -fx-background-color: #000");
                    lightBtn.setStyle("-fx-border-color: #fff; -fx-background-color: #fff");
                    homeBtn.setStyle("-fx-text-fill: #eee");
                    mainPane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            parallelTransition.play();
                        }
                    });
                }
            });
            t.start();
        } else {
            SettingController.dark = false;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    FillTransition f = new FillTransition(Duration.millis(500), r, Color.web("#383838"), Color.web("#eee"));
                    FillTransition f1 = new FillTransition(Duration.millis(500), r2, Color.web("#252525"), Color.web("#eee"));
                    FillTransition f2 = new FillTransition(Duration.millis(500), r3, Color.web("#353535"), Color.web("#ddd"));
                    f.setCycleCount(1);
                    f1.setCycleCount(1);
                    f2.setCycleCount(1);
                    ParallelTransition parallelTransition = new ParallelTransition();
                    parallelTransition.getChildren().addAll(f, f1, f2);
                    parallelTransition.setCycleCount(1);
                    darkBtn.setStyle("-fx-border-color: #000; -fx-background-color: #000");
                    lightBtn.setStyle("-fx-border-color: #000; -fx-background-color: #fff");
                    sidePane.getStylesheets().clear();
                    homeBtn.setStyle("");
                    mainPane.getStylesheets().clear();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            scenePane.getStylesheets().clear();
                            parallelTransition.play();
                        }
                    });
                }
            });
            t.start();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (SettingController.dark == true) {
            lightBtn.setStyle("-fx-background-color: #fff");
            darkBtn.setStyle("-fx-background-color: #000");
            r3.setFill(Paint.valueOf("#353535"));
            sidePane.getStylesheets().add(Main.class.getResource("View/dark.css").toExternalForm());
            r2.setFill(Paint.valueOf("#252525"));
            mainPane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
            r.setFill(Paint.valueOf("#303030"));
        } else {
            lightBtn.setStyle("-fx-border-color: #000; -fx-background-color: #fff");
            darkBtn.setStyle("-fx-border-color: #000; -fx-background-color: #000");
        }
    }
}
