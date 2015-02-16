package lists;

public class ProductList {
    private String image;
    private String name;
    private String price;
    private String[] items;

    public ProductList(String image, String name, String price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public ProductList(String price, String[] items, String image) {
        this.price = price;
        this.items = items;
        this.image = image;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
