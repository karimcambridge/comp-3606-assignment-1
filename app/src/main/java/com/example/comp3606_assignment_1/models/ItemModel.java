package com.example.comp3606_assignment_1.models;

import android.provider.BaseColumns;

import com.example.comp3606_assignment_1.R;

public final class ItemModel {
	private static final String TEXT_TYPE = " TEXT";
	private static final String INT_TYPE = " INTEGER";
	private static final String REAL_TYPE = " REAL";
	public static abstract class ItemEntry implements BaseColumns {
		public static final String TABLE_NAME = "items";
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String DESC = "desc";
		public static final String PRICE = "price";
		public static final String IMAGE = "image";
	}
	public static final String CREATE_TABLE =
		"CREATE TABLE " + ItemEntry.TABLE_NAME + " (" +
		ItemEntry.ID + INT_TYPE + " PRIMARY KEY, " +
		ItemEntry.NAME + TEXT_TYPE + " NOT NULL, " +
		ItemEntry.DESC + TEXT_TYPE + " NOT NULL, " +
		ItemEntry.PRICE + REAL_TYPE + " NOT NULL, " +
		ItemEntry.IMAGE + INT_TYPE +
		");";

	public static String CREATE_ITEMS() {
		String str = "";
		str += "INSERT INTO " + ItemEntry.TABLE_NAME + "(name, desc, price, image) VALUES ";
		str += " ('Formal Shirt', 'An ugly shirt that caucasians wear in an attempt to impress the female gender', 50, " + R.drawable.formal_shirt + "),";
		str += " ('Shorts', 'An ugly shorts', 100, " + R.drawable.shorts + "),";
		str += " ('Laptop', 'A device used to surf the internet and do various tasks', 150, " + R.drawable.laptop + "),";
		str += " ('Printer', 'A device used to print material', 200, " + R.drawable.printer + ");";
		return str;
	}
}