package com.example.kostas.helpers;

import com.example.kostas.lists.ProductList;

import java.util.List;
import java.util.Vector;

public class ShoppingCartHelper {
    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<ProductList> catalog;
    private static List<ProductList> cart;

    public static List<ProductList> getCatalog(int resID){
        if(catalog == null) {
            catalog = new Vector<ProductList>();
            catalog.add(new ProductList(resID, "Dead or Alive", 29.99));
            catalog.add(new ProductList(resID, "Switch", 19.99));
            catalog.add(new ProductList(resID, "Watchmen", 39.99));

        }
        return catalog;
    }

    public static List<ProductList> getCart() {
        if(cart == null) {
            cart = new Vector<ProductList>();
        }

        return cart;
    }
}
