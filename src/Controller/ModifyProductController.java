package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.ResourceBundle;

import static Model.Inventory.updateProduct;


/** This class is the controller for the Modify Product form. I experienced a logical error in this class. The class was somehow excluded from the package. The project was able to run but when the modify product button was pressed, an error occurred. Since there were no warnings, I was unsure where the error stemmed from but noticed there was an "X" on the class icon and Professor Wabara was able to determine that the entire class was excluded from the package thus why modify products would not run. We were able to troubleshoot and find the error which ment we had to go to the compiler settings and delete the class from the exclusion category.*/

public class ModifyProductController implements Initializable {


    Stage stage;
    Parent scene;
    private ObservableList<Part> tempAssocParts = FXCollections.observableArrayList();
    private ObservableList<Part> originalAssocParts = FXCollections.observableArrayList();
    private int autoID = MainScreenController.prodModIndex;


    /**
     * This is what we see when we first open the modify products screen.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Product prodMod = Inventory.getAllProducts().get(autoID);

        /**Populates Available Parts Table*/
        modifyProductAddTable.setItems(Inventory.getAllParts());
        modifyProductPartIDAddCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameAddCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInvLvlAddCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceAddCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductAddTable.setItems(Inventory.allParts);

        /**Populate Associated Parts table*/
        modifyProductRemoveTable.setItems(Inventory.getAllProducts().get(autoID).getAllAssociatedParts());
        modifyProductPartIDRemoveCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameRemoveCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInvLvlRemoveCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceRemoveCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**Auto fills the text fields*/
        modifyProductId.setText(Integer.toString(autoID + 1));
        modifyProductName.setText(prodMod.getName());
        modifyProductInv.setText(Integer.toString(prodMod.getStock()));
        modifyProductMin.setText(Integer.toString(prodMod.getMin()));
        modifyProductMax.setText(Integer.toString(prodMod.getMax()));
        modifyProductPrice.setText(Double.toString(prodMod.getPrice()));

        /**Disables ID*/
        modifyProductId.setDisable(true);

/**Adds the parts associated with the products*/
        tempAssocParts = prodMod.getAllAssociatedParts();
        modifyProductRemoveTable.setItems(tempAssocParts);

        originalAssocParts.addAll(tempAssocParts);

    }


    /**
     * table view
     */
    @FXML
    private TableView<Part> modifyProductAddTable;
    @FXML
    private TableView<Part> modifyProductRemoveTable;

    /**
     * text field
     */
    @FXML
    private TextField modifyProductMax;
    @FXML
    private TextField modifyProductId;
    @FXML
    private TextField modifyProductName;
    @FXML
    private TextField modifyProductPrice;
    @FXML
    private TextField modifyProductMin;
    @FXML
    private TextField modifyProductInv;
    @FXML
    private TextField modifyProductSearchTxt;

    /**
     * buttons
     */
    @FXML
    private Button SaveModifiedPart;
    @FXML
    private Button RemoveModifiedPart;
    @FXML
    private Button CancelModifiedPart;
    @FXML
    private Button AddModifiedPart;

    /**
     * table column
     */
    @FXML
    private TableColumn<Part, Integer> modifyProductPartIDAddCol;
    @FXML
    private TableColumn<Part, String> modifyProductPartNameAddCol;
    @FXML
    private TableColumn<Part, Integer> modifyProductInvLvlAddCol;
    @FXML
    private TableColumn<Part, Double> modifyProductPriceAddCol;
    @FXML
    private TableColumn<Part, String> modifyProductPartNameRemoveCol;
    @FXML
    private TableColumn<Part, Double> modifyProductPriceRemoveCol;
    @FXML
    private TableColumn<Part, Integer> modifyProductPartIDRemoveCol;
    @FXML
    private TableColumn<Part, Integer> modifyProductInvLvlRemoveCol;

    /**actions*/

    /**
     * This is the search method.
     * @param event searches by part name or ID
     */
    @FXML
    void searchTxt(ActionEvent event) {
        String searchText = modifyProductSearchTxt.getText();
        if (searchText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Search field is empty");
            alert.setContentText("Enter part ID or name");
            alert.showAndWait();
        }
        ObservableList<Part> partsReturned = Inventory.lookupPart(searchText);
        if (partsReturned.size() == 0) {
            try {
                int idNumber = Integer.parseInt(searchText);
                Part p = Inventory.lookupPart(idNumber);
                if (p != null) {
                    partsReturned.add(p);
                }
            } catch (NumberFormatException e) {
            }

            modifyProductAddTable.setItems(partsReturned);
            if (partsReturned.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Search Failed");
                alert.setContentText("No matching part found.");
                alert.showAndWait();
                modifyProductAddTable.setItems(Inventory.getAllParts());
            }
        }
    }

    /**
     * This is the add product part method.
     * @param event adds the product part
     */
    @FXML
    void onActionAddModifyProduct(ActionEvent event) {
        Part selectedPart = modifyProductAddTable.getSelectionModel().getSelectedItem();

        tempAssocParts.add(selectedPart);
        modifyProductRemoveTable.setItems(tempAssocParts);
    }

    /**
     * This is the remove associated part method.
     * @param event removes associated parts
     */
    @FXML
    void onActionRemoveModifyProduct(ActionEvent event) {
        Part part = modifyProductRemoveTable.getSelectionModel().getSelectedItem();
        if (part != null) {
            if (tempAssocParts.size() > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("Removing part from your new product");
                alert.setHeaderText("Confirm");
                alert.setContentText("Would you like to remove this part from your new product?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    tempAssocParts.remove(part);
                }
            }
 else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Nothing selected");
            alert.setHeaderText("Nothing selected");
            alert.setContentText("Nothing was selected to delete.");
            alert.showAndWait();
        }
    }
            }

    /**
     * This is the cancel modify product method.
     * @param event cancels modifying the product and returns to the main screen
     * @throws IOException
     */
    @FXML
    void onActionCancelModifyProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cancel Modify Product");
        alert.setContentText("Canceling Product Modification");
        alert.showAndWait();
        tempAssocParts.clear();
        tempAssocParts.addAll(originalAssocParts);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * This is the save product method for the modify product form.
     * @param event saves the new modified product information and displays it on the main menu form
     * @throws IOException
     * @throws NumberFormatException
     */
    @FXML
    public void onActionSaveModifyProduct(ActionEvent event) throws IOException, NumberFormatException {

        int modProductID = Integer.parseInt(modifyProductId.getText());
        String modProductName = modifyProductName.getText();
        int modProductInv = 0;
        Double modProductPrice = 0.0;
        int modProductMax = 0;
        int modProductMin = 0;

        try {
            modProductInv = Integer.parseInt(modifyProductInv.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INPUT ERROR");
            alert.setHeaderText("INPUT ERROR");
            alert.setContentText("Inventory must be a number");
            alert.showAndWait();
            return;
        }
        try {
            modProductMax = Integer.parseInt(modifyProductMax.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INPUT ERROR");
            alert.setHeaderText("INPUT ERROR");
            alert.setContentText("Maximum must be a number");
            alert.showAndWait();
            return;
        }
        try {
            modProductMin = Integer.parseInt(modifyProductMin.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INPUT ERROR");
            alert.setHeaderText("INPUT ERROR");
            alert.setContentText("Minimum must be a number");
            alert.showAndWait();
            return;
        }
        try {
            modProductPrice = Double.parseDouble(modifyProductPrice.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INPUT ERROR");
            alert.setHeaderText("INPUT ERROR");
            alert.setContentText("Price must be a decimal number XX.XX");
            alert.showAndWait();
            return;
        }
        Product modProduct =  new Product(modProductID, modProductName, modProductPrice, modProductInv, modProductMin, modProductMax);

        for (Part assoc : tempAssocParts) {
            modProduct.addAssociatedPart(assoc);
        }


        if (isValidProduct(modProduct)) {
            updateProduct(modProduct);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Validates input fields.
     * @param product the part being checked
     * @return true
     * @throws NumberFormatException
     */
    public boolean isValidProduct(Product product) throws NumberFormatException{
try {
    String checkedName = modifyProductName.getText();
    int checkedInv = Integer.parseInt((modifyProductInv.getText()));
    Double checkedPrice = Double.parseDouble(modifyProductPrice.getText());
    int checkedMax = Integer.parseInt(modifyProductMax.getText());
    int checkedMin = Integer.parseInt(modifyProductMin.getText());

    if (checkedName.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setContentText("Name field can not be empty");
        alert.showAndWait();
        return false;
    }
    if (checkedMin > checkedMax) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setContentText("Minimum can not be greater than maximum");
        alert.showAndWait();
        return false;
    }
    if (checkedPrice < 0) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setContentText("Price can not be less than zero");
        alert.showAndWait();
        return false;

    }
    if (checkedInv < checkedMin) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setContentText("Inventory can not be less than minimum");
        alert.showAndWait();
        return false;
    }
    if (checkedInv > checkedMax) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setContentText("Inventory can not be greater than maximum");
        alert.showAndWait();
        return false;
    }
}
catch (NumberFormatException e){

}
        return true;

    }
}