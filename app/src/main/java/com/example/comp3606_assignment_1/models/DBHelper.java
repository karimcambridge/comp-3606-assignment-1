package com.example.comp3606_assignment_1.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "NewToyDB";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CartModel.CREATE_TABLE);
		db.execSQL(ItemModel.CREATE_TABLE);
		db.execSQL(ItemModel.CREATE_ITEMS());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// upgrade db code
		if(oldVersion < 2) {
			db.execSQL(ItemModel.CREATE_TABLE);
			db.execSQL(ItemModel.CREATE_ITEMS());
		}
	}
}
