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
import android.widget.TextView;

import com.example.comp3606_assignment_1.models.CartModel;
import com.example.comp3606_assignment_1.models.DBHelper;
import com.example.comp3606_assignment_1.models.ItemModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
	String [] cartFields = {
			CartModel.CartEntry.ITEM,
			CartModel.CartEntry.TIME
	};
	String sortedOrder = CartModel.CartEntry.TIME + " DESC";

	String [] itemFields = {
			ItemModel.ItemEntry.ID,
			ItemModel.ItemEntry.PRICE,
			ItemModel.ItemEntry.IMAGE
	};

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

		double totalItemPrice = 0.0;

		Cursor itemCartRes = db.rawQuery(
			"SELECT cart.item, items.id, items.name, items.price FROM " + ItemModel.ItemEntry.TABLE_NAME + ", " + CartModel.CartEntry.TABLE_NAME + " WHERE cart.item = items.id", null);

		while (itemCartRes.moveToNext()) {
			int itemId = itemCartRes.getInt(itemCartRes.getColumnIndex(CartModel.CartEntry.ITEM));
			String name = itemCartRes.getString(itemCartRes.getColumnIndex(ItemModel.ItemEntry.NAME));
			double price = itemCartRes.getDouble(itemCartRes.getColumnIndex(ItemModel.ItemEntry.PRICE));
			totalItemPrice += price;
			itemList.add(name + " ($" + price + ")");
		}
		if(itemList.size() == 0) {
			itemList.add("Cart is empty.");
		}
		TextView txtTotalPrice = (TextView)findViewById(R.id.txt_cartTotalPrice);
		txtTotalPrice.setText("\tTotal Price: $" + totalItemPrice);

		ListView lv = (ListView)findViewById(R.id.cart_list);
		ArrayAdapter<String>adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
		lv.setAdapter(adapter);
	}

	public void onCheckoutAttempt(View view) {
		if(itemList.size() == 1 && itemList.get(0).equals("Cart is empty.")) {
			Snackbar.make(view, "Your cart is empty.", Snackbar.LENGTH_LONG).show();
		} else {
			Intent i = new Intent(CartActivity.this, CheckoutActivity.class);
			startActivity(i);
		}
	}
}
