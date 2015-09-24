package lists;

public class SpiritList {

    private String price;
    private String name;

    public SpiritList(String name, String price) {
        this.name = name;
        this.price = price;

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
}
