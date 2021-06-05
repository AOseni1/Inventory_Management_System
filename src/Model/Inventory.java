package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


public class Inventory {


/**The Inventory class allows for creating methods for the parts and products observable lists */
    public Inventory(){}

    /**observable lists*/
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    public static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();



    /**all parts and products
     * @return returns all parts*/

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
/** @return all products*/
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }
    public static ObservableList<Part> getAllFilteredParts() {
        return getAllFilteredParts();
    }

    public static ObservableList<Product> getAllFilteredProducts() {

        return getAllFilteredProducts();
    }



    /**add parts and products
     * @param part for add parts
     */

    public static void addPart (Part part) {
        allParts.add(part);
    }

    public static void addProduct (Product product) {
        allProducts.add(product);
    }




    /**search parts by part ID
     * @param searchPartName searches parts by part ID
     * @return all parts and filtered parts*/

    public static ObservableList<Part> lookupPart(String searchPartName) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(searchPartName)) {
                filteredParts.add(part);
            }
        }
        return filteredParts;
    }

    /**search part by id
     * @return null*/
    public static Part lookupPart(int searchPartId) {
        for (Part part : allParts) {
            if (part.getId() == searchPartId) {
                return part;
            }
        }
        return null;
    }


    /**search parts by product ID
     * @return all products and filtered products*/
    public static ObservableList<Product> lookupProduct(String searchPartName) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(searchPartName)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    /**search product by id
     * @return null*/
    public static Product lookupProduct(int searchProductID) {
        for (Product product : allProducts) {
            if (product.getId() == searchProductID) {
                return product;
            }
        }
        return null;
    }



    /**update parts and products
     * @param partToUpdate updates modified parts and products*/
    public static void updatePart(int index, Part partToUpdate) {
   allParts.set(index, partToUpdate);
            }
    public static void updateProduct(Product productToUpdate) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productToUpdate.getId()) {
                allProducts.set(i, productToUpdate);
            }
        }
    }

    /**delete part and product*/
    public static boolean deletePart(Part partToDelete) {
        return allParts.remove(partToDelete);
    }

    public static boolean deleteproduct (Product productToDelete){
        return allProducts.remove(productToDelete);
    }



    /**assign part and product ID*/
    private static int partCounter = 0;
    private static int productCounter = 0;

    public static int assignPartID(){
        partCounter++;
        return partCounter;
    }

    public static int assignProductID(){
        productCounter++;
        return productCounter;
    }

}






