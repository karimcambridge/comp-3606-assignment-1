package com.example.comp3606_assignment_1.models;

import android.provider.BaseColumns;

public final class CartModel {
	private static final String INT_TYPE = " INTEGER";
	public static abstract class CartEntry implements BaseColumns {
		public static final String TABLE_NAME = "cart";
		public static final String ID = "id";
		public static final String ITEM = "item";
		public static final String TIME = "timecreated";
	}
	public static final String CREATE_TABLE =
		"CREATE TABLE " + CartEntry.TABLE_NAME + " (" +
		CartEntry.ID + INT_TYPE + " PRIMARY KEY, " +
		CartEntry.ITEM + INT_TYPE + " NOT NULL, " +
		CartEntry.TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
		");";
}
