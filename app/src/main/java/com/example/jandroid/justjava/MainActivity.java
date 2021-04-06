package com.example.jandroid.justjava;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int numberOfCoffee = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String[] emailID = new String[]{"cherry.sh1996@gmail.com"};

        CheckBox checkBoxWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox checkBoxChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);

        boolean checkBoxWhippedCreamState = checkBoxWhippedCream.isChecked();
        boolean checkBoxChocolateState = checkBoxChocolate.isChecked();

        String priceMessage = createOrderSummary(numberOfCoffee, checkBoxWhippedCreamState, checkBoxChocolateState);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, emailID);
        intent.putExtra(Intent.EXTRA_SUBJECT, "order");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    public void incrementOrderQuantityOrder(View view) {
        numberOfCoffee++;
        if (numberOfCoffee < 0 || numberOfCoffee >= 100) {
            Toast.makeText(this, "Please enater a value between 1 to 100", Toast.LENGTH_SHORT).show();
            return;
        }
        display(numberOfCoffee);
    }

    public void decrementOrderQuantity(View view) {
        numberOfCoffee--;
        if (numberOfCoffee < 1 || numberOfCoffee >= 100) {
            Toast.makeText(this, "Please enater a value between 1 to 100", Toast.LENGTH_SHORT).show();
            return;
        }
        display(numberOfCoffee);
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private int calculatePrice(int quantity, boolean checkBoxWhippedCreamState, boolean checkBoxChocolateState) {

        int price = 0;

        if (checkBoxWhippedCreamState == true && checkBoxChocolateState == true)
            price = quantity * 8;
        else if (checkBoxWhippedCreamState == true)
            price = quantity * 6;
        else if (checkBoxChocolateState == true)
            price = quantity * 7;
        else
            price = quantity * 5;

        return price;
    }

    private String createOrderSummary(int numberOfCoffee, boolean checkBoxWhippedCreamState, boolean checkBoxChocolateState) {
        TextView summaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        EditText simpleEditText = (EditText) findViewById(R.id.name_edit_view);
        String name = simpleEditText.getText().toString();

        String summary = "Name: " + name + "\n" +
                "Quantity: " + numberOfCoffee + "\n" +
                "Add Whipped Cream?" + checkBoxWhippedCreamState + "\n" +
                "Add Chocolate?" + checkBoxChocolateState + "\n" +
                "Total Price: " + calculatePrice(numberOfCoffee, checkBoxWhippedCreamState, checkBoxChocolateState) + "\n" +
                "Thank You!";
        summaryTextView.setText(summary);
        return summary;
    }


}