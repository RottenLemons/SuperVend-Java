package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.Inventory;
import sample.Model.Product;
import sample.Model.ProductCategory;
import sample.Model.ShoppingCart;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private AnchorPane sidePane;
    @FXML
    private AnchorPane iconPane;
    @FXML
    private JFXButton settingsBtn;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private JFXButton logoutBtn;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView allItems;
    @FXML
    private JFXButton allItemsBtn;
    @FXML
    private JFXTextField searchTF;
    @FXML
    private JFXButton uploadBtn;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private JFXButton addProBtn;
    @FXML
    private ImageView upload;
    @FXML
    private ImageView plusImg;
    private JFXButton editBtn;
    private boolean btnClicked = false;
    private ArrayList<Product> products;
    private String filename = null;

    private ArrayList<JFXButton> productCat = new ArrayList<>(3);
    private Inventory inventory = new Inventory("src/sample/Model/Resource/Data Files/ProductCode.csv", "src/sample/Model/Resource/Data Files/Product.csv", "src/sample/Model/Resource/Data Files/Inventory.csv");
    public static ShoppingCart shoppingCart = new ShoppingCart("src/sample/Model/Resource/Data Files/Transaction.csv");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProducts();
        if (SettingController.dark) {
            iconPane.setStyle("-fx-background-color: #353535");
            sidePane.getStylesheets().add(Main.class.getResource("View/dark.css").toExternalForm());
            sidePane.setStyle("-fx-background-color: #252525");
            mainPane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
            mainPane.setStyle("-fx-background-color: #303030");
            upload.setImage(new Image("file:src/sample/View/darkUpload.png"));
            if (Controller.iconFile.equals("")) {
                logo.setImage(new Image("file:src/sample/View/darkLogo.png"));
            } else {
                logo.setImage(new Image("file:" + Controller.iconFile));
            }
            logoutBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
            addProBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
            uploadBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
            scrollPane.getStylesheets().add(Main.class.getResource("View/noPane.css").toExternalForm());
        } else {
            if (Controller.iconFile.equals("")) {
                logo.setImage(new Image("file:src/sample/View/logo.png"));
            } else {
                logo.setImage(new Image("file:" + Controller.iconFile));
            }
            upload.setImage(new Image("file:src/sample/View/lightUpload.png"));
            scrollPane.getStylesheets().add(Main.class.getResource("View/noPane.css").toExternalForm());

        }

        for (int i = 0; i < inventory.getCats().size(); i++) {
            JFXButton button = new JFXButton(inventory.getCats().get(i).getName());
            button.setPrefSize(170, settingsBtn.getPrefHeight());
            button.setLayoutX(51);
            button.setLayoutY(0 + i * settingsBtn.getPrefHeight());
            button.setFont(new Font("Segoe UI Light", 18));
            int k = i;
            button.setOnAction((ActionEvent ex) -> {
                addProBtn.setVisible(false);
                plusImg.setVisible(false);
                btnClicked = true;
                products = new ArrayList<>();
                gridPane.getChildren().clear();
                int row1 = 0;

                try{
                    for (Product product: inventory.getCats().get(k).getProdCat()) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(Main.class.getResource("View/adminTile.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        AdminTileController tileController = fxmlLoader.getController();
                        tileController.setTile(product ,inventory.getQuantity(product));
                        gridPane.add(anchorPane, 0, row1++);
                        products.add(product);
                    }
                } catch (IOException e) {
                    System.out.println("File Error");
                }


                for (JFXButton jfxButton: productCat) {
                    if (SettingController.dark) {
                        jfxButton.setStyle("-fx-background-color: #252525");
                    } else {
                        jfxButton.setStyle("-fx-background-color: #eee");
                    }
                }

                if (SettingController.dark) {
                    button.setStyle("-fx-background-color: #2c7c46");
                } else {
                    button.setStyle("-fx-background-color: #48b469");
                }
                allItems.setVisible(true);
                allItemsBtn.setVisible(true);
                searchTF.clear();

            });

            sidePane.getChildren().add(button);
            productCat.add(button);
        }

        ImageView imageView = new ImageView(new Image("file:src/sample/View/edit.png"));
        imageView.setFitHeight(39);
        imageView.setFitWidth(39);
        editBtn = new JFXButton();
        editBtn.setStyle("-fx-background-color: transparent");
        imageView.setLayoutX(120);
        editBtn.setLayoutX(120);
        imageView.setLayoutY(productCat.get(productCat.size()-1).getLayoutY() + 2 + settingsBtn.getPrefHeight());
        editBtn.setLayoutY(productCat.get(productCat.size()-1).getLayoutY() + 2 + settingsBtn.getPrefHeight());
        editBtn.setPrefSize(39, 39);
        editBtn.setOnAction((ActionEvent e) -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(Main.class.getResource("View/addCode.fxml"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Stage stage = (Stage) settingsBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
        });
        sidePane.getChildren().addAll(imageView, editBtn);
    }

    @FXML
    private void settingsBtnOnClick(ActionEvent ex) throws IOException {
        SettingMainController.setFile("View/adminMain.fxml");
        Parent root = FXMLLoader.load(Main.class.getResource("View/settingMain.fxml"));
        Stage stage = (Stage) settingsBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void logoutBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/sample.fxml"));
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        Controller.security.nullinator();
        shoppingCart.clearAll();
        searchTF.clear();
    }

    @FXML
    private void allItemsBtnOnClick(ActionEvent event) {
        allItemsBtn.setVisible(false);
        allItems.setVisible(false);
        addProBtn.setVisible(true);
        plusImg.setVisible(true);

        for (JFXButton jfxButton: productCat) {
            if (SettingController.dark) {
                jfxButton.setStyle("-fx-background-color: #252525");
            } else {
                jfxButton.setStyle("-fx-background-color: #eee");
            }
        }

        gridPane.getChildren().clear();
        setProducts();
        searchTF.clear();
        btnClicked = false;
    }

    private void setProducts() {
        int row = 0;
        for (int i = 0; i < inventory.getInventoryList().size(); i++) {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("View/adminTile.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                AdminTileController tileController = fxmlLoader.getController();
                ProductCategory productCategory = inventory.getProductCode(((String)inventory.getInventoryList().get(i).get(0)).substring(0,2));
                tileController.setTile(productCategory.getProduct(((String)inventory.getInventoryList().get(i).get(0))) ,(int) inventory.getInventoryList().get(i).get(1));
                gridPane.add(anchorPane, 0, row++);
            } catch (IOException e) {
                System.out.println("File Error");
            }
        }
    }

    @FXML
    private void searchTFOnReleased(KeyEvent event) {
        if (!btnClicked) {
            String text = searchTF.getText();
            gridPane.getChildren().clear();
            int row = 0;
            for (int i = 0; i < inventory.getInventoryList().size(); i++) {
                try{
                    String id = ((String)inventory.getInventoryList().get(i).get(0));
                    Product product = inventory.getProductCode(id.substring(0,2)).getProduct(id);
                    if (id.toLowerCase().contains(text.toLowerCase()) || product.getName().toLowerCase().contains(text.toLowerCase())) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(Main.class.getResource("View/adminTile.fxml"));

                        AnchorPane anchorPane = fxmlLoader.load();
                        AdminTileController tileController = fxmlLoader.getController();
                        tileController.setTile(product,(int) inventory.getInventoryList().get(i).get(1));
                        gridPane.add(anchorPane, 0, row++);
                    }
                } catch (IOException e) {
                    System.out.println("File Error");
                }
            }
        } else {
            String text = searchTF.getText();
            gridPane.getChildren().clear();
            int row = 0;
            for (int i = 0; i < products.size(); i++) {
                try {
                    Product product = products.get(i);
                    if (product.getProductID().toLowerCase().contains(text.toLowerCase()) || product.getName().toLowerCase().contains(text.toLowerCase())) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(Main.class.getResource("View/adminTile.fxml"));

                        AnchorPane anchorPane = fxmlLoader.load();
                        AdminTileController tileController = fxmlLoader.getController();
                        tileController.setTile(product, (int) inventory.getInventoryList().get(i).get(1));
                        gridPane.add(anchorPane, 0, row++);
                    }
                } catch (IOException e) {
                    System.out.println("File Error");
                }
            }
        }
    }

    @FXML
    private void uploadBtnOnClick(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );
        fileChooser.setTitle("Set Logo");
        File file = fileChooser.showOpenDialog(uploadBtn.getScene().getWindow());
        if (file != null && file.exists()) {
            filename = file.getAbsolutePath();
            PrintWriter printWriter = new PrintWriter(new FileWriter("src/sample/Model/Resource/Data Files/Logo.csv"));
            printWriter.println(filename);
            printWriter.close();
            logo.setImage(new Image("file:" + filename));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Image Set");
            alert.setHeaderText("No Image Set");
            alert.setContentText("No Image Selected and Set");
            alert.show();
        }
    }

    private @FXML
    void addProBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/addPro.fxml"));
        Stage stage = (Stage) settingsBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
