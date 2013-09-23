package com.example.projectui;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.view.LayoutInflater;

public class AddPlayersActivity extends Activity {

	/*Continue with Derek Bana's video:
	 * http://www.youtube.com/watch?v=Kt5wIEF7Lls&list=PLGLfVvz_LVvQUjiCc8lUT9aO0GsWA4uNe
	 * (android tutorial 7)
	 * around 11:24*/
	
	Button doneButton;
	
	TableLayout addPlayersTableLayout;
	
	ScrollView addPlayersScrollView;
	
	String[] players = new String[]{ "Blah", "Whatever", "Test", "Nipah!"}; //this is where we will store the players from the database
	final ArrayList<String> list = new ArrayList<String>();

	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_players);

		addPlayersTableLayout = (TableLayout)findViewById(R.id.TableLayout1);

		
		addPlayersScrollView = (ScrollView)findViewById(R.id.playersScrollView);
		
		doneButton = (Button) findViewById(R.id.addButton);
		doneButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(AddPlayersActivity.this, CreateGameActivity.class);
				startActivity(i);
				
			}
		});
		
		
		for (int i=0; i<players.length; i++){
			insertPlayerInScrollView();
			counter++;
		}
		
		
	}

	int counter =0;
	private void insertPlayerInScrollView() {
		LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View newPlayerRow = inflator.inflate(R.layout.add_player_row, null);
		
		TextView newPlayerTextView = (TextView) newPlayerRow.findViewById(R.id.addPlayerTextView);

			
		Button addPlayersAddButton = (Button)newPlayerRow.findViewById(R.id.addPlayersAddButton);
		addPlayersAddButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TableRow tableRow = (TableRow)v.getParent();
				//add this person as the friend to database
				
				//remove it from the list since its already in the database
				addPlayersTableLayout.removeView(tableRow);	
				
			}
		});
		
		newPlayerTextView.setText(players[counter]);
		addPlayersTableLayout.addView(newPlayerRow, counter);


	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_players, menu);
		return true;
	}

}
