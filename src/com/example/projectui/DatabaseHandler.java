package com.example.projectui;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_NAME="DatabaseShoe.db";
	private static final String TABLE_NAME="calories";
	private static final String KEY_ID="Id";
	private static final String USERNAME="username";
	private static final String PASSWORD="password";
	private static final String CONFIRM_PASSWORD="confirmpassword";
	private static final String EMAIL="email";
	



public DatabaseHandler(Context context)
{
	super(context,DATABASE_NAME,null,DATABASE_VERSION);
}

@Override
public void onCreate(SQLiteDatabase db)
{
	String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_NAME + "("
    		+ KEY_ID + " INTEGER PRIMARY KEY," + USERNAME + " TEXT,"+ PASSWORD +" TEXT,"+CONFIRM_PASSWORD + " TEXT," + EMAIL + " TEXT)";
	
	db.execSQL(CREATE_CATEGORIES_TABLE);
}


@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	db.execSQL("IF TABLE EXISTS"+TABLE_NAME);
	onCreate(db);
	
}
public void insertdata(String username,String password,String confirmpassword,String email)
{
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put(USERNAME, username);
	values.put(PASSWORD, password);
	values.put(CONFIRM_PASSWORD,confirmpassword);
	values.put(EMAIL, email);
	db.insert(TABLE_NAME,null,values);
	db.close();	

}


public List<String> getAllData(){
	List<String> Datas =new ArrayList<String>();
	String selectQuery = "SELECT *FROM "+TABLE_NAME;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor =db.rawQuery(selectQuery, null);
	if(cursor.moveToFirst())
	{
		do
		{
         Datas.add(cursor.getString(0));
         Datas.add(cursor.getString(1));
         Datas.add(cursor.getString(2));
         Datas.add(cursor.getString(3));
         Log.d("Inserting","VALUES"+Datas);
         
		}while(cursor.moveToNext());

	}
	
cursor.close();
db.close();
return Datas;
}
}