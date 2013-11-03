package com.mcalpinedevelopment.calculatepay.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class Database {
		// Activity reference
		Context _context;
		
		private static Database _instance;
		
		private final String DATABASE_NAME = "EmployeeData.db";
		private final String TABLE_NAME = "employee";
		
		// Table columns
		private final String NAME = "name";
		private final String PAY_PERIOD = "pay_period";
		private final String TAX_CODE = "tax_code";
		private final String KIWI_SAVER = "kiwi_saver";
		private final String STUDENT_LOAN = "student_loan";
		private final String HOURLY_PAY = "hourly_pay";

		
		private final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
				+ NAME 			+ " TEXT,"	
				+ PAY_PERIOD 	+ " TEXT,"
				+ TAX_CODE 		+ " TEXT,"
				+ KIWI_SAVER 	+ " TEXT,"
				+ STUDENT_LOAN 	+ " TEXT,"
				+ HOURLY_PAY 	+ " TEXT"
				+ ");";
		
		
		private Database(Context context) {
			_context = context;
			createDatabase();	
		}
		
		public static Database getDatabase(Context context) {
			if (_instance == null) {
				_instance = new Database(context);
			}
			return _instance;
		}
		
		/**
		 * Sets the _instance field to null so that a new instance can be created
		 */
		public static void destroyInstance() {
			_instance = null;
		}
		
		private void createDatabase() {
			SQLiteDatabase db;
			try {
				db = openDatabase();
				db.execSQL(DATABASE_CREATE);
				db.close();
			} catch (Exception e){ 
				Log.e("myDatabase","ERROR: Creating the database");
				e.printStackTrace();
			}
		}
		
		private void insertData(String name, String payPeriod, String taxCode, String kiwiSaver, String studentLoan, String hourlyPay) {
			Log.d("myDatabase","Insert data");
			SQLiteDatabase db;
			db =  openDatabase();
			
			ContentValues values = new ContentValues();
			values.put(NAME, name);
			values.put(PAY_PERIOD, payPeriod);
			values.put(TAX_CODE, taxCode);
			values.put(KIWI_SAVER, kiwiSaver);
			values.put(STUDENT_LOAN, studentLoan);
			values.put(HOURLY_PAY, hourlyPay);

			db.insert(TABLE_NAME, null, values);
			
			db.close(); // Always close the database
		}
		
		public void updateData(String name, String payPeriod, String taxCode, String kiwiSaver, String studentLoan, String hourlyPay) {
			if (getName() == null) {
				insertData(name, payPeriod, taxCode, kiwiSaver, studentLoan, hourlyPay);
				return;
			}
			Log.d("myDatabase","Update data");
			SQLiteDatabase db;
			db =  openDatabase();
			
			ContentValues values = new ContentValues();
			values.put(NAME, name);
			values.put(PAY_PERIOD, payPeriod);
			values.put(TAX_CODE, taxCode);
			values.put(KIWI_SAVER, kiwiSaver);
			values.put(STUDENT_LOAN, studentLoan);
			values.put(HOURLY_PAY, hourlyPay);
			
			db.update(TABLE_NAME, values,NAME + "=" + "\'"+getName()+"\'", null);
			
			db.close();
		}
		
		public String[] getEmployeeDetails() {
			SQLiteDatabase db;
			db =  openDatabase();		
			Cursor rows = null;
			
			try {
				rows = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null); //db.rawQuery("SELECT * FROM "+ TABLE_NAME + " WHERE " + NAME + " == " + "\'"+getName()+"\'", null);
			} catch (SQLiteException e) {
				return null;
			}
			if (rows.moveToFirst() == false) {
				Log.d("myDatabase", "First row empty");
			} else {
				Log.d("myDatabase", "Not empty");
			}
			
			String[] details = null;
			
			try {
				Log.d("myDatabase", "Database name: " + rows.getString(0));
				Log.d("myDatabase", "Database pay period: " + rows.getString(1));
				Log.d("myDatabase", "Database tax code: " + rows.getString(2));
				Log.d("myDatabase", "Database kiwi saver: " + rows.getString(3));
				Log.d("myDatabase", "Database student loan: " + rows.getString(4));
				Log.d("myDatabase", "Database hourly pay: " + rows.getString(5));
				details = new String[]{rows.getString(0), rows.getString(1), rows.getString(2), rows.getString(3), rows.getString(4), rows.getString(5)};
			} catch (CursorIndexOutOfBoundsException e) {
			}
			
			db.close();
			
			return details;
		}
		
		
		/**
		 * @return The name at the top of the database
		 * null if no table
		 */
		public String getName() {
			SQLiteDatabase db;
			db =  openDatabase();	
			Cursor rows = null;
			try {
				rows = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
			} catch (SQLiteException e) {
				return null;
			}
			String name = null;
			if (rows.moveToFirst() == false) {
				Log.e("myDatabase", "First row empty");
			} else {
				Log.d("myDatabase", "Not empty");
			}
			try {
				name = rows.getString(0);
			} catch (CursorIndexOutOfBoundsException e) {
				Log.e("myDatabase", "There is no value in row 0");
			}
			db.close(); // Always close the database
			return name;		
		}
		
		
		
		private SQLiteDatabase openDatabase() {
			return _context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
		}
		
		/**
		 * @return true if the database was successfully deleted
		 */
		public boolean deleteDatabase() {
			return _context.deleteDatabase(DATABASE_NAME);
		}
}
