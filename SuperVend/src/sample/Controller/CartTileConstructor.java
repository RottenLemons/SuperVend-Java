package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.Model.Inventory;
import sample.Model.Product;
import sample.Model.ShoppingCart;

import java.net.URL;
import java.util.ResourceBundle;

public class CartTileConstructor implements Initializable {

    @FXML
    private AnchorPane tilePane;
    @FXML
    private ImageView itemImage;
    @FXML
    private Label itemName;
    @FXML
    private Label itemPrice;
    @FXML
    private Label productID;
    @FXML
    public JFXTextField amtTf;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton removeBtn;
    boolean selected = false;
    public Product product;
    private ShoppingCart cart = MainController.shoppingCart;
    private Inventory inventory = new Inventory("src/sample/Model/Resource/Data Files/ProductCode.csv", "src/sample/Model/Resource/Data Files/Product.csv", "src/sample/Model/Resource/Data Files/Inventory.csv");

    @FXML
    private void amtBtnOnClick(ActionEvent event) {
        if (event.getSource().equals(addBtn)) {
            if (cart.getShoppingCart().size() + 1 <= 10 && Integer.parseInt(amtTf.getText()) + 1 <= inventory.getQuantity(product)) {
                cart.add(product);
                amtTf.setText(((Integer.parseInt(amtTf.getText()) + 1)) + "");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Amount Error");
                alert.setHeaderText("Amount Error");
                alert.setContentText("Cannot Add More Products");
                alert.show();
            }
        } else {
            if (Integer.parseInt(amtTf.getText()) - 1 > 0) {
                cart.delete(product);
                amtTf.setText(((Integer.parseInt(amtTf.getText()) - 1)) + "");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Remove Product");
                alert.setHeaderText("Remove Product");
                alert.setContentText("To Remove Product\nClick On The Product\nAnd Use The Remove Button");
                alert.show();
            }
        }
    }

    @FXML
    private void mouseEnteredTile(MouseEvent event) {
        if (!selected) {
            if (SettingController.dark == true) {
                tilePane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
                tilePane.setStyle("-fx-background-color: #299476;-fx-border-color: #eee");
            } else {
                tilePane.setStyle("-fx-background-color: #cacaca; -fx-border-color: #000");
            }
        }
    }

    @FXML
    private void mouseExitedTile(MouseEvent event) {
        if (!selected) {
            if (SettingController.dark == true) {
                tilePane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
                tilePane.setStyle("-fx-background-color: #3d3d3d;-fx-border-color: #eee");
            } else {
                tilePane.setStyle("-fx-background-color: #fff; -fx-border-color: #000");
            }
        }
    }

    @FXML
    private void tilePaneOnClick(MouseEvent event) {
        if (selected) {
            selected = false;
            if (SettingController.dark == true) {
                tilePane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
                tilePane.setStyle("-fx-background-color: #2c7c46;-fx-border-color: #eee");
            } else {
                tilePane.setStyle("-fx-background-color: #cacaca; -fx-border-color: #000");
            }
        } else {
            selected = true;
            if (SettingController.dark == true) {
                tilePane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
                tilePane.setStyle("-fx-background-color: #0c6fa2;-fx-border-color: #eee");
            } else {
                tilePane.setStyle("-fx-background-color: #138CD8; -fx-border-color: #000");
            }
        }
    }

    public void setPublicTile(Product product, int amount) {
        this.product = product;
        itemName.setText(product.getName());
        itemPrice.setText(String.format("$%.2f", product.getPrice()));
        productID.setText(product.getProductID());
        itemImage.setImage(new Image("file:" + product.getImages().get(0)));
        amtTf.setText(amount + "");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (SettingController.dark == true) {
            tilePane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
            tilePane.setStyle("-fx-background-color: #3d3d3d;-fx-border-color: #eee");
            addBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
            removeBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
        } else {
            addBtn.setStyle("");
            removeBtn.setStyle("");
        }
    }
}
