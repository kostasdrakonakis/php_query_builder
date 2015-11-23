package lists;


public class CartList {

    private String name, price, image, preferation1, preferation2, preferation3, preferation4, quantity;

    public CartList(String name, String price, String image, String preferation1, String preferation2, String preferation3, String preferation4, String quantity) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.preferation1 = preferation1;
        this.preferation2 = preferation2;
        this.preferation3 = preferation3;
        this.preferation4 = preferation4;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPreferation1() {
        return preferation1;
    }

    public void setPreferation1(String preferation1) {
        this.preferation1 = preferation1;
    }

    public String getPreferation2() {
        return preferation2;
    }

    public void setPreferation2(String preferation2) {
        this.preferation2 = preferation2;
    }

    public String getPreferation3() {
        return preferation3;
    }

    public void setPreferation3(String preferation3) {
        this.preferation3 = preferation3;
    }

    public String getPreferation4() {
        return preferation4;
    }

    public void setPreferation4(String preferation4) {
        this.preferation4 = preferation4;
    }
}
