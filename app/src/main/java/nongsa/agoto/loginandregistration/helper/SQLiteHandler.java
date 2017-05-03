/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package nongsa.agoto.loginandregistration.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// Login table name
	private static final String TABLE_USER = "user";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_UID = "uid";
	private static final String KEY_EXP = "exp";
	private static final String KEY_NATION = "nation";
	private static final String KEY_GROW = "grow";
	private static final String KEY_PHONE = "phone";
	private static final String KEY_AUTH = "auth";
	private static final String KEY_INTRO = "intro";
	private static final String KEY_SUBNATIONA = "subNationA";
	private static final String KEY_SUBNATIONB = "subNationB";

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
				+ KEY_EXP + " INTEGER,"
				+ KEY_NATION + " TEXT," + KEY_GROW + " TEXT,"
				+ KEY_PHONE + " TEXT," + KEY_AUTH + " INTEGER,"
				+ KEY_INTRO + " TEXT," + KEY_SUBNATIONA + " TEXT," + KEY_SUBNATIONB + " TEXT);";
		db.execSQL(CREATE_LOGIN_TABLE);

		Log.d(TAG, "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);
	}


	/**
	 * Storing user details in database
	 * */

	public void setAuth(int auth){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("update "+TABLE_USER+" set auth="+auth);
		System.out.println("guntwo :1 "+auth);
		db.close();

	}
	public int getAuth(){
		int id = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor result = db.rawQuery("select auth from "+TABLE_USER, null);
		result.moveToFirst();
		while(!result.isAfterLast()){
			id = result.getInt(0);
			result.moveToNext();
		}
		result.close();
		System.out.println("guntwo :2 "+id);
		db.close();
		return id;

	}
	public void addUser(String name, String email, String uid, int exp, String nation, String grow, String phone, int auth, String intro, String subNationA, String subNationB) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name); // Name
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_UID, uid); // uid
		values.put(KEY_EXP, exp);
		values.put(KEY_NATION, nation);
		values.put(KEY_GROW, grow);
		values.put(KEY_PHONE, phone);
		values.put(KEY_AUTH, auth);
		values.put(KEY_INTRO, intro);
		values.put(KEY_SUBNATIONA, subNationA);
		values.put(KEY_SUBNATIONB, subNationB);

		// Inserting Row
		long id = db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("name", cursor.getString(1));
			user.put("email", cursor.getString(2));
			user.put("uid", cursor.getString(3));
			user.put("exp", cursor.getString(4));
			user.put("nation", cursor.getString(5));
			user.put("grow", cursor.getString(6));
			user.put("phone", cursor.getString(7));
			user.put("auth", cursor.getString(8));
			user.put("intro", cursor.getString(9));
			user.put("subNationA", cursor.getString(10));
			user.put("subNationB", cursor.getString(11));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return user;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

}
