package Controller;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/** This class is the controller for the Add Part form. */
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;
    boolean inhouse;

    /**
     * This is what is initialized when we start up the section.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartID.setText("Auto Gen - Disabled");
        addPartID.setDisable(true);


    }

    /**
     * labels
     */
    @FXML
    private Label machineIDCompanyName;

    /**
     * radio buttons
     */
    @FXML
    private ToggleGroup AddPartTG;
    @FXML
    private Button AddPartSaveBtn;
    @FXML
    private RadioButton inHouseRadioBtn;
    @FXML
    private RadioButton outsourcedRadioBtn;


    /**
     * text fields
     */
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartInv;
    @FXML
    private TextField AddPartMin;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartID;
    @FXML
    private TextField AddPartMachineCompany;
    private int partID;

    /**
     * This controls how the radio buttons function.
     *
     * @param event Shows Machine ID next to the text box when Inhouse is selected
     */
    @FXML
    private void inhouseHandler(ActionEvent event) {
        inhouse = true;
        machineIDCompanyName.setText("Machine ID");

    }

    /**
     * This also controls how the radio buttons function.
     *
     * @param event Shows Company name next to the text box when Outsourced is selected
     */
    @FXML
    private void outsourcedHandler(ActionEvent event) {
        inhouse = false;
        machineIDCompanyName.setText("Company Name");

    }

    /**
     * This cancels the add part and go back to main screen.
     *
     * @param event cancels adding the part and returns to the main menu screen
     */

    @FXML
    void onActionCancelAddPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This saves a new part to the part table.
     *
     * @param event saves a new part to the part table
     */


    @FXML
    private void onActionAddPartSave(ActionEvent event) throws IOException, NumberFormatException, NullPointerException {

        String newName = addPartName.getText();
        int newInv = 0;
        double newPrice = 0;
        int newMax = 0;
        int newMin = 0;
        int machineID = 0;
        String companyName = "";

        int newPartId = 1;
        for (Part a : Inventory.getAllParts()) {
            if (a.getId() >= newPartId) {
                newPartId = a.getId() + 1;
            }
        }

        if (inHouseRadioBtn.isSelected()) {
            try {
                newInv = Integer.parseInt(addPartInv.getText());
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
                newPrice = Double.parseDouble(addPartPrice.getText());
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
                newMax = Integer.parseInt(addPartMax.getText());
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
                newMin = Integer.parseInt(AddPartMin.getText());
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
                machineID = Integer.parseInt(AddPartMachineCompany.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Machine ID must be a number");
                alert.showAndWait();
                return;

            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Enter the Machine ID number");
                alert.showAndWait();
                return;
            }
            Part newInhouse = new InHouse(newPartId, newName, newPrice, newInv, newMax, newMin, machineID);
            if (isValidPart(newInhouse)) {
                Inventory.addPart(newInhouse);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        if (outsourcedRadioBtn.isSelected()) {
            try {
                newInv = Integer.parseInt(addPartInv.getText());
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
                newPrice = Double.parseDouble(addPartPrice.getText());
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
                newMax = Integer.parseInt(addPartMax.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Inventory maximum must be a number");
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
                newMin = Integer.parseInt(AddPartMin.getText());
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
                companyName = AddPartMachineCompany.getText();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Machine ID must be a number");
                alert.showAndWait();
                return;

            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Enter the Machine ID number");
                alert.showAndWait();
                return;
            }
            Part newOutsource = new Outsourced(newPartId, newName, newPrice, newInv, newMax, newMin, companyName);
            if (isValidPart(newOutsource)) {
                Inventory.addPart(newOutsource);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }


        }
    }

    /**
     * Validates input fields.
     *
     * @param part the part being checked
     * @return true
     */
    public boolean isValidPart(Part part) {
        try {
            String name = addPartName.getText();
            int inv = Integer.parseInt(addPartInv.getText());
            Double price = Double.parseDouble(addPartPrice.getText());
            int max = Integer.parseInt(addPartMax.getText());
            int min = Integer.parseInt(AddPartMin.getText());

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Name field can not be empty");
                alert.showAndWait();
                return false;
            }
            System.out.println("step 1");
            if (min > max) { System.out.println("step 2");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Minimum inventory can not be greater than maximum");
                alert.showAndWait();
                return false;
            }
            System.out.println("step 3");
            if (price < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Price can not be less than zero");
                alert.showAndWait();
                return false;
            }
            if (inv < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Inventory can not be less than minimum");
                alert.showAndWait();
                return false;
            }
            if (inv > max) {
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
}
