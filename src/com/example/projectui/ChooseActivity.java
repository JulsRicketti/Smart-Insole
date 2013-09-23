package com.example.projectui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseActivity extends Activity {
static final String EXTRA_MESSAGE="com.example.projectui";
	Button createGameButton;
	Button goToGameButton;
	Button sendRequestButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);
		
		createGameButton = (Button)findViewById(R.id.createGameButton);
		goToGameButton = (Button)findViewById(R.id.goToGameButton);
		sendRequestButton = (Button)findViewById(R.id.sendRequestButton);
		setButtonOnClickListeners();
		//Intent sendmessage = getIntent();
	//	String message= sendmessage.getStringExtra(LoginActivity.EXTRA_MESSAGE);
		
		//text.setAllCaps(true);
		
		
		
	}
	
	
		
		
	
	
	private void setButtonOnClickListeners() {
		
		createGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ChooseActivity.this, CreateGameActivity.class);
				startActivity(i);
				
			}
		});
		
		goToGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ChooseActivity.this, ChooseGameActivity.class);
				startActivity(i);
			}
		});
	
		sendRequestButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//deleted everything facebook related, remember to put them back!
//				Intent i = new Intent(ChooseActivity.this, MainActivity.class);
//				startActivity(i);
			}
		});
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose, menu);
		return true;
	}

}
