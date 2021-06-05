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
import java.util.Optional;
import java.util.ResourceBundle;

import static Model.Inventory.*;

/** This class is the controller for the Main Screen form.*/


public class MainScreenController implements Initializable {
    Stage stage;
    Parent scene;

    protected static int partModIndex;
    protected static int prodModIndex;

    /**
     * This is what we see when we first open the application.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**populate parts table*/
        imsPartsTable.setItems(Inventory.getAllParts());
        imsPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        imsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        imsInvPartCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        imsPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**Populate Products table*/
        imsProductsTable.setItems(Inventory.getAllProducts());
        imsProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        imsProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        imsInvProductCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        imsProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Buttons
     */
    @FXML
    private Button deleteProducts;
    @FXML
    private Button addParts;
    @FXML
    private Button AddParts;
    @FXML
    private Button modifyProducts;
    @FXML
    private Button deleteParts;
    @FXML
    private Button EsitIMS;
    @FXML
    private Button modifyParts;

    /**
     * Part Columns
     */
    @FXML
    private TableColumn<Part, String> imsPartNameCol;
    @FXML
    private TableColumn<Part, Integer> imsInvPartCountCol;
    @FXML
    private TableColumn<Part, Double> imsPartPriceCol;
    @FXML
    private TableColumn<Part, Integer> imsPartIDCol;

    /**
     * Product Columns
     */
    @FXML
    private TableColumn<Product, Integer> imsProductIDCol;
    @FXML
    private TableColumn<Product, Double> imsProductPriceCol;
    @FXML
    private TableColumn<Product, String> imsProductNameCol;
    @FXML
    private TableColumn<Product, Integer> imsInvProductCountCol;

    /**
     * Text Field
     */
    @FXML
    private TextField mainScreenPartsSearchTxt;
    @FXML
    private TextField mainScreenProductsSearchTxt;

    /**
     * Table View
     */
    @FXML
    private TableView<Product> imsProductsTable;
    @FXML
    private TableView<Part> imsPartsTable;

    /** This is the add parts method and it takes the user to the add parts forms.
     * @param event  This is the add parts button that takes the user to the add parts form
     * @throws IOException
     */
    @FXML
    void onActionAddParts(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This is the delete parts method and it deletes the parts from the parts table on the main screen.
     * @param event  deleted the parts from the parts table
     */
    @FXML
    void onActionDeleteParts(ActionEvent event) {
        if (imsPartsTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Nothing selected");
            alert.setHeaderText("Nothing selected");
            alert.setContentText("Nothing selected to delete");
            alert.showAndWait();
        }
        else {
            if (allParts.size() > 1) {
                Part partToDelete = imsPartsTable.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("Delete Part");
                alert.setHeaderText("Delete Part");
                alert.setContentText("Do you want to delete " + partToDelete.getName() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    deletePart(partToDelete);
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setTitle("Unable to delete");
                alert.setHeaderText("Unable to delete");
                alert.setContentText("Unable to delete as Part table cannot be empty.");
                alert.showAndWait();
            }
        }
    }


    /** This is the modify parts method and it takes the user to the modify parts form.
     * @param event  parts button takes the user to the modify parts form
     * @throws IOException
     */
    @FXML
    void onActionModifyParts(ActionEvent event) throws IOException {
        partModIndex = Inventory.getAllParts().indexOf((imsPartsTable.getSelectionModel().getSelectedItem()));
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ModifyPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This is the search parts method and it searches the parts by ID or name.
     * @param event searches parts by ID or name
     */
    @FXML
    void onActionPartsSearch(ActionEvent event) {
        String search = mainScreenPartsSearchTxt.getText();
        if (search.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Search is empty");
            alert.setHeaderText("Search is empty");
            alert.setContentText("Search text box is empty. To search, type in the Part ID or name and hit Enter.");
            alert.showAndWait();
        }
        ObservableList<Part> returnedParts = Inventory.lookupPart(search);
        if (returnedParts.size() == 0) {
            try {
                int partIDNumber = Integer.parseInt(search);
                Part p = Inventory.lookupPart(partIDNumber);
                if (p != null) {
                    returnedParts.add(p);
                }
            } catch (NumberFormatException e) {
            }
        }
        imsPartsTable.setItems(returnedParts);
        if(returnedParts.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Failed");
            alert.setContentText("No matching part found.");
            alert.showAndWait();
            imsPartsTable.setItems(Inventory.getAllParts());
        }
    }




    /** This is the add products method and it takes the user to the add products form.
     * @param event this takes the user to the add products form
     * @throws IOException
     */

    @FXML
    void onActionAddProducts(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This is the delete products method and it deletes products from the products table.
     * @param event deletes products from the products table
     */
    @FXML
    void onActionDeleteProducts(ActionEvent event) {
        Product productToDelete = imsProductsTable.getSelectionModel().getSelectedItem();
        if(!productToDelete.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to delete");
            alert.setContentText("Can not delete product with associated parts.");
            alert.showAndWait();
        }
        else {
            if (allProducts.size() > 1) {
                Product delete = imsProductsTable.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("Delete");
                alert.setHeaderText("Deleting");
                alert.setContentText("Are you sure you want to delete " + delete.getName() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    deleteproduct(delete);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setTitle("Unable to delete");
                alert.setHeaderText("Unable to delete");
                alert.setContentText("Table cannot be empty.");
                alert.showAndWait();
            }
        }
    }


    /** This is the modify products method and it takes the user to the modify methods form.
     * @param event takes the user to the modify parts form
     * @throws IOException
     */
    @FXML
    void onActionModifyProducts(ActionEvent event) throws IOException {
        prodModIndex = Inventory.getAllProducts().indexOf((imsProductsTable.getSelectionModel().getSelectedItem()));
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /** This is the search products method and it searches products by product ID or name.
     * @param event searches products by ID or name
     */
    @FXML
    void onActionProductsSearch(ActionEvent event) {
        String search = mainScreenProductsSearchTxt.getText();
        if (search.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Search is empty");
            alert.setHeaderText("Search is empty");
            alert.setContentText("Search text box is empty. To search, type in the Part ID or name and hit Enter.");
            alert.showAndWait();
        }
        ObservableList<Product> returnedProds = Inventory.lookupProduct(search);
        if (returnedProds.size() == 0) {
            try {
                int productID = Integer.parseInt(search);
                Product product = Inventory.lookupProduct(productID);
                if (product != null) {
                    returnedProds.add(product);
                }
            } catch (NumberFormatException e) {
            }
        }
        imsProductsTable.setItems(returnedProds);
        mainScreenProductsSearchTxt.setText("");
        if (returnedProds.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed Search");
            alert.setContentText("No matching part found.");
            alert.showAndWait();
            imsProductsTable.setItems(Inventory.getAllProducts());
        }
    }

        /**exit the application.
         * @param event exits application*/
        @FXML
        void onActionExitIMS (ActionEvent event){
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }


    }

