package com.example.projectui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.Toast;

public class ChooseGameActivity extends Activity {
	//Stuff for the layout:
	TableLayout chooseGameTableLayout;
	DatabaseAdapter dbAdapter;
	ScrollView chooseGameScrollView;
	
	static ArrayList<String> list = new ArrayList<String>();
	//============================================================
	
	ListView list1;
	DatabaseAdapter db;
	
	
	 //this is where we will store the players from the database
	ArrayList<String> newlist;
ArrayAdapter<String> arrayadapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_game);
		list1= (ListView)findViewById(R.id.dblist);
		list = new ArrayList<String>();
		newlist = new ArrayList<String>();
		getData();

        dbAdapter =new DatabaseAdapter(this);
        dbAdapter=dbAdapter.open();
	}
	
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
	}
	
	public void getData(){
		
		list.clear();
		
		db = new DatabaseAdapter(this);
		try{
			db.open();
			Cursor cur = db.getAllData();
			while(cur.moveToNext()){
				String gametitle = cur.getString(1);
				String gametype = cur.getString(2);
			    String hours=cur.getString(3);
				String minutes=cur.getString(4);
				list.add(gametitle+"  "+gametype+"  "+hours+"  "+minutes);
//			list.add(gametype);
//			list.add(hours);
//			list.add(minutes);
//			
			}
		}
		finally{
			db.close();
			
		}		
		setDataintoList();
		
	}

	
	
	private void setDataintoList(){
		String[] from = new String[]{"col_1","col_2","col_3","col4"};
		int[] to = new int[]{R.id.col1tv,R.id.col2tv};
		List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
		for(int i=0;i<list.size();i++){
			
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("col_1",list.get(i));
			map.put("col2",list.get(i));
			fillMaps.add(map);
			
			
		}
		SimpleAdapter adapter = new SimpleAdapter(this,fillMaps,R.layout.custom_title1,from,to);
		list1.setAdapter(adapter);
		
		list1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				BluetoothActivity.gameNumber = Integer.toString(position+1);
				//Toast.makeText(getApplicationContext(), "Game Number: "+BluetoothActivity.gameNumber, Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), "Game Name: "+dbAdapter.getGameTitle(BluetoothActivity.gameNumber), Toast.LENGTH_SHORT).show();
				Intent i  = new Intent(ChooseGameActivity.this,BluetoothActivity.class);
				startActivity(i);

			}
		});
	}
		
}

