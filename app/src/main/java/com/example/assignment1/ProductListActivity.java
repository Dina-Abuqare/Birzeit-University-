// Package declaration for the app
package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
public class ProductListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Product> products;
    ArrayList<Product> filteredProducts;
    ProductAdapter adapter;
    SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Initialize the ListView
        listView = findViewById(R.id.listViewProducts);
        prefManager = new SharedPrefManager(this);

        // Get the data from MainActivity
        String nameQuery = getIntent().getStringExtra("name");
        String categoryQuery = getIntent().getStringExtra("category");
        String skinTypeQuery = getIntent().getStringExtra("skinType");
        boolean isOrganicQuery = getIntent().getBooleanExtra("organic", false);
        boolean isCrueltyFreeQuery = getIntent().getBooleanExtra("crueltyFree", false);
        boolean isExpressQuery = getIntent().getBooleanExtra("express", false);

        // Initialize the product list
        products = new ArrayList<>();
        // Add some sample products
        products.add(new Product("Lip Gloss", "Makeup", 5, 30));
        products.add(new Product("Face Serum", "Skincare", 3, 60));
        products.add(new Product("Hair Mist", "Haircare", 7, 40));
        products.add(new Product("Face Mask", "Skincare", 5, 10));

        // Initialize filteredProducts and set up the adapter
        filteredProducts = new ArrayList<>(products);
        adapter = new ProductAdapter(this, filteredProducts);
        listView.setAdapter(adapter);

        // Add search functionality and filter the list
        Button searchButton = findViewById(R.id.buttonSearch);  // Make sure this button exists in your layout
        searchButton.setOnClickListener(v -> {
            filterProducts(nameQuery, categoryQuery, skinTypeQuery, isOrganicQuery, isCrueltyFreeQuery, isExpressQuery);
        });

        // Set an item on  click listener when a product is clicked
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            Product selected = filteredProducts.get(position); // Get the product clicked by the user
            prefManager.addToCart(selected); // Add the product to the cart using SharedPrefManager
            Toast.makeText(this, selected.getName() + " added to cart", Toast.LENGTH_SHORT).show(); // Show a message to the user
        });

        Button viewCart = findViewById(R.id.buttonGoToCart);
        viewCart.setOnClickListener(v -> {
            // When the button is clicked, open the CartActivity to view the cart
            startActivity(new Intent(ProductListActivity.this, CartActivity.class));
        });
    }

    private void filterProducts(String name, String category, String skinType, boolean isOrganic, boolean isCrueltyFree, boolean isExpress) {
        filteredProducts.clear();

        // Loop through the original products list and add the ones that match the search
        for (Product product : products) {
            boolean matchesName = product.getName().toLowerCase().contains(name.toLowerCase());
            boolean matchesCategory = product.getCategory().equalsIgnoreCase(category) || category.equals("All Categories");

            if (matchesName && matchesCategory) {
                filteredProducts.add(product);
            }
        }

        // Tell the adapter that the data changed
        adapter.notifyDataSetChanged();
    }
}
