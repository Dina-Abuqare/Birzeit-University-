package com.example.assignment1;

import android.content.Context;
import android.content.SharedPreferences;

// to convert lists into strings and back
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPrefManager {
    private SharedPreferences prefs; // to save the data
    private SharedPreferences.Editor editor; // to edit the saved data
    private static final String CART_KEY = "cart_items"; // the name for our cart in the prefs

    // constructor – runs when we make a new SharedPrefManager
    public SharedPrefManager(Context context) {
        // this is like opening the notebook called GlamShopPrefs
        prefs = context.getSharedPreferences("GlamShopPrefs", Context.MODE_PRIVATE);
        editor = prefs.edit(); // we can now write into it
    }

    // this method adds a product to the cart
    public void addToCart(Product product) {
        ArrayList<Product> cart = getCart(); // get whatever is already in the cart
        cart.add(product); // add the new item
        Gson gson = new Gson(); //  turn the list into a string
        String json = gson.toJson(cart); // convert list to JSON
        editor.putString(CART_KEY, json); // save it into prefs
        editor.apply(); // apply the changes
    }

    //  get all the products from the cart
    public ArrayList<Product> getCart() {
        String json = prefs.getString(CART_KEY, null); // try to get the saved cart
        if (json != null) {
            // if it exists, convert that JSON back to a list
            Type type = new TypeToken<ArrayList<Product>>(){}.getType();
            return new Gson().fromJson(json, type); // give back the actual list
        } else {
            return new ArrayList<>(); // if there is no cart yet, just return empty list
        }
    }

    // this empties the cart
    public void clearCart() {
        editor.remove(CART_KEY); // delete the saved cart
        editor.apply(); // save the deletion
    }
}
