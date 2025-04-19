package com.example.assignment1;



import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
// to connect our product list to the ListView in the app
public class ProductAdapter extends BaseAdapter {
    private Context context; // the screen or activity where this will be shown
    private ArrayList<Product> products; // the list of all the products


    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    // tells the ListView how many items it should show
    @Override
    public int getCount() {
        return products.size(); // return the total number of products
    }


    @Override
    public Object getItem(int item) {
        return products.get(item);
    }


    @Override
    public long getItemId(int item) {
        return item;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        // create a new view from our XML file
        View view = View.inflate(context, R.layout.product_item, null);

        // find the name and price TextViews in the layout
        TextView name = view.findViewById(R.id.productName);
        TextView price = view.findViewById(R.id.productPrice);

        // get the product for this position in the list
        Product p = products.get(i);

        // set the name and price in the TextViews
        name.setText(p.getName());
        price.setText(p.getPrice() + "$");

        return view; // show this view in the ListView
    }
}
