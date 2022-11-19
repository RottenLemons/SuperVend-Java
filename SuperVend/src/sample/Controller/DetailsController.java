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
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.Model.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {
    @FXML
    private Label name;
    @FXML
    private Label brand;
    @FXML
    private Label price;
    @FXML
    private Label description;
    @FXML
    private Label quantity;
    @FXML
    private Label temp;
    @FXML
    private Label size;
    @FXML
    private Label country;
    @FXML
    private Label expiryDate;
    @FXML
    private Label netWeight;
    @FXML
    private JFXButton addToCart;
    @FXML
    private JFXTextField amountTF;
    @FXML
    private JFXButton addQty;
    @FXML
    private JFXButton removeQty;
    @FXML
    private JFXButton prevPicBtn;
    @FXML
    private JFXButton nextPicBtn;
    @FXML
    private ImageView image;
    @FXML
    private ImageView prevIcon;
    @FXML
    private ImageView nextIcon;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label id;
    private int quantityNum;
    private ArrayList<String> images;
    private Product product;
    private int pos = 1;

    @FXML
    private void addToCartOnClick(ActionEvent event) {
        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(amountTF.getText()); i++) {
            products.add(product);
        }

        try {
            MainController.shoppingCart.add(products);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success");
            alert.setTitle("Success");
            alert.setContentText("Products Added To Cart");
            alert.show();
        } catch (ArrayStoreException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cart Is Full");
            alert.setTitle("Cart Is Full");
            alert.setContentText("Cannot Add More To Cart");
            alert.show();
        } finally {
            amountTF.setText(1 + "");
        }
    }

    @FXML
    private void qtyOnClick(ActionEvent event) {
        int amount = Integer.parseInt(amountTF.getText());
        if (event.getSource().equals(addQty)) {
            if (amount + 1 <= quantityNum) {
                amountTF.setText((amount + 1) + "");
            } else {

            }
        } else {
            if (amount - 1 >= 1) {
                amountTF.setText((amount - 1) + "");
            } else {

            }
        }
    }

    @FXML
    private void picOnClick(ActionEvent event) {
        if (event.getSource().equals(nextPicBtn)) {
            if (pos == images.size()) {
                image.setImage(new Image("file:" + images.get(0)));
                pos = 1;
            } else {
                image.setImage(new Image("file:" + images.get(pos)));
                pos += 1;
            }
        } else {
            if (pos == 1) {
                image.setImage(new Image("file:" + images.get(images.size() - 1)));
                pos = images.size();
            } else {
                pos -= 1;
                image.setImage(new Image("file:" + images.get(pos - 1)));
            }
        }
    }

    public void setDetails(Product product, int quantity) {
        this.product = product;
        quantityNum = quantity;
        id.setText(product.getProductID());
        name.setText(product.getName());
        description.setText(product.getDescription());
        brand.setText(product.getBrand());
        price.setText(String.format("$%.2f", product.getPrice()));
        temp.setText(product.getTemp() + "");
        size.setText(product.getSize());
        country.setText(product.getCountry());
        expiryDate.setText(product.getExpiry());
        netWeight.setText(product.getWeight() + "g");
        this.quantity.setText(quantity + "");
        if (quantity == 0) {
            amountTF.setText(0 + "");
            addQty.setDisable(true);
            removeQty.setDisable(true);
            addToCart.setDisable(true);
        }
        images = product.getImages();
        if (images.size() <= 1) {
            nextPicBtn.setVisible(false);
            nextIcon.setVisible(false);
            prevPicBtn.setVisible(false);
            prevIcon.setVisible(false);
        }
        image.setImage(new Image("file:" + images.get(0)));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (SettingController.dark == true) {
            addToCart.setStyle("");
            mainPane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
            mainPane.setStyle("-fx-background-color: #3d3d3d");
            addQty.setStyle("-fx-background-color: transparent; -fx-border-color: null");
            removeQty.setStyle("-fx-background-color: transparent; -fx-border-color: null");
            nextPicBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
            prevPicBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
        } else {
            addToCart.setStyle("-fx-border-color: #111");
            addQty.setStyle("");
            removeQty.setStyle("");
            nextPicBtn.setStyle("");
            prevPicBtn.setStyle("");
        }
    }
}
