package lists;

public class CustomList {

    private int imageId;
    private String itemName;
    private String description;
    int mainImage;
    private String link;

    public CustomList(int imageId, String itemName, String description, int mainImage, String link) {
        super();
        this.imageId = imageId;
        this.itemName = itemName;
        this.description = description;
        this.mainImage = mainImage;
        this.link = link;
    }


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMainImage() {
        return mainImage;
    }

    public void setMainImage(int mainImage) {
        this.mainImage = mainImage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
