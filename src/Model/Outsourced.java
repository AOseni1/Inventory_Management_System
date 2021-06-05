package Model;

import Model.Part;
/**The Outsourced Part class extends the Part class to create Outsourced Part objects.*/
public class Outsourced extends Part {

    public String companyName;

    public Outsourced() {
        super(0, "", 0, 0, 0, 0);
    }

    /**This is the getter for the company name.
     * @return the company name*/
    public String getCompanyName() {
        return companyName;
    }

    /**This is the setter for the company name
     * @param companyName sets the company name */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**constructor.
     * @param id the id
     * @param companyName the company name
     * @param max the max mount of items
     * @param min the min amount items
     * @param name the name of the item
     * @param price the price of the item
     * @param stock the amount of item in stock
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;


    }
}
