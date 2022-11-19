package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.*;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddCodeController implements Initializable {

    @FXML
    private AnchorPane sidePane;

    @FXML
    private JFXButton adminBtn;

    @FXML
    private AnchorPane iconPane;

    @FXML
    private TableColumn<ProCat, String> nameColumn;

    @FXML
    private TableColumn<ProCat, String> catColumn;

    @FXML
    private JFXTextField catName;

    @FXML
    private JFXTextField catCode;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton deleteBtn;
    @FXML
    private TableView<ProCat> table;

    ObservableList<ProCat> oldata = FXCollections.observableArrayList();
    ObservableList<ProCat> data = FXCollections.observableArrayList();
    private Products products = new Products("src/sample/Model/Resource/Data Files/Product.csv");
    private Inventory inventory = new Inventory("src/sample/Model/Resource/Data Files/ProductCode.csv", "src/sample/Model/Resource/Data Files/Product.csv", "src/sample/Model/Resource/Data Files/Inventory.csv");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameString"));
        catColumn.setCellValueFactory(new PropertyValueFactory<>("codeString"));

        table.setItems(data);

        for(int i=0;i< inventory.getCats().size();i++){
            data.add(new ProCat(inventory.getCats().get(i).getName(), inventory.getCats().get(i).getCode()));
            oldata.add(new ProCat(inventory.getCats().get(i).getName(), inventory.getCats().get(i).getCode()));
        }
        editBtn.setDisable(true);
        deleteBtn.setDisable(true);
        table.setItems(data);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if(table.getSelectionModel()!=null){
                    editBtn.setDisable(false);
                    deleteBtn.setDisable(false);
                    catColumn.setText(data.get(table.getSelectionModel().getSelectedIndex()).getCodeString());
                    nameColumn.setText(data.get(table.getSelectionModel().getSelectedIndex()).getNameString());
                }
            }
        });
    }

    @FXML
    private void addBtnOnClick(ActionEvent event) {
        if (!catCode.getText().matches("[A-Z]{2}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Input Not Valid");
            alert.setTitle("Input Not Valid");
            alert.setContentText("Code Should Be 2 Cap Letters");
            alert.show();
        }

        boolean in = false;
        for(ProCat proCat : data){
            if(catName.getText().equals(proCat.getNameString()) || catCode.getText().equals(proCat.getCodeString())){
                in = true;
            }
        }
        if (!in && data.size() + 1 <= 5) {
            data.add(new ProCat(catName.getText(), catCode.getText()));
            inventory.addCat(catName.getText(), catCode.getText());

        } else if (in) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Input Not Valid");
            alert.setTitle("Input Not Valid");
            alert.setContentText("Code Or Name Already Used");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cannot Add More");
            alert.setTitle("Cannot Add More");
            alert.setContentText("Cannot Add More Than Five");
            alert.show();
        }
    }

    @FXML
    private void adminBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/adminMain.fxml"));
        Stage stage = (Stage) addBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deleteBtnOnClick(ActionEvent event) {

        for (ArrayList<Object> arrayList: inventory.getInventoryList()) {
            if (((String)arrayList.get(1)).substring(0,2).equals(table.getSelectionModel().getSelectedItem().getCodeString())) {
                inventory.delete((String) arrayList.get(1));
                products.delete((String) arrayList.get(1));
            }
        }

        inventory.deleteCat(table.getSelectionModel().getSelectedItem().getCodeString());
        data.remove(table.getSelectionModel().getSelectedIndex());
        table.setItems(data);
    }

    @FXML
    private void editBtnOnClick(ActionEvent event) throws ParseException {
        data.set(table.getSelectionModel().getSelectedIndex(), new ProCat(catName.getText(),catCode.getText()));
        table.setItems(data);

        boolean in = false;
        for(ProCat proCat : data){
            if(catName.getText().equals(proCat.getNameString()) || catCode.getText().equals(proCat.getCodeString())){
                in = true;
            }
        }
        if (!in && data.size() + 1 <= 5) {
            table.setItems(data);
            for (ArrayList<Object> arrayList: inventory.getInventoryList()) {
                if (((String)arrayList.get(1)).substring(0,2).equals(table.getSelectionModel().getSelectedItem().getCodeString())) {
                    Product product = inventory.getProductCode(table.getSelectionModel().getSelectedItem().getCodeString()).getProduct((String) arrayList.get(1));
                    products.edit(product, (String)arrayList.get(1), product.getName(), product.getDescription(), product.getBrand(), product.getPrice(), product.getTemp(), product.getSize(), product.getCountry(), product.getExpiry(), product.getWeight(), product.getImages());
                }
            }
            data.set(table.getSelectionModel().getSelectedIndex(), new ProCat(catName.getText(),catCode.getText()));
            table.setItems(data);
            inventory.editCat(catName.getText(), catCode.getText());
        } else if (in) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Input Not Valid");
            alert.setTitle("Input Not Valid");
            alert.setContentText("Code Or Name Already Used");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cannot Add More");
            alert.setTitle("Cannot Add More");
            alert.setContentText("Cannot Add More Than Five");
            alert.show();
        }
    }
}
