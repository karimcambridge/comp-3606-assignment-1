package com.example.comp3606_assignment_1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.comp3606_assignment_1.models.CartModel;
import com.example.comp3606_assignment_1.models.DBHelper;
import com.example.comp3606_assignment_1.models.ItemModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		final SQLiteDatabase db = (new DBHelper(this)).getReadableDatabase();
		ListView lv = (ListView)findViewById(R.id.items_list);
		ArrayList<Map> items = new ArrayList<>();

		Cursor i = db.query(ItemModel.ItemEntry.TABLE_NAME,
			new String[]{ItemModel.ItemEntry.ID,
			ItemModel.ItemEntry.NAME,
			ItemModel.ItemEntry.DESC,
			ItemModel.ItemEntry.PRICE,
			ItemModel.ItemEntry.IMAGE},
			null, null, null, null, null
		);

		while(i.moveToNext()) {
			int id = i.getInt(i.getColumnIndex(ItemModel.ItemEntry.ID));
			String name = i.getString(i.getColumnIndex(ItemModel.ItemEntry.NAME));
			String desc = i.getString(i.getColumnIndex(ItemModel.ItemEntry.DESC));
			double price = i.getDouble(i.getColumnIndex(ItemModel.ItemEntry.PRICE));
			int imageId = i.getInt(i.getColumnIndex(ItemModel.ItemEntry.IMAGE));

			Map map = new HashMap();
			map.put("itemid", id);
			map.put("name", name);
			map.put("desc", desc);
			map.put("price", price);
			map.put("image", imageId);

			items.add(map);
		}
		ArrayAdapter<Map> adapter = new ItemAdapter(this, items);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent(ItemsActivity.this, ItemDetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("itemid", position);
				i.putExtras(bundle);
				startActivity(i);
			}
		});
	}

	public class ItemAdapter extends ArrayAdapter<Map> {
		public ItemAdapter(Context context, ArrayList<Map> items) { super(context, 0, items); }

		public View getView(int position, View v, ViewGroup p) {
			v = LayoutInflater.from(getContext()).inflate(R.layout.content_item_details, null);
			Map iMap = getItem(position);
			String name = (String)iMap.get("name");
			String desc = (String)iMap.get("desc");
			double price = (double)iMap.get("price");
			int imageId = (Integer)iMap.get("image");
			((TextView)v.findViewById(R.id.txt_name)).setText(name);
			((TextView)v.findViewById(R.id.txt_description)).setText(desc);
			((TextView)v.findViewById(R.id.txt_price)).setText("$" + new Double(price).toString());
			((ImageView)v.findViewById(R.id.img_icon)).setImageResource(imageId);

			Button cartButton = (Button)v.findViewById(R.id.btn_add_cart);
			cartButton.setTag(position);
			cartButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int position = (Integer) view.getTag();
					// Access the row position here to get the correct data item
					Map iMap = getItem(position);
					// Do what you want here...

					int itemid = (Integer)iMap.get("itemid");

					SQLiteOpenHelper helper = new DBHelper(ItemsActivity.this);
					final SQLiteDatabase db = helper.getWritableDatabase();

					ContentValues cv = new ContentValues();
					cv.put(CartModel.CartEntry.ITEM, itemid);

					final long count = DatabaseUtils.queryNumEntries(db, CartModel.CartEntry.TABLE_NAME);

					if(count < 5) {
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
						}

					} else {
						Snackbar.make(view, "Your cart is full!", Snackbar.LENGTH_LONG).show();
					}
				}
			});
			return v;
		}
	}

	public void addToCart(View v) {
		/*
		switch(v.getId()) {
			case R.id.btn_add_cart:
				View parentRow = (View) v.getParent();
				ListView listView = (ListView) parentRow.getParent();
				final int position = listView.getPositionForView(parentRow);

				break;
		}*/
	}
}
