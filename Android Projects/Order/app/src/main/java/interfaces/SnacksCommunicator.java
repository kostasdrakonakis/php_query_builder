package interfaces;

public interface SnacksCommunicator {
    /**
     * Sends the data category snacks to the Activity that has the Fragment implementation
     * @author Kostas
     * @param name the name for each snack product
     * @param image the image for each snack product
     * @param price the price for each snack product
     */
    void sendSnackListData(String name, String image, String price);
}
