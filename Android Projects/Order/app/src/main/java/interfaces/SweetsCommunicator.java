package interfaces;


public interface SweetsCommunicator {
    /**
     * Sends the data category sweets to the Activity that has the Fragment implementation
     * @author Kostas
     * @param name the name for each sweet product
     * @param image the image for each sweet product
     * @param price the price for each sweet product
     */
    void sendSweetListData(String name, String image, String price);
}
