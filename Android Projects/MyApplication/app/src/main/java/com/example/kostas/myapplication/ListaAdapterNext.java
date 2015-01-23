package com.example.kostas.myapplication;

import android.app.Activity;

public class ListaAdapterNext extends Activity {

    private int imageNextId;
    private String itemNextName;
    private String itemDescription;
    private String itemUsage;
    private int nextImage;

    public ListaAdapterNext(int imageNextId, String itemNextName, String itemDescription, String itemUsage, int nextImage) {
        super();
        this.imageNextId = imageNextId;
        this.itemNextName = itemNextName;
        this.itemDescription = itemDescription;
        this.itemUsage = itemUsage;
        this.nextImage = nextImage;
    }

    public int getImageNextId() {
        return imageNextId;
    }

    public void setImageNextId(int imageNextId) {
        this.imageNextId = imageNextId;
    }

    public String getItemNextName() {
        return itemNextName;
    }

    public void setItemNextName(String itemNextName) {
        this.itemNextName = itemNextName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemUsage() {
        return itemUsage;
    }

    public void setItemUsage(String itemUsage) {
        this.itemUsage = itemUsage;
    }

    public int getNextImage() {
        return nextImage;
    }

    public void setNextImage(int nextImage) {
        this.nextImage = nextImage;
    }
}
