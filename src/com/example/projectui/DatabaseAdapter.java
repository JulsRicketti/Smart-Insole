package com.example.projectui;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAdapter {
	private final String TAG="com.example.projectui";
	static final String DATABASE_NAME="newdb.txt";
	static final int DATABASE_VERSION=1;
	static final String KEY_ID="_ID";
	static final String TABLE_NAME="DataTable";
	static final String GAMETITLE="title";
	static final String GAMETYPE="type";
	static final String HOURS="hours";
	static final String MINUTES="minutes";
	static final String CALORIES="calories";
	private static String key=""; 
	public static long rowid1;
	static String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_NAME + "(" 
	+ KEY_ID + " INTEGER PRIMARY KEY," + GAMETITLE + " TEXT ," +  GAMETYPE + " TEXT ," + HOURS + " TEXT ," + MINUTES + " TEXT ," + CALORIES +  " TEXT)";
	  SQLiteDatabase db;
	  Context context;
	  DatabaseHelper mdb;
	  
	  public DatabaseAdapter(Context con)
	  {
		  context=con;
		  mdb = new DatabaseHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
		  
	  }
	  public  DatabaseAdapter  open() throws SQLException
	  {
		db=mdb.getWritableDatabase();
		return this;
	  }
	public void close() 
	{
		db.close();
	}

	public void EnterCreateGame(String gametitle, String gametype, String hours,String minutes,String numberCalories){
		ContentValues values1= new ContentValues();
		
		
		values1.put(GAMETITLE,gametitle);
		values1.put(GAMETYPE,gametype);
		values1.put(HOURS,hours);
		values1.put(MINUTES,minutes);
		values1.put(CALORIES,numberCalories);
		
		long rowid=db.insert(TABLE_NAME, null, values1);
		rowid1=rowid;
		Log.d(TAG, "IDD"+rowid1);
		
	}
	public long getID(long __id){
		
		rowid1=__id;
		return __id;
	}

	//stuff for the game, we select things based on the primary key
	
	public long getRowId() {
		return rowid1;
	}
	
	public String getGameTitle(String keyid) {
		Cursor cursor = db.query(TABLE_NAME, null, "_ID=?",new String[]{keyid},null, null,null,null);
		if(cursor.getCount()<1)
		{
			cursor.close();
			return "????";
		}
		cursor.moveToFirst();
		String gameTitle = cursor.getString(cursor.getColumnIndex(GAMETITLE));
		cursor.close();
		return gameTitle;
	}
	
	public String getNumberOfCalories (String keyid){
		Cursor cursor = db.query(TABLE_NAME, null, "_ID=?",new String[]{keyid},null, null,null,null);
		if(cursor.getCount()<1)
		{
			cursor.close();
			return "????";
		}
		cursor.moveToFirst();
		String numberOfCalories = cursor.getString(cursor.getColumnIndex(CALORIES));
		cursor.close();
		return numberOfCalories;
	}
	
	public String[] getTime (String keyid){
		Cursor cursor = db.query(TABLE_NAME, null, "_ID=?",new String[]{keyid},null, null,null,null);
		if(cursor.getCount()<1)
		{
			cursor.close();
			return null;
		}
		cursor.moveToFirst();
		String[] time = new String[2];
		//First is always hours and second is minutes
		time[0]= cursor.getString(cursor.getColumnIndex(HOURS));
		time[1]= cursor.getString(cursor.getColumnIndex(MINUTES));
		cursor.close();
		return time;
	}
	
	public Cursor getAllData() {
        // using simple SQL query
        return db.rawQuery("select * from " + TABLE_NAME, null);
    }
	public Cursor getallCols(String id) throws SQLException {
        Cursor mCursor = db.query(TABLE_NAME, new String[] { GAMETITLE,GAMETYPE,HOURS,MINUTES,
                 }, null, null, null, null, null);
        Log.e("getallcols zmv", "opening successfull"+mCursor);
        return mCursor;
    }
 
    public Cursor getColsById(String id) throws SQLException {
        Cursor mCursor = db.query(TABLE_NAME, new String[] { GAMETITLE,
               GAMETYPE,HOURS,MINUTES }, KEY_ID + " = " + id, null, null, null, null);
        Log.e("getallcols zmv", "opening successfull"+mCursor);
        return mCursor;
    }
	
		
	
	public String getGameType(String keyid){
		Cursor cursor = db.query(TABLE_NAME, null, KEY_ID+" = ?",new String[]{keyid},null, null,null,null);
		if(cursor.getCount()<1)
		{
			cursor.close();
			return "????";
		}
		cursor.moveToFirst();
		String gameType = cursor.getString(cursor.getColumnIndex(GAMETYPE));
		cursor.close();
		return gameType;
	}


}
