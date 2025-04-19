package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText nameInput;
    Spinner categorySpinner;
    RadioGroup skinTypeGroup;
    CheckBox organicCheck, crueltyFreeCheck;
    Switch expressDelivery;
    Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link the XML views to the variables in the code
        nameInput = findViewById(R.id.editTextName);
        categorySpinner = findViewById(R.id.spinnerCategory);
        skinTypeGroup = findViewById(R.id.radioGroupSkin);
        organicCheck = findViewById(R.id.checkBoxOrganic);
        crueltyFreeCheck = findViewById(R.id.checkBoxCrueltyFree);
        expressDelivery = findViewById(R.id.switchExpress);
        searchBtn = findViewById(R.id.buttonSearch);

        // Set the click listener for the search button
        searchBtn.setOnClickListener(v -> {
            // Get the search query entered by the user
            String searchQuery = nameInput.getText().toString();

            if (!searchQuery.isEmpty()) {
                // Pass the search query via Intent
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                intent.putExtra("searchQuery", searchQuery); // Send search query to ProductListActivity
                startActivity(intent); // Start the ProductListActivity
            } else {
                Toast.makeText(MainActivity.this, "Please enter a search term", Toast.LENGTH_SHORT).show();
            }

            // Get the selected category from the spinner
            String category = categorySpinner.getSelectedItem().toString();

            // Get the selected skin type from the radio buttons
            int selectedSkinId = skinTypeGroup.getCheckedRadioButtonId();
            RadioButton selectedSkin = findViewById(selectedSkinId);
            String skinType = selectedSkin != null ? selectedSkin.getText().toString() : "";

            // Check if the checkboxes and switch are enabled
            boolean isOrganic = organicCheck.isChecked();
            boolean isCrueltyFree = crueltyFreeCheck.isChecked();
            boolean isExpress = expressDelivery.isChecked();

            // Create an intent to move to the ProductListActivity and send all the inputs
            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
            intent.putExtra("name", searchQuery);
            intent.putExtra("category", category);
            intent.putExtra("skinType", skinType);
            intent.putExtra("organic", isOrganic);
            intent.putExtra("crueltyFree", isCrueltyFree);
            intent.putExtra("express", isExpress);
            startActivity(intent); // Start the ProductListActivity
        });
    }
}
