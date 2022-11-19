package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.Product;
import sample.Model.ShoppingCart;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    @FXML
    private AnchorPane sidePane;
    @FXML
    private JFXButton settingsBtn;
    @FXML
    private JFXButton exitCartBtn;
    @FXML
    private AnchorPane iconPane;
    @FXML
    private ImageView allItems;
    @FXML
    private JFXTextField searchTF;
    @FXML
    private ImageView logo;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private JFXButton logoutBtn;
    @FXML
    private JFXButton clearBtn;
    @FXML
    private JFXButton checkoutBtn;
    @FXML
    private Label total;
    @FXML
    private JFXButton removeBtn;
    @FXML
    private AnchorPane mainPane;
    private static ShoppingCart cart = MainController.shoppingCart;
    private ArrayList<CartTileConstructor> tiles = new ArrayList<>();

    @FXML
    private void exitCartBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/main.fxml"));
        Stage stage = (Stage) settingsBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void clearBtnOnClick(ActionEvent event) {
        cart.clearAll();
        gridPane.getChildren().clear();
        total.setText("");
    }

    @FXML
    private void logoutBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/sample.fxml"));
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        Controller.security.nullinator();
        cart.clearAll();
        searchTF.clear();
    }

    @FXML
    private void searchTFOnReleased(KeyEvent event) {
        String text = searchTF.getText();
        gridPane.getChildren().clear();
        ArrayList<ArrayList<Object>> products = new ArrayList<>();
        for (Product product: cart.getShoppingCart()) {
            boolean in = false;

            for (ArrayList<Object> prod: products) {
                if (((Product) prod.get(0)).getProductID().equals(product.getProductID())) {
                    prod.set(1, ((int)prod.get(1)) + 1);
                    in = true;
                    break;
                }
            }

            if (!in) {
                ArrayList<Object> p = new ArrayList<>();
                p.add(product);
                p.add(1);
                products.add(p);
            }
        }

        int row = 0;
        for (int i = 0; i < products.size(); i++) {
            try{

                Product product = ((Product)products.get(i).get(0));
                if (product.getProductID().toLowerCase().contains(text.toLowerCase()) || product.getName().toLowerCase().contains(text.toLowerCase())) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(Main.class.getResource("View/cartTile.fxml"));

                    AnchorPane anchorPane = fxmlLoader.load();
                    CartTileConstructor tileController = fxmlLoader.getController();
                    tileController.setPublicTile(product, ((int)products.get(i).get(1)));
                    tiles.add(tileController);
                    tileController.amtTf.textProperty().addListener(new InvalidationListener() {
                        @Override
                        public void invalidated(Observable observable) {
                            total.setText(String.format("%10.2f", cart.sum()));
                        }
                    });
                    gridPane.add(anchorPane, 0, row++);
                }
            } catch (IOException e) {
                System.out.println("File Error");
            }
        }

    }

    @FXML
    private void settingsBtnOnClick(ActionEvent event) throws IOException {
        SettingMainController.setFile("View/cart.fxml");
        Parent root = FXMLLoader.load(Main.class.getResource("View/settingMain.fxml"));
        Stage stage = (Stage) settingsBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCart();
        total.setText(String.format("%6.2f", cart.sum()));
        if (SettingController.dark == true) {
            logoutBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
            removeBtn.setStyle("");
            checkoutBtn.setStyle("");
            clearBtn.setStyle("");
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
            clearBtn.setStyle("-fx-border-color: #111");
            checkoutBtn.setStyle("-fx-border-color: #111");
            removeBtn.setStyle("-fx-border-color: #111");
        }
        scrollPane.getStylesheets().add(Main.class.getResource("View/noPane.css").toExternalForm());
    }

    @FXML
    private void checkoutBtnOnClick(ActionEvent event) throws IOException {
        if (cart.getShoppingCart().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cart Is Empty");
            alert.setHeaderText("Cart Is Empty");
            alert.setContentText("Shopping Cart Contains No Products");
            alert.show();
        } else {
            Parent root = FXMLLoader.load(Main.class.getResource("View/transaction.fxml"));
            Stage stage = (Stage) settingsBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }

    @FXML
    private void removeBtnOnClick(ActionEvent event) {
        removeBtn.setVisible(false);
        for (CartTileConstructor ctc :tiles) {
            if (ctc.selected) {
                cart.remove(ctc.product);
            }
        }
        gridPane.getChildren().clear();
        setCart();
        total.setText(String.format("%6.2f", cart.sum()));
    }

    private void setCart() {
        ArrayList<ArrayList<Object>> products = new ArrayList<>();
        for (Product product: cart.getShoppingCart()) {
            boolean in = false;

            for (ArrayList<Object> prod: products) {
                if (((Product) prod.get(0)).getProductID().equals(product.getProductID())) {
                    prod.set(1, ((int)prod.get(1)) + 1);
                    in = true;
                    break;
                }
            }

            if (!in) {
                ArrayList<Object> p = new ArrayList<>();
                p.add(product);
                p.add(1);
                products.add(p);
            }
        }

        int row = 0;
        for (int i = 0; i < products.size(); i++) {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("View/cartTile.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                CartTileConstructor tileController = fxmlLoader.getController();
                tileController.setPublicTile(((Product)products.get(i).get(0)), ((int)products.get(i).get(1)));
                tiles.add(tileController);
                tileController.amtTf.textProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        total.setText(String.format("%6.2f", cart.sum()));
                    }
                });
                gridPane.add(anchorPane, 0, row++);
            } catch (IOException e) {
                System.out.println("File Error");
            }
        }
    }

    @FXML
    private void scrollPaneOnClick(MouseEvent event) {
        removeBtn.setVisible(false);
        for (CartTileConstructor tileController: tiles) {
            if (tileController.selected) {
                removeBtn.setVisible(true);
            }
        }
    }
}

