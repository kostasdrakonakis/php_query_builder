package lists;


public class SpiritComponentProduct {
    private int image;
    private String productName;

    public SpiritComponentProduct(int image, String productName) {
        this.image = image;
        this.productName = productName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
