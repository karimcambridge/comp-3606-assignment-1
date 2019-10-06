package com.example.comp3606_assignment_1;

import android.content.ContentValues;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.example.comp3606_assignment_1.models.CartModel;
import com.example.comp3606_assignment_1.models.DBHelper;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDetailsActivity extends AppCompatActivity {
	//private int item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_details);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		/*Bundle bundle = getIntent().getExtras();
		if(bundle.containsKey("itemid")) {
			int itemid = bundle.getInt("itemid");
			//this.item = itemid;
			int defaultVal = 0;
			String[] itemList = getResources().getStringArray(R.array.items_available);
			String[] itemDescriptions = getResources().getStringArray(R.array.items_description);
			String itemName = itemList[itemid];
			String itemDescription = itemDescriptions[itemid];
			TypedArray itemImages = getResources().obtainTypedArray(R.array.item_images);
			// Set the text view to the item name
			TextView txtName = (TextView)findViewById(R.id.txt_name);
			txtName.setText(itemName);
			// Set the text view with the description
			TextView txtDescription = (TextView)findViewById(R.id.txt_description);
			txtDescription.setText(itemDescription);
			// Set the image view to the item img
			ImageView imgView = (ImageView)findViewById(R.id.img_icon);
			imgView.setImageResource(itemImages.getResourceId(itemid, defaultVal));
		}*/
	}

	public void addToCart(View view) {
		/*int item = this.item;

		SQLiteOpenHelper helper = new DBHelper(this);
		final SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(CartModel.CartEntry.ITEM, item);

		final long cartId = db.insert(CartModel.CartEntry.TABLE_NAME, null, cv);

		if(cartId != -1) {
			Snackbar.make(view, "Item successfully added to the cart", Snackbar.LENGTH_LONG)
					.setAction("UNDO", new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							try {
								db.delete(CartModel.CartEntry.TABLE_NAME, CartModel.CartEntry.ITEM + "=" + cartId, null);
								Snackbar.make(v, "Removed item from Cart", Snackbar.LENGTH_LONG).show();
							} catch(Exception e) {
								e.printStackTrace();
								Snackbar.make(v, "Unable to remove Item from Cart", Snackbar.LENGTH_LONG).show();
							}
						}
					}).show();
		} else {
			Snackbar.make(view, "Unable to add Item to Cart", Snackbar.LENGTH_LONG).show();
		}*/
	}
}
