package sample.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class TileController implements Initializable {
    @FXML
    private ImageView itemImage;
    @FXML
    private Label itemName;
    @FXML
    private Label itemPrice;
    @FXML
    private Label itemQuantity;
    @FXML
    private JFXButton addToCartBtn;
    @FXML
    private Label productID;
    @FXML
    private AnchorPane tilePane;
    private Product product;

    @FXML
    private void addToCartOnClick(ActionEvent event) {
        try {
            MainController.shoppingCart.add(product);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success");
            alert.setTitle("Success");
            alert.setContentText("Product Added To Cart");
            alert.show();
        } catch (ArrayStoreException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cart Is Full");
            alert.setTitle("Cart Is Full");
            alert.setContentText("Cannot Add More To Cart");
            alert.show();
        }
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
            addToCartBtn.setStyle("");
            tilePane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
            tilePane.setStyle("-fx-background-color: #252525; -fx-border-color: #eee");
        } else {
            addToCartBtn.setStyle("-fx-border-color: #111");
            tilePane.setStyle("-fx-background-color: #fff; -fx-border-color: #000");
        }
    }

    @FXML
    private void tilePaneOnClick(MouseEvent event) throws IOException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        tilePane.setStyle("-fx-background-color: #fff; -fx-border-color: #000");
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Thread Error");
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        tilePane.setStyle("-fx-background-color: #138CD8; -fx-border-color: #000");
                    }
                });
            }
        });
        thread.start();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("View/details.fxml"));

        AnchorPane anchorPane = fxmlLoader.load();
        DetailsController detailsController = fxmlLoader.getController();
        detailsController.setDetails(product, Integer.parseInt(itemQuantity.getText()));
        Stage stage = new Stage();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle(product.getName());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (SettingController.dark == true) {
            addToCartBtn.setStyle("");
            tilePane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
            tilePane.setStyle("-fx-background-color: #252525; -fx-border-color: #eee");
        } else {
            addToCartBtn.setStyle("-fx-border-color: #111");
        }
    }
}
