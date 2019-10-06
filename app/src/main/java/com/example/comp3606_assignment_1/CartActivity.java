package com.example.comp3606_assignment_1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.comp3606_assignment_1.models.CartModel;
import com.example.comp3606_assignment_1.models.DBHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
	String [] fields = {
			CartModel.CartEntry.ITEM,
			CartModel.CartEntry.TIME
	};
	String sortedOrder = CartModel.CartEntry.TIME + " DESC";

	SQLiteOpenHelper helper = new DBHelper(this);
	SQLiteDatabase db;

	ArrayList<String> itemList = new ArrayList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//
		/*String[] itemList = getResources().getStringArray(R.array.items_available);

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

		cartList.setAdapter(adapter); // adapter.add();*/

		db = helper.getReadableDatabase();

		Cursor res = db.query(CartModel.CartEntry.TABLE_NAME, fields, null, null, null, null, sortedOrder);

		String[] items = getResources().getStringArray((R.array.items_available));

		while (res.moveToNext()) {
			int itemId = res.getInt(res.getColumnIndex(CartModel.CartEntry.ITEM)) - 1;
			itemList.add(items[itemId]);
		}
		if(itemList.size() == 0) {
			itemList.add("Cart is empty.");
		}
		ListView lv = (ListView)findViewById(R.id.cart_list);
		ArrayAdapter<String>adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
		lv.setAdapter(adapter);
	}

	public void onCheckoutAttempt(View view) {
		if(itemList.size() == 0) {
			Snackbar.make(view, "Your cart is empty.", Snackbar.LENGTH_LONG).show();
		} else {
			Intent i = new Intent(CartActivity.this, CheckoutActivity.class);
			startActivity(i);
		}
	}
}
