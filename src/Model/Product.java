package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This is the Product class and it creates Product objects.*/
public class Product {
    protected ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;




    /**observable list of associated parts
     * @return associated parts*/
    public ObservableList getAllAssociatedParts() {
        return this.associatedParts;
    }

    /**constructor.
     * @param id the id
     * @param max the max mount of items
     * @param min the min amount items
     * @param name the name of the item
     * @param price the price of the item
     * @param stock the amount of item in stock
     * */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }

    /**getters and setters.
     * @return id, name, price, stock, min, max, and associated part*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }


/**to delete associated part
 * @param part being referred to
 * @return original list*/
    public boolean deleteAssociatedPart(Part part) {
        if (associatedParts.contains(part)) {
            associatedParts.remove(part);
            return true;
        } else {
            return false;
        }
    }

}

