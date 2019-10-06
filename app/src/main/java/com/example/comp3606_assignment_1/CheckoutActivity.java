package com.example.comp3606_assignment_1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.example.comp3606_assignment_1.models.CartModel;
import com.example.comp3606_assignment_1.models.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
	String [] fields = {
			CartModel.CartEntry.ITEM,
			CartModel.CartEntry.TIME
	};
	String sortedOrder = CartModel.CartEntry.TIME + " DESC";

	SQLiteOpenHelper helper = new DBHelper(this);
	SQLiteDatabase db;

	ArrayList<String> itemList = new ArrayList();

	TextView txtFinalDisplay;
	EditText txtZipCode, txtCouponCode;
	CheckBox btnVat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		txtFinalDisplay = (TextView)findViewById(R.id.txt_checkout_display);
		txtZipCode = (EditText)findViewById(R.id.txt_zipcode);
		txtCouponCode = (EditText)findViewById(R.id.txt_couponcode);
		btnVat = (CheckBox)findViewById(R.id.btn_vat);

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
		ListView lv = (ListView)findViewById(R.id.checkout_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
		lv.setAdapter(adapter);
	}

	public void onFinalCheckout(View v) {
		String zipCodeStr = "", couponCode = "";
		int zipCode = -1;
		boolean vat = false;
		List validCouponList = Arrays.asList("SPECIALDISC05", "SPECIALDISC10");

		double finalPrice = 0.0, totalItemPrice = 0.0, shippingCharge = 1.0;

		if(txtZipCode != null) {
			zipCodeStr = txtZipCode.getText().toString();
			if(zipCodeStr.matches("-?\\d+")) {
				zipCode = Integer.parseInt(zipCodeStr);
				if(zipCode >= 33100 && zipCode <= 34000) {
					shippingCharge = 4.95;
				}
				else if(zipCode >= 34001 && zipCode <= 35000) {
					shippingCharge = 5.95;
				}
			}
		}
		if(txtCouponCode != null) {
			couponCode = txtCouponCode.getText().toString();
			if(validCouponList.contains(couponCode)) {
				if(couponCode == "SPECIALDISC05") {
					totalItemPrice *= 0.95;
				}
				else if(couponCode == "SPECIALDISC10") {
					totalItemPrice *= 0.90;
				}
			}
			else if(!couponCode.isEmpty()){
				Toast.makeText(getApplicationContext(),"INVALID COUPON CODE", Toast.LENGTH_SHORT).show();
			}
		}
		if(btnVat != null) {
			vat = btnVat.isChecked();
		}

		finalPrice = shippingCharge;
		if(vat) {
			finalPrice *= 1.10;
		}
		txtFinalDisplay.setText(
				"zipCode: " + zipCode +
						"\tcouponCode: " + couponCode +
						"\tvat: " + vat +
						"\tFinal Price: $" + finalPrice
		);
	}
}
