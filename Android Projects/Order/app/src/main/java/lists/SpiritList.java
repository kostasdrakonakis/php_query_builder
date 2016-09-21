package lists;

public class SpiritList {

    private String price;
    private String name;
    private String image;

    public SpiritList(String name, String price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;

    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
