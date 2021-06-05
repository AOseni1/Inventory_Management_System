package Controller;

import Model.*;
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
import java.util.Optional;
import java.util.ResourceBundle;

import static Model.Inventory.*;

/** This class is the controller for the Add Product form.*/
public class AddProductController implements Initializable {
    Stage stage;
    Parent scene;

    private final ObservableList<Part> addparts = FXCollections.observableArrayList();
    private final ObservableList<Part> tempAssocParts = FXCollections.observableArrayList();
    private final ObservableList<Part> productParts = FXCollections.observableArrayList();
    private String error = "";

    /**
     * This is what we see when we initialize this screen(pre-populated).
     *
     * @param resourceBundle
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        /**Populate Products table*/
        addProductAddTable.setItems(Inventory.getAllParts());
        addProductPartIDAddCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameAddCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvLvlAddCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceAddCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**populate remove table*/
        addProductRemoveTable.setItems(addparts);
        addProductPartIdRemoveCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameRemoveCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvLvlRemoveCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceRemoveCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductAddTable.setItems(Inventory.allParts);
        addProductRemoveTable.setItems(tempAssocParts);


        addProductId.setText("Auto Gen - Disabled");
        addProductId.setDisable(true);

    }

    /**
     * table views
     */
    @FXML
    private TableView<Part> addProductRemoveTable;
    @FXML
    private TableView<Part> addProductAddTable;


    /**
     * textfield
     */
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductMin;
    @FXML
    private TextField addProductInv;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductSearchTxt;
    @FXML
    private TextField addProductId;
    @FXML
    private TextField addProductPrice;

    /**
     * table column add product
     */
    @FXML
    private TableColumn<Part, Double> addProductPriceAddCol;
    @FXML
    private TableColumn<Part, Integer> addProductInvLvlAddCol;
    @FXML
    private TableColumn<Part, Integer> addProductPartIDAddCol;
    @FXML
    private TableColumn<Part, String> addProductPartNameAddCol;

    /**
     * table column remove products
     */
    @FXML
    private TableColumn<Part, Double> addProductPriceRemoveCol;
    @FXML
    private TableColumn<Part, String> addProductPartNameRemoveCol;
    @FXML
    private TableColumn<Part, Integer> addProductInvLvlRemoveCol;
    @FXML
    private TableColumn<Part, Integer> addProductPartIdRemoveCol;

    @FXML
    private Button cancelBtn;

/**actions*/

    /**
     * This is the add product part method.
     *
     * @param event adds the product part
     */
    @FXML
    void onActionAddProductPart(ActionEvent event) {

        Part selectedPart = addProductAddTable.getSelectionModel().getSelectedItem();
        tempAssocParts.add(selectedPart);
        addProductRemoveTable.setItems(tempAssocParts);
    }

    /**
     * This is the remove associated part method.
     *
     * @param event removes associated parts
     */
    @FXML
    void onActionRemoveAssociatedProductPart(ActionEvent event) {
        Part part = addProductRemoveTable.getSelectionModel().getSelectedItem();
        if (part != null) {
            if (tempAssocParts.size() > 1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("Removing part from your new product");
                alert.setHeaderText("Confirm");
                alert.setContentText("Would you like to remove this part from your new product?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    tempAssocParts.remove(part);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setTitle("Unable to remove");
                alert.setHeaderText("Unable to remove");
                alert.setContentText("You must have at least 1 part associated with your product.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Nothing selected");
            alert.setHeaderText("Nothing selected");
            alert.setContentText("Nothing was selected to delete.");
            alert.showAndWait();
        }
    }

    /**
     * This is the cancel add product method.
     *
     * @param event cancels adding the product and returns to the main screen
     */
    @FXML
    void onActionCancelAddProduct(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is the save product method for the add product form.
     *
     * @param event saves the new product information and displays it on the main menu form
     * @throws NullPointerException
     * @throws NumberFormatException
     * @throws IOException
     */
    @FXML
    void onActionSaveAddProduct(ActionEvent event) throws NumberFormatException, NullPointerException, IOException {
        int newproductId = 1;
        for (Product a : Inventory.getAllProducts()) {
            if (a.getId() >= newproductId) {
                newproductId = a.getId() + 1;
            }
        }

        String productName = addProductName.getText();
           int productInv = 0;
         Double productPrice = 0.0;
        int productMax = 0;
        int productMin = 0;


        try {
             productInv = Integer.parseInt(addProductInv.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Inventory must be a number");
            alert.showAndWait();
            return;

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Enter an amount");
            alert.showAndWait();
            return;

        }
        try {
             productPrice = Double.parseDouble(addProductPrice.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Price must be entered in XX.XX format");
            alert.showAndWait();
            return;

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Enter a price");
            alert.showAndWait();
            return;
        }
        try {
             productMax = Integer.parseInt(addProductMax.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
           alert.setContentText("Maximum must be a number");
           alert.showAndWait();
           return;


        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Enter an amount");
            alert.showAndWait();
            return;

        }
        try {
             productMin = Integer.parseInt(addProductMin.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Minimum must be a number");
            alert.showAndWait();
            return;

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Enter an amount");
            alert.showAndWait();
            return;
        }


    Product newProduct = new Product(newproductId, productName, productPrice, productInv, productMax, productMin);

        for(Part p :tempAssocParts)

    {
        newProduct.addAssociatedPart(p);
    }
        if(isValidProduct(newProduct))

    {
        addProduct(newProduct);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}

    /**
     * Validates input fields.
     *
     * @param product the product being checked
     * @return true
     */
    public boolean isValidProduct(Product product) {
        try {
            String validName = addProductName.getText();
            int validInv = Integer.parseInt(addProductInv.getText());
            Double validPrice = Double.parseDouble(addProductPrice.getText());
            int validMax = Integer.parseInt(addProductMax.getText());
            int validMin = Integer.parseInt(addProductMin.getText());

            if (validName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Name field can not be empty");
                alert.showAndWait();
                return false;

            }
            if (validMin > validMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Minimum can not be greater than maximum");
                alert.showAndWait();
                return false;
            }
            if (validPrice < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Price can not be less than zero");
                alert.showAndWait();
                return false;
            }
            if (validInv < validMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Inventory can not be less than minimum");
                alert.showAndWait();
                return false;
            }
            if (validInv > validMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Inventory can not be greater than maximum");
                alert.showAndWait();
                return false;
            }
        } catch (NumberFormatException e) {

        }
        return true;
    }



    /**
     * This is the search method.
     *
     * @param event searches by part name or ID
     */
    @FXML
    void searchTxt(ActionEvent event) {
        String searchText = addProductSearchTxt.getText();
        if (searchText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Search is empty");
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

            addProductAddTable.setItems(partsReturned);
            if (partsReturned.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Search Failed");
                alert.setContentText("No matching part found.");
                alert.showAndWait();
                addProductAddTable.setItems(Inventory.getAllParts());
            }
        }
    }
}


