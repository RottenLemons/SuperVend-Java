package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.Inventory;
import sample.Model.Product;
import sample.Model.ProductCategory;
import sample.Model.Products;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AdminProController implements Initializable {
    @FXML
    private AnchorPane sidePane;

    @FXML
    private JFXButton adminBtn;

    @FXML
    private AnchorPane iconPane;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView image;

    @FXML
    private ImageView prevIcon;

    @FXML
    private ImageView nextIcon;

    @FXML
    private JFXButton prevPicBtn;

    @FXML
    private JFXButton nextPicBtn;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton uploadBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXTextField quantityTF;

    @FXML
    private JFXTextField itemTF;

    @FXML
    private JFXTextArea descTA;

    @FXML
    private JFXTextField brandTF;

    @FXML
    private JFXTextField priceTF;

    @FXML
    private JFXTextField tempTF;

    @FXML
    private JFXTextField sizeTF;

    @FXML
    private JFXTextField countryTF;

    @FXML
    private JFXTextField dateTF;

    @FXML
    private JFXTextField weightTF;
    @FXML
    private JFXButton deletePro;
    @FXML
    private JFXTextField idTF;
    private ArrayList<String> images;
    private Product product;
    private int pos = 1;
    private Products products = new Products("src/sample/Model/Resource/Data Files/Product.csv");
    private Inventory inventory = new Inventory("src/sample/Model/Resource/Data Files/ProductCode.csv", "src/sample/Model/Resource/Data Files/Product.csv", "src/sample/Model/Resource/Data Files/Inventory.csv");

    @FXML
    private void deleteBtnOnClick(ActionEvent event) {
        if (images.size() - 1 == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Must Have 1 Pic");
            alert.setHeaderText("Must Have 1 Pic");
            alert.setContentText("Cannot Delete More Pics");
            alert.show();
        } else {
            images.remove(pos-1);
            image.setImage(new Image("file:" + images.get(images.size() - 1)));
            if (pos - 1 == 0) {
                pos = 1;
            } else {
                pos -= 1;
            }
        }

        if (images.size() == 1) {
            nextPicBtn.setVisible(false);
            prevPicBtn.setVisible(false);
            nextIcon.setVisible(false);
            prevIcon.setVisible(false);
        }
    }

    @FXML
    private void editBtnOnClick(ActionEvent event) throws ParseException {
        boolean error = false;
        String alertText = "";

        if (itemTF.getText().length() > 36) {
            alertText += "Name More Than 36 Chars\n";
            error = true;
            itemTF.clear();
        }

        if (descTA.getText().length() > 170) {
            alertText += "Desc More Than 170 Chars\n";
            error = true;
            descTA.clear();
        }

        boolean pc = false;
        if (idTF.getText().length() < 2) {

        } else {
            for (ProductCategory s:inventory.getCats()) {
                if (s.getCode().equals(idTF.getText().substring(0,2))) {
                    pc = true;
                    break;
                }
            }
        }

        if (!pc || !idTF.getText().matches("^[A-Z]{0,3}[\\d]{5}$")) {
            alertText += "Product Code Fails\n";
            error = true;
            idTF.clear();
        }

        if (!priceTF.getText().matches("^[\\d]+[.][\\d]+$") || Double.parseDouble(priceTF.getText()) > 50) {
            alertText += "Price Not Right\n";
            error = true;
            priceTF.clear();
        }

        if (!tempTF.getText().matches("^(-)?[\\d]+")) {
            alertText += "Temp Not Right\n";
            error = true;
            tempTF.clear();
        }

        if (!sizeTF.getText().matches("^(M|L|S)$")) {
            alertText += "Size Not Right\n";
            error = true;
            sizeTF.clear();
        }

        if (!dateTF.getText().matches("^[\\d]{2}-[\\d]{2}-[\\d]{4}$")) {
            alertText += "Date Not Right\n";
            error = true;
            dateTF.clear();
        } else {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                format.setLenient(false);
                format.parse(dateTF.getText());
            } catch (ParseException e) {
                alertText += "Date Not Possible\n";
                error = true;
                dateTF.clear();
            }
        }

        if (!weightTF.getText().matches("^[\\d]+[.][\\d]+$")) {
            alertText += "Weight Not Possible/Right\n";
            error = true;
            weightTF.clear();
        }

        if (!quantityTF.getText().matches("^[\\d]+$")) {
            alertText += "Quantity Not Possible\n";
            error = true;
            quantityTF.clear();
        }

        if (!error) {
            try {
                products.edit(product, idTF.getText(), itemTF.getText(), descTA.getText(), brandTF.getText(), Double.parseDouble(priceTF.getText()), Integer.parseInt(tempTF.getText()), sizeTF.getText(), countryTF.getText(), dateTF.getText(), Double.parseDouble(weightTF.getText()), images);
                inventory.updateQuantity(product.getProductID(), idTF.getText(), Integer.parseInt(quantityTF.getText()));
                Parent root = FXMLLoader.load(Main.class.getResource("View/adminMain.fxml"));
                Stage stage = (Stage) editBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (ParseException | IOException parseException) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ParseException");
                alert.setHeaderText("ParseException");
                alert.setContentText("Input Correctly");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(alertText);
            alert.setTitle("Input Error");
            alert.setHeaderText("Input Error");
            alert.show();
        }
    }

    @FXML
    private void uploadBtnOnClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );
        fileChooser.setTitle("Add Image");
        List<File> file = fileChooser.showOpenMultipleDialog(uploadBtn.getScene().getWindow());
        if (file != null && !file.isEmpty()) {
            for (File files: file) {
                images.add(files.getAbsolutePath());
            }
            image.setImage(new Image("file:" + images.get(images.size() - 1)));
            pos = images.size();
            if (images.size() > 1) {
                nextPicBtn.setVisible(true);
                prevPicBtn.setVisible(true);
                nextIcon.setVisible(true);
                prevIcon.setVisible(true);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Image Added");
            alert.setHeaderText("No Image Added");
            alert.setContentText("No Image Selected and Added");
            alert.show();
        }
    }

    @FXML
    private void adminBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/adminMain.fxml"));
        Stage stage = (Stage) editBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (SettingController.dark == true) {
            iconPane.setStyle("-fx-background-color: #353535");
            sidePane.getStylesheets().add(Main.class.getResource("View/dark.css").toExternalForm());
            sidePane.setStyle("-fx-background-color: #252525");
            editBtn.setStyle("");
            deletePro.setStyle("");
            mainPane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
            mainPane.setStyle("-fx-background-color: #3d3d3d");
            nextPicBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
            prevPicBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
            uploadBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
            deleteBtn.setStyle("-fx-background-color: transparent; -fx-border-color: null");
        } else {
            editBtn.setStyle("-fx-border-color: #111");
            nextPicBtn.setStyle("");
            prevPicBtn.setStyle("");
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

        quantityTF.setText(quantity + "");
        idTF.setText(product.getProductID());
        itemTF.setText(product.getName());
        descTA.setText(product.getDescription());
        brandTF.setText(product.getBrand());
        priceTF.setText(product.getPrice() + "");
        tempTF.setText(product.getTemp() + "");
        sizeTF.setText(product.getSize());
        countryTF.setText(product.getCountry());
        dateTF.setText(product.getExpiry());
        weightTF.setText(product.getWeight() + "");

        images = product.getImages();
        if (images.size() <= 1) {
            nextPicBtn.setVisible(false);
            nextIcon.setVisible(false);
            prevPicBtn.setVisible(false);
            prevIcon.setVisible(false);
        }
        image.setImage(new Image("file:" + images.get(0)));
    }

    @FXML
    private void deleteProOnClick(ActionEvent event) throws IOException {
        products.delete(product.getProductID());
        inventory.delete(product.getProductID());
        Parent root = FXMLLoader.load(Main.class.getResource("View/adminMain.fxml"));
        Stage stage = (Stage) editBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
