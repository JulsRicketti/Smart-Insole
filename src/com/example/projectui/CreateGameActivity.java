package com.example.projectui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateGameActivity extends Activity {

	final Context context =this;
	private static final String USERNAME = "USERNAME";
	private static final String GAME_TITLE = "GAME_TITLE";
	private static final String NUMBER_CALORIES = "NUMBER_CALORIES";
	private static final String HOURS = "HOURS";
	private static final String MINUTES = "MINUTES";
	
	
    private String username;
	private String gameTitle;
	private String gameType;
	private String numberCalories;
	private String hours;
	private String minutes;
	
	TextView usernameTV;
	EditText gameTitleET;
	EditText numberCaloriesET;
	EditText hoursET;
	EditText minutesET;
	
	TextView startDateTextView;
	TextView endDateTextView;
	
	Button playButton;
	DatabaseAdapter dbadapter;
	CheckBox timeBasedCheckBox;
	CheckBox calorieBasedCheckBox;
	

	
	/*These two variables will see if either the date or calorie settings are true
	 * and wont let the user finish creating his game unless one of them is true
	 * and that their respective fields are filled out*/
	boolean mustSetTime = false;
	boolean mustSetCalories = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);
		dbadapter = new DatabaseAdapter(this);
		dbadapter = dbadapter.open();
		String Username=username;
		if(savedInstanceState == null) {
			//they start with an empty string
			
			Username="";			
			gameTitle ="";
			numberCalories="";
			hours="";
			minutes="";
			
		}
		else {
		  Username =savedInstanceState.getString(USERNAME);			
			gameTitle= savedInstanceState.getString(GAME_TITLE);
			numberCalories = savedInstanceState.getString(NUMBER_CALORIES);
			hours = savedInstanceState.getString(HOURS);
			minutes= savedInstanceState.getString(MINUTES);
			}
		gameTitleET = (EditText)findViewById(R.id.gameTitleEditText);
		numberCaloriesET = (EditText)findViewById(R.id.numberCaloriesEditText);
		numberCaloriesET.setEnabled(false);
		numberCaloriesET.setClickable(false);
	//	numberCaloriesET.setActivated(false);
		
		gameTitleET.addTextChangedListener(gameTitleListener);
		numberCaloriesET.addTextChangedListener(numberCaloriesListener);
		
		hoursET = (EditText)findViewById(R.id.hoursEditText);
		hoursET.addTextChangedListener(hoursListener);
		hoursET.setEnabled(false);
		hoursET.setClickable(false);
		//hoursET.setActivated(false);
		
		minutesET = (EditText)findViewById(R.id.minutesEditText);
		minutesET.addTextChangedListener(minutesListener);
		minutesET.setEnabled(false);
		minutesET.setClickable(false);
	//	minutesET.setActivated(false);
			
		playButton = (Button)findViewById(R.id.playButton);
		
		timeBasedCheckBox = (CheckBox)findViewById(R.id.timeBasedCheckBox);
		calorieBasedCheckBox = (CheckBox)findViewById(R.id.calorieBasedCheckBox);
		
		setCheckBoxListeners();
		setButtonOnClickListeners();
		
	}

	private void setCheckBoxListeners() {
		timeBasedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					mustSetTime = true;
					hoursET.setEnabled(true);
					hoursET.setClickable(true);
				//	hoursET.setActivated(true);
					
					minutesET.setEnabled(true);
					minutesET.setClickable(true);
				//	minutesET.setActivated(true);
					gameType="TimeBased";
					if(mustSetCalories)
						gameType="TimeAndCalorieBased";
			
				}
				else {
					hoursET.setEnabled(false);
					hoursET.setClickable(false);
				//	hoursET.setActivated(false);
					hoursET.setText("");
					
					minutesET.setEnabled(false);
					minutesET.setClickable(false);
					//minutesET.setActivated(false);
					minutesET.setText("");
					gameType="CalorieBased";
				}
			}
		});
		
		calorieBasedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					mustSetCalories = true;
					numberCaloriesET.setEnabled(true);
					numberCaloriesET.setClickable(true);
					//numberCaloriesET.setActivated(true);
					
					gameType="CalorieBased";
					if(mustSetTime)
						gameType="TimeAndCalorieBased";
				}
				else {
					mustSetCalories = false;
					numberCaloriesET.setEnabled(false);
					numberCaloriesET.setClickable(false);
					//numberCaloriesET.setActivated(false);
					numberCaloriesET.setText("");
					gameType="TimeBased";
				}
				
			}
		});
	}
	

	private void setButtonOnClickListeners() {
		
		playButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean readyToGo = true;
				gameTitle=gameTitleET.getText().toString();
				hours=hoursET.getText().toString();
				minutes=minutesET.getText().toString(); 
				numberCalories=numberCaloriesET.getText().toString();
				dbadapter.EnterCreateGame(gameTitle, gameType, hours, minutes, numberCalories);

				if(gameTitle.equals("")) {
					Toast.makeText(getApplicationContext(), "Please create game name", Toast.LENGTH_SHORT).show();
					readyToGo=false;
				}
				if(!calorieBasedCheckBox.isChecked() && !timeBasedCheckBox.isChecked()){
					Toast.makeText(getApplicationContext(), "Please choose game type", Toast.LENGTH_SHORT).show();
					readyToGo = false;
				}

				if(calorieBasedCheckBox.isChecked() && numberCalories.equals("")){
					Toast.makeText(getApplicationContext(), "Please choose number of calories", Toast.LENGTH_SHORT).show();
					readyToGo=false;
				}
				
				if(timeBasedCheckBox.isChecked() && (hours.equals("") || minutes.equals(""))){
					Toast.makeText(getApplicationContext(), "Please choose the hours and minutes it will last", Toast.LENGTH_SHORT).show();
					readyToGo=false;
				}
				
				if(readyToGo){
					Toast.makeText(getApplicationContext(), "Game Creation Sucessfull"+dbadapter.getRowId(), Toast.LENGTH_SHORT).show();
					
					//dbadapter.close();
					Intent i  = new Intent(CreateGameActivity.this,ChooseGameActivity.class);
					startActivity(i);
				}
		}
		
		});
		
		//when the button is clicked, we start the dialog

	}
	
	private TextWatcher hoursListener = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			hours = s.toString();
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private TextWatcher minutesListener = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			minutes = s.toString();
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private TextWatcher gameTitleListener = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			gameTitle= s.toString();
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private TextWatcher numberCaloriesListener = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			numberCalories= s.toString();
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_game, menu);
		return true;
	}

}
