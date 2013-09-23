package com.example.projectui;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	public DatabaseHelper(Context context,String name,CursorFactory cursofac,int version)
	{
		super(context,name,cursofac,version);
	}

	@Override
	public void onCreate(SQLiteDatabase _db) {
	
	_db.execSQL(DatabaseAdapter.CREATE_CATEGORIES_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
		
	Log.w( "DataAdapter","Upgrading Database from"+oldVersion+"to"+newVersion);
	_db.execSQL("Drop Table if Exists"+DatabaseAdapter.TABLE_NAME);
	onCreate(_db);
		
	}

	public Cursor query(String tableName, String[] strings, Object object,
			Object object2, Object object3, Object object4, Object object5) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cursor query(boolean b, String tableName, String[] strings,
			String string, Object object, Object object2, Object object3,
			Object object4, Object object5) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
