package com.example.kostas.lists;

public class CartList {
    private String name;
    private double price;
    private String sugarTaste;
    private String sugarFlavor;
    private String Quantity;
    private String dose;
    private String comments;

    public CartList(String name, double price, String sugarTaste, String sugarFlavor, String quantity, String dose, String comments) {
        this.name = name;
        this.price = price;
        this.sugarTaste = sugarTaste;
        this.sugarFlavor = sugarFlavor;
        Quantity = quantity;
        this.dose = dose;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSugarTaste() {
        return sugarTaste;
    }

    public void setSugarTaste(String sugarTaste) {
        this.sugarTaste = sugarTaste;
    }

    public String getSugarFlavor() {
        return sugarFlavor;
    }

    public void setSugarFlavor(String sugarFlavor) {
        this.sugarFlavor = sugarFlavor;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
