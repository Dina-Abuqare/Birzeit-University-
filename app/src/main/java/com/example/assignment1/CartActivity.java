package com.example.assignment1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ListView cartListView;
    Button checkoutBtn;
    SharedPrefManager prefManager;
    ArrayAdapter<String> adapter;
    ArrayList<String> cartDisplay;
    TextView orderConfirmationMessage; // Declare the TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Link UI components to the code
        cartListView = findViewById(R.id.cartListView);
        checkoutBtn = findViewById(R.id.checkoutButton);
        prefManager = new SharedPrefManager(this);
        orderConfirmationMessage = findViewById(R.id.orderConfirmationMessage); // Link TextView

        // Get the cart items from the shared preferences
        ArrayList<Product> cart = prefManager.getCart();
        cartDisplay = new ArrayList<>();

        //Add cart items to the display list
        for (Product p : cart) {
            cartDisplay.add(p.getName() + " - " + p.getPrice() + "$");
        }

        // Create an adapter to put the cart items in the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cartDisplay);
        cartListView.setAdapter(adapter);

        // Checkout button click listener
        checkoutBtn.setOnClickListener(v -> {
            // Clear cart and show a confirmation message
            prefManager.clearCart();

            // Set the TextView with the confirmation message and make it visible
            orderConfirmationMessage.setText("Order placed!");
            orderConfirmationMessage.setVisibility(android.view.View.VISIBLE);

            //Hide the confirmation message after 3 seconds
            new Handler().postDelayed(() -> orderConfirmationMessage.setVisibility(android.view.View.GONE), 3000);

            cartDisplay.clear();
            adapter.notifyDataSetChanged(); // Refresh the ListView
        });
    }
}
