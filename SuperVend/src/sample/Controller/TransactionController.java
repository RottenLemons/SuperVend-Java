package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.ShoppingCart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    @FXML
    private AnchorPane sidePane;

    @FXML
    private JFXButton settingsBtn;

    @FXML
    private JFXButton cartBtn;

    @FXML
    private AnchorPane iconPane;

    @FXML
    private ImageView allItems;

    @FXML
    private JFXTextField amountTF;

    @FXML
    private ImageView logo;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private Label totalLabel;

    @FXML
    private Label changeLabel;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private JFXButton paymentBtn;
    private ShoppingCart cart = MainController.shoppingCart;

    @FXML
    private void cartBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/cart.fxml"));
        Stage stage = (Stage) settingsBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void logoutBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/sample.fxml"));
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        Controller.security.nullinator();
        cart.clearAll();
    }

    @FXML
    private void paymentOnClick(ActionEvent event) throws IOException {
        if (changeLabel.getText().equals("") || Double.parseDouble(changeLabel.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Amount Is Insufficient");
            alert.setHeaderText("Amount Is Insufficient");
            alert.setContentText("Money Given Not Enough");
            alert.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("View/bags.fxml"));

            AnchorPane anchorPane = fxmlLoader.load();
            BagsController bagsController = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Bags");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
            stage.show();

            Parent root = FXMLLoader.load(Main.class.getResource("View/main.fxml"));
            Stage stage1 = (Stage) settingsBtn.getScene().getWindow();
            stage1.setScene(new Scene(root));
        }
    }


    @FXML
    private void amountTFOnReleased(KeyEvent event) {
        try {
            if (amountTF.getText().equals("")) {
                changeLabel.setText("");
            } else {
                double number = Double.parseDouble(amountTF.getText());
                changeLabel.setText(String.format("%10.2f",cart.returnCash(number)));
            }
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Input Error");
            alert.setContentText("Double not entered");
            alert.show();
            amountTF.clear();
            changeLabel.setText("");
        }
    }

    @FXML
    private void settingsBtnOnClick(ActionEvent event) throws IOException {
        SettingMainController.setFile("View/transaction.fxml");
        Parent root = FXMLLoader.load(Main.class.getResource("View/settingMain.fxml"));
        Stage stage = (Stage) settingsBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        totalLabel.setText(String.format("%10.2f", cart.sum()));
        if (SettingController.dark == true) {
            logoutBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
            paymentBtn.setStyle("");
            iconPane.setStyle("-fx-background-color: #353535");
            sidePane.getStylesheets().add(Main.class.getResource("View/dark.css").toExternalForm());
            sidePane.setStyle("-fx-background-color: #252525");
            mainPane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
            mainPane.setStyle("-fx-background-color: #303030");
            if (Controller.iconFile.equals("")) {
                logo.setImage(new Image("file:src/sample/View/darkLogo.png"));
            } else {
                logo.setImage(new Image("file:" + Controller.iconFile));
            }
        } else {
            if (Controller.iconFile.equals("")) {
                logo.setImage(new Image("file:src/sample/View/logo.png"));
            } else {
                logo.setImage(new Image("file:" + Controller.iconFile));
            }
            paymentBtn.setStyle("-fx-border-color: #111");
        }
    }
}
