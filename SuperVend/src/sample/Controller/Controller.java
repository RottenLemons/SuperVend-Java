package sample.Controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.Security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {
    @FXML
    private AnchorPane sidePane;
    @FXML
    private AnchorPane iconPane;
    @FXML
    private ImageView image;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXButton settingsBtn;
    @FXML
    private JFXButton submitBtn;
    @FXML
    private JFXTextField userIDTF;
    @FXML
    private JFXPasswordField passwordTF;
    @FXML
    private JFXCheckBox showPassword;
    @FXML
    private JFXTextField passwordTF1;
    @FXML
    private AnchorPane bigPane;
    @FXML
    private AnchorPane scenePane;
    public static Security security = new Security("src/sample/Model/Resource/Data Files/Login.csv");
    public static String iconFile = "";

    @FXML
    private void settingsBtnOnClick(ActionEvent ex) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/setting.fxml"));
        Stage stage = (Stage) submitBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void loginBtnOnClick(ActionEvent ex) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/sample.fxml"));
        Stage stage = (Stage) submitBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void submitBtnOnClick(ActionEvent ex) throws IOException {
        if (security.authenticate(userIDTF.getText(), passwordTF.getText()) == 0 || security.authenticate(userIDTF.getText(), passwordTF1.getText()) == 0) {
            Parent root = FXMLLoader.load(Main.class.getResource("View/main.fxml"));
            Stage stage = (Stage) submitBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
        } else if (security.authenticate(userIDTF.getText(), passwordTF.getText()) == 1 || security.authenticate(userIDTF.getText(), passwordTF1.getText()) == 1) {
            Parent root = FXMLLoader.load(Main.class.getResource("View/adminMain.fxml"));
            Stage stage = (Stage) submitBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Login");
            alert.setHeaderText("Invalid Login");
            alert.setContentText("Incorrect Password or UserID");
            alert.show();
            passwordTF.clear();
            passwordTF1.clear();
            userIDTF.clear();
        }
    }

    @FXML
    private void showPasswordOnClick(ActionEvent e) {
        if (showPassword.isSelected()) {
            passwordTF1.setText(passwordTF.getText());
            passwordTF.setVisible(false);
            passwordTF1.setVisible(true);
        } else {
            passwordTF.setText(passwordTF1.getText());
            passwordTF.setVisible(true);
            passwordTF1.setVisible(false);
        }
    }

    @FXML
    private void scenePaneOnPressed(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            submitBtnOnClick(new ActionEvent());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Scanner sc = new Scanner(new File("src/sample/Model/Resource/Data Files/Logo.csv"));
            if (sc.hasNextLine()) {
                iconFile = sc.nextLine();
            }
        } catch (FileNotFoundException e) {

        }
        if (SettingController.dark == true) {
            submitBtn.setStyle("");
            iconPane.setStyle("-fx-background-color: #353535");
            sidePane.getStylesheets().add(Main.class.getResource("View/dark.css").toExternalForm());
            sidePane.setStyle("-fx-background-color: #252525");
            bigPane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
            bigPane.setStyle("-fx-background-color: #303030");
            if (iconFile.equals("")) {
                image.setImage(new Image("file:src/sample/View/darkLogo.png"));
            } else {
                image.setImage(new Image("file:" + iconFile));
            }
        } else {
            if (iconFile.equals("")) {
                image.setImage(new Image("file:src/sample/View/logo.png"));
            } else {
                image.setImage(new Image("file:" + iconFile));
            }
            submitBtn.setStyle("-fx-border-color: #111");
        }
    }
}
