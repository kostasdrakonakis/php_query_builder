package interfaces;


public interface CoffeeCommunicator {

    /**
     * Sends the data category coffees to the Activity that has the Fragment implementation
     * @author Kostas
     * @param name the name for each coffee product
     * @param image the image for each coffee product
     * @param price the price for each coffee product
     */
    void sendCoffeeListData(String name, String image, String price);
}
