package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static Model.Inventory.addProduct;

/** This class runs the inventory management system.*/

/** If I were to add a feature, it would be to attach this to a database to constantly update the information instead of adding information through the GUI. This was, the information would be better streamlined and the information would also be backed up in a more secure way. Also, this would make it easier to export and manipulate the data*/

public class InventoryManagementSystem extends Application {

    /**
     * what we see when we first load the application - we see the data.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
       Inventory inv = new Inventory();
        addTestData(inv);
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 654, 381));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * adding test data
     * @param inv adds the test data to the inventory
     */

    void addTestData(Inventory inv) {

        Part a1 = new InHouse(Inventory.assignPartID(), "Part 1", 2.99, 75, 50, 100, 1);
        Part a2 = new InHouse(Inventory.assignPartID(), "Part 2", 1.99, 20, 10, 25, 2);
        Part a3 = new InHouse(Inventory.assignPartID(), "Part 3", 9.99, 150, 100, 250, 4);
        inv.addPart(a1);
        inv.addPart(a2);
        inv.addPart(a3);
        Inventory.addPart(new InHouse(Inventory.assignPartID(), "Part 4", 1.99, 15, 10, 25, 2));
        Inventory.addPart(new InHouse(Inventory.assignPartID(), "Part 5", 1.99, 15, 10, 25, 2));


        Part b1 = new Outsourced(Inventory.assignPartID(), "Part 6", 2.99, 75, 50, 100, "Moon");
        Part b2 = new Outsourced(Inventory.assignPartID(), "Part 7", 1.99, 15, 10, 25, "Mercury");
        Part b3 = new Outsourced(Inventory.assignPartID(), "Part 8", 9.99, 150, 100, 250, "Mars");
        inv.addPart(b1);
        inv.addPart(b2);
        inv.addPart(b3);
        Inventory.addPart(new Outsourced((Inventory.assignPartID()), "Part 9", 21.00, 30, 10, 70, "Company Four"));
        Inventory.addPart(new Outsourced((Inventory.assignPartID()), "Part 10", 17.00, 80, 15, 90, "Company Five"));

        Product prod1 = new Product(Inventory.assignProductID(), "Product 1", 2.99, 90, 50, 100);
        prod1.addAssociatedPart(a1);
        addProduct(prod1);


        Product prod2 = new Product(Inventory.assignProductID(), "Product 2", 1.99, 15, 10, 25);
        prod2.addAssociatedPart(a2);
        addProduct(prod2);




    }




    }
