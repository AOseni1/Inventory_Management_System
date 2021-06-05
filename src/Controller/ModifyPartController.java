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

import javax.xml.validation.Validator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class is the controller for the Modify Part form.*/
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;
    protected int autoIndex = MainScreenController.partModIndex;
    boolean inhouse;
/**This is what you see when you first open the modify part screen. */

    /**
     * I had a runtime error that made it so that when I would save, the partId would change but be in the correct position.
     * For example, I would select "Part 3" and modify it, save it then it would liks it as "12" in the third position
     * I worked with Professor Mark Kinkead on the issue but was unable to find the error until I realized that
     * In one area the autoID didn't refactor to autoIndex (which is odd) so once I found that out,
     * I changed it to autoIndex and everything worked!
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Part partMod = Inventory.getAllParts().get(autoIndex);
        modifyPartID.setText(Integer.toString(partMod.getId()));
        modifyPartName.setText(partMod.getName());
        modifyPartInv.setText(Integer.toString(partMod.getStock()));
        modifyPartMin.setText(Integer.toString(partMod.getMin()));
        modifyPartMax.setText(Integer.toString(partMod.getMax()));
        modifyPartPrice.setText(Double.toString(partMod.getPrice()));
        if (partMod instanceof InHouse) {
            inHouseRadioBtn.setSelected(true);
            modifypartfield.setText("Machine ID");
            MachineCompany.setText(Integer.toString(((InHouse) Inventory.getAllParts().get(autoIndex)).getMachineId()));
        } else {
            outsourcedRadioBtn.setSelected(true);
            modifypartfield.setText("Company Name");
            MachineCompany.setText(((Outsourced) Inventory.getAllParts().get(autoIndex)).getCompanyName());
        }
        modifyPartID.setDisable(true);
    }

    /**
     * text fields
     */
    @FXML
    private TextField modifyPartID;
    @FXML
    private TextField MachineCompany;
    @FXML
    private TextField modifyPartMin;
    @FXML
    private TextField modifyPartInv;
    @FXML
    private TextField modifyPartName;
    @FXML
    private TextField modifyPartMax;
    @FXML
    private TextField modifyPartPrice;

    /**
     * radio buttons
     */
    @FXML
    private RadioButton outsourcedRadioBtn;
    @FXML
    private RadioButton inHouseRadioBtn;

    /**
     * buttons
     */
    @FXML
    private Button saveModifiedPart;

    /**
     * labels
     */
    @FXML
    private Label modifypartfield;
    @FXML
    private Label MachineIDorCompanyName;

    /**
     * toggle group
     */
    @FXML
    private ToggleGroup ModifyPartTG;

    /**
     * for toggling
     */
    boolean isOutsourced;


    /**
     * toggle the radio buttons
     *
     * @param event Toggles the radio buttons. Shows Machine ID when InHouse is selected
     */

    @FXML
    void inHouseHandler(ActionEvent event) {
        MachineIDorCompanyName.setText("Machine ID");
        isOutsourced = false;
    }

    /**
     * toggle the radio buttons
     *
     * @param event Toggles the radio buttons. Shows Company Name when OutSourced is selected
     */
    @FXML
    void outSourcedHandler(ActionEvent event) {
        MachineIDorCompanyName.setText("Company Name");
        isOutsourced = true;
    }

    /**
     * This saves a modified part to the part table.
     *
     * @param event saves the modified part to the part table
     * @throws IOException
     * @throws NumberFormatException
     * @throws NullPointerException
     */
    @FXML
    void onActionSaveModifyPart(ActionEvent event) throws IOException, NumberFormatException, NullPointerException {
        int newID = Integer.parseInt(String.valueOf(autoIndex));
        String newName = modifyPartName.getText();
        int newInv = 0;
        double newPrice = 0;
        int newMax = 0;
        int newMin = 0;
        int machineID = 0;
        String companyName = "";

        if (inHouseRadioBtn.isSelected()) {
            try {
                newInv = Integer.parseInt(modifyPartInv.getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Inventory count must be a number");
                alert.showAndWait();
                return;

            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Please enter an amount");
                alert.showAndWait();
                return;
            }
            try {
                newPrice = Double.parseDouble(modifyPartPrice.getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Price must be entered in XX.XX format");
                alert.showAndWait();
                return;

            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Please enter a price");
                alert.showAndWait();
                return;
            }
            try {
                newMax = Integer.parseInt(modifyPartMax.getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Inventory maximum must be a number");
                alert.showAndWait();
                return;

            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Please enter an amount");
                alert.showAndWait();
                return;
            }
            try {
                newMin = Integer.parseInt(modifyPartMin.getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Inventory maximum must be a number");
                alert.showAndWait();
                return;

            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Please enter an amount");
                alert.showAndWait();
                return;
            }
            try {
                machineID = Integer.parseInt(MachineCompany.getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Machine ID must be a number");
                alert.showAndWait();
                return;

            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Please enter the Machine ID number");
                alert.showAndWait();
                return;
            }
            Part newInhouse = new InHouse(newID, newName, newPrice, newInv, newMax, newMin, machineID);
            if (isValidPart(newInhouse)) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        if (outsourcedRadioBtn.isSelected()) {
            try {
                newInv = Integer.parseInt(modifyPartInv.getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Inventory count must be a number");
                alert.showAndWait();
                return;

            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Please enter an amount");
                alert.showAndWait();
                return;
            }
            try {
                newPrice = Double.parseDouble(modifyPartPrice.getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Price must be entered in XX.XX format");
                alert.showAndWait();
                return;

            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Please enter a price");
                alert.showAndWait();
                return;
            }
            try {
                newMax = Integer.parseInt(modifyPartMax.getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Inventory maximum must be a number");
                alert.showAndWait();
                return;

            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Please enter an amount");
                alert.showAndWait();
                return;
            }
            try {
                newMin = Integer.parseInt(modifyPartMin.getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Inventory maximum must be a number");
                alert.showAndWait();
                return;

            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Please enter an amount");
                alert.showAndWait();
                return;
            }
            try {
                companyName = MachineCompany.getText();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Machine ID must be a number");
                alert.showAndWait();
                return;

            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Please enter the Machine ID number");
                alert.showAndWait();
                return;
            }

            Part newOutsource = new Outsourced(newID, newName, newPrice, newInv, newMax, newMin, companyName);
            if (isValidPart(newOutsource)) {
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
            String name = modifyPartName.getText();
            int inv = Integer.parseInt(modifyPartInv.getText());
            Double price = Double.parseDouble(modifyPartPrice.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            int min = Integer.parseInt(modifyPartMin.getText());

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Name field can not be empty");
                alert.showAndWait();
                return false;
            }

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Minimum inventory can not be greater than maximum");
                alert.showAndWait();
                return false;
            }
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

        /**
         * This cancels the modifying of the part and returns back to main screen.
         * @param event cancels the modifying of the part and returns to the main menu screen
         * @throws IOException
         */
        @FXML
        void onActionCancelModifyPart (ActionEvent event) throws IOException {


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
