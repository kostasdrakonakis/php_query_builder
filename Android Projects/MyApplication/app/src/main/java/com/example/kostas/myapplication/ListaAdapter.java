package com.example.kostas.myapplication;

import android.app.Activity;

public class ListaAdapter extends Activity {

    private int imageId;
    private String itemName;

    public ListaAdapter(int imageId, String itemName) {
        super();
        this.imageId = imageId;
        this.itemName = itemName;
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
}
