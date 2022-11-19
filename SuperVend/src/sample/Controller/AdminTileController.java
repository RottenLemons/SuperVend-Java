package sample.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminTileController implements Initializable {
    @FXML
    private ImageView itemImage;
    @FXML
    private Label itemName;
    @FXML
    private Label itemPrice;
    @FXML
    private Label itemQuantity;
    @FXML
    private JFXButton editBtn;
    @FXML
    private Label productID;
    @FXML
    private AnchorPane tilePane;
    private Product product;

    @FXML
    private void editBtnOnClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("View/adminPro.fxml"));

        AnchorPane anchorPane = fxmlLoader.load();
        AdminProController detailsController = fxmlLoader.getController();
        detailsController.setDetails(product, Integer.parseInt(itemQuantity.getText()));
        ((Stage) editBtn.getScene().getWindow()).setScene(new Scene(anchorPane));
    }

    public void setTile(Product product, int quantity) {
        this.product = product;
        itemName.setText(product.getName());
        itemPrice.setText(String.format("$%.2f", product.getPrice()));
        itemQuantity.setText(quantity + "");
        productID.setText(product.getProductID());
        itemImage.setImage(new Image("file:" + product.getImages().get(0)));
    }

    @FXML
    private void mouseEnteredTile(MouseEvent event) {
        if (SettingController.dark) {
            tilePane.setStyle("-fx-background-color: #0c6fa2; -fx-border-color: #eee");
        } else {
            tilePane.setStyle("-fx-background-color: #138CD8; -fx-border-color: #000");
        }
    }

    @FXML
    private void mouseExitedTile(MouseEvent event) {
        if (SettingController.dark == true) {
            editBtn.setStyle("");
            tilePane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
            tilePane.setStyle("-fx-background-color: #252525; -fx-border-color: #eee");
        } else {
            editBtn.setStyle("-fx-border-color: #111");
            tilePane.setStyle("-fx-background-color: #fff; -fx-border-color: #000");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (SettingController.dark == true) {
            editBtn.setStyle("");
            tilePane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
            tilePane.setStyle("-fx-background-color: #252525; -fx-border-color: #eee");
        } else {
            editBtn.setStyle("-fx-border-color: #111");
        }
    }
}
