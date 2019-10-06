package com.example.comp3606_assignment_1;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
	ArrayList<String> listItems = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	ListView cartList;
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//
		String[] itemList = getResources().getStringArray(R.array.items_available);

		cartList = (ListView) findViewById(R.id.cart_list);
		sp = getApplicationContext().getSharedPreferences("cartData", MODE_PRIVATE);

		for(int i = 0; i < itemList.length; ++i) {
			if(sp.getInt("itemCart", -1) == i) {
				listItems.add(itemList[i]);
			}
		}

		if(listItems.size() == 0) {
			listItems.add("Cart is empty.");
		}

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				listItems);

		cartList.setAdapter(adapter); // adapter.add();
	}

}
