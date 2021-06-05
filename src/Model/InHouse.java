package Model;

/**The Inhouse Part class extends the Part class to create Inhouse Part objects.*/
public class InHouse extends Part {

    public int machineId;
/**constructor.
 * @param id the id
 * @param machineId the machine id
 * @param max the max mount of items
 * @param min the min amount items
 * @param name the name of the item
 * @param price the price of the item
 * @param stock the amount of item in stock
 * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    public InHouse() {
        super(0, "", 0, 0, 0, 0);
    }

    /**This is the getter for the machine ID.
     * @return the machine ID*/
    public int getMachineId() {
        return machineId;
    }

    /**This is the setter for the machine ID
     * @param machineId sets the machine ID*/
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
