package objects;


public class ParseItem {
    private String name, image;
    private String[] items;

    public ParseItem(String name, String image, String[] items) {
        this.name = name;
        this.image = image;
        this.items = items;
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

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }
}
