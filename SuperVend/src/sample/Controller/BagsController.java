package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import sample.Main;
import sample.Model.Product;
import sample.Model.ShoppingCart;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BagsController implements Initializable {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane mainPane;
    private ShoppingCart cart = MainController.shoppingCart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane.getStylesheets().add(Main.class.getResource("View/noPane.css").toExternalForm());
        if (SettingController.dark) {
            mainPane.getStylesheets().add(Main.class.getResource("View/darkMain.css").toExternalForm());
            mainPane.setStyle("-fx-background-color: #303030");
        } else {

        }
        ArrayList<ArrayList<Product>> bags = cart.bagging();

        for (int i = 1; i <= bags.size(); i++) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefHeight(71);
            anchorPane.setMaxHeight(71);
            anchorPane.setMinHeight(71);
            Label label = new Label();
            String s = "Bag " + i + ": ";
            for (int j = 0; j < bags.get(i-1).size(); j++) {
                if (j!=0) {
                    s += ", ";
                }

                s += bags.get(i-1).get(j).getName();
            }
            label.setText(s);
            anchorPane.getChildren().add(label);
            label.setLayoutX(14);
            label.setLayoutY(17);
            label.setFont(new Font("Segoe UI SemiBold", 18));
            label.setMinWidth(1200);
            gridPane.add(anchorPane, 0, i);
        }

        cart.updateTransaction();
    }
}
