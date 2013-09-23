

package com.example.projectui;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the main Activity that displays the current chat session.
 */
public class BluetoothActivity extends Activity {
	
	public static String gameNumber; //we use this to get information from the database as to which game we are playing
	
	//Stuff for the images:
	//int stepCounter = BluetoothChat.i; //this is where I am receiving data from the bluetooth class
	int counter =0;
	//int i=0;
	ImageView p, d; //player and dummy
	TextView text;
	
	private CountDownTimer countDownTimer;
	//private boolean timerHasStarted = false;
	private final long startTime =  1000; //(1 second)
	private final long interval = 1 * 1000; //(1 second)
    RelativeLayout.LayoutParams lp, lp2;
	//=======================================
    
    //Stuff for the game side of it:
    String gameType;
    boolean isCalorieBased =true; //this is only set for testing, the true answer will come from the database
    int objectiveOfCalories = 200; //just using 200 as an example
    int hours=0, minutes=0, time=100; //for time based games
    private final int distanceToFinish=200; //it could also be 190 (which is when the finish line is reached)
    private final int caloriesPerStep =2; //this was found on the internet, but may later be changed!
    
    int timeRemaining;
  //=======================================
	
    // Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;
   
public static int i=0; //this is the variable that will take the number of steps to the GameScreenActivity
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
Context context;
    // Layout Views
    private TextView mTitle;

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothSupportService mChatService = null;
    DatabaseAdapter dbAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");
        MediaPlayer mediaPlayer = MediaPlayer.create(BluetoothActivity.this, R.raw.sound_file_1);
        mediaPlayer.start();
        
        // Seta up the window layout
       requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main1);
       getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        // Set up the custom title
       mTitle = (TextView) findViewById(R.id.title_left_text);
       mTitle.setText(R.string.app_name);
       mTitle = (TextView) findViewById(R.id.title_right_text);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        
        dbAdapter =new DatabaseAdapter(this);
        dbAdapter=dbAdapter.open();
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
       setGame(); //uncomment for the real game to work
        
        //Stuff with the images:
       text= (TextView)findViewById(R.id.textView);
       p = (ImageView)findViewById(R.id.player);
       d= (ImageView)findViewById(R.id.dummy);
       
        lp = (android.widget.RelativeLayout.LayoutParams) p.getLayoutParams();
        lp2 = (android.widget.RelativeLayout.LayoutParams) d.getLayoutParams();
        
 		countDownTimer = new MyCountDownTimer(startTime, interval);
 		countDownTimer.start();
 	
        //================================================
    }
    
    //Getting stuff from the database in order to set the game:
    
    public void setGame() {
    	Log.d(TAG, "ERROR HERE");
    	gameType = dbAdapter.getGameType(gameNumber);
    	
    	if(gameType.equals("TimeAndCalorieBased")){
    		objectiveOfCalories = Integer.parseInt(dbAdapter.getNumberOfCalories(gameNumber));
    		hours = Integer.parseInt(dbAdapter.getTime(gameNumber)[0]);
    		minutes = Integer.parseInt(dbAdapter.getTime(gameNumber)[1]);
    	}
    	
    	if(gameType.equals("CalorieBased")){
    		objectiveOfCalories = Integer.parseInt(dbAdapter.getNumberOfCalories(gameNumber));
    		
    	}

    	if(gameType.equals("TimeBased")){
    		hours = Integer.parseInt(dbAdapter.getTime(gameNumber)[0]);
    		minutes = Integer.parseInt(dbAdapter.getTime(gameNumber)[1]);
    	}

    }

    DatabaseAdapter db;
    public void getData(){
		
		//list.clear();
		
		db = new DatabaseAdapter(this);
		try{
			db.open();
			Cursor cur = db.getAllData();
			int helper=0;
			while(helper<Integer.parseInt(gameNumber)){
				cur.moveToNext();
				String gametitle = cur.getString(1);
				String gametype = cur.getString(2);
			    String hours=cur.getString(3);
				String minutes=cur.getString(4);
				//list.add(gametitle+"  "+gametype+"  "+hours+"  "+minutes);
//			list.add(gametype);
//			list.add(hours);
//			list.add(minutes);
//			
				//hours = Integer.parseInt(minutes);
			}
		}
		finally{
			db.close();
			
		}	
}
    //This is the real game
public class MyCountDownTimer extends CountDownTimer {
		private MyCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		@Override
		public void onFinish() {
			counter++; //this also checks how many times the timer has gone around
			//test to move dummy
			lp2.setMargins(lp2.leftMargin, counter, lp2.rightMargin, lp2.bottomMargin);
		    d.setLayoutParams(lp2);
			if(gameType.equals("CalorieBased")){
				//the dummy is just randomly moving
				lp2.setMargins(lp2.leftMargin, counter, lp2.rightMargin, lp2.bottomMargin);
			    d.setLayoutParams(lp2);
				text.setText("Time gone by: "+interval*counter );
				lp.setMargins(lp.leftMargin, calculateDistance(i*caloriesPerStep), lp.rightMargin, lp.bottomMargin);
			    p.setLayoutParams(lp);
			    
			    //condition for player to win in calorie based games
			    if(calculateDistance(i*caloriesPerStep)==distanceToFinish){
					Intent i  = new Intent(BluetoothActivity.this,EndGameActivity.class);
					startActivity(i);

			    }
			}
			if(gameType.equals("TimeBased")) {
				//here the dummy moves according to time
				lp2.setMargins(lp2.leftMargin, calculateTime(), lp2.rightMargin, lp2.bottomMargin);
			    d.setLayoutParams(lp2);
				
			    
				if(timeRemaining==0){
					Intent i  = new Intent(BluetoothActivity.this,EndGameActivity.class);
					startActivity(i);
				}
			}
			
		    if(gameType.equals("TimeAndCalorieBased")){
		    	lp2.setMargins(lp2.leftMargin, calculateTime(), lp2.rightMargin, lp2.bottomMargin);
			    d.setLayoutParams(lp2);
		    	lp.setMargins(lp.leftMargin, calculateDistance(i*caloriesPerStep), lp.rightMargin, lp.bottomMargin);
			    p.setLayoutParams(lp);
			    
			    if(calculateDistance(i*caloriesPerStep)==distanceToFinish){
					Intent i  = new Intent(BluetoothActivity.this,EndGameActivity.class);
					startActivity(i);

			    }

			    
				if(timeRemaining==0){
					Intent i  = new Intent(BluetoothActivity.this,LoseGameActivity.class);
					startActivity(i);
				}

		    }

			countDownTimer = new MyCountDownTimer(startTime, interval);
			countDownTimer.start();
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
	}
	//this function will decide where the user is in the graphic interface
	//(where the picture is)
	//we are considering 2 calories per step, as it was answered on the internet (THIS MAY CHANGE!!!)
	int calculateDistance(int currentCalories) {
		
		return (distanceToFinish*currentCalories)/objectiveOfCalories;
		
	}

	int calculateTime(){
		return (int)(distanceToFinish*((interval*counter)/1000)/time);
	}
	
    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothSupportService.STATE_NONE) {
              // Start the Bluetooth chat services
              mChatService.start();
            }
        }
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        mChatService = new BluetoothSupportService(this, mHandler);
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
   private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothSupportService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

   }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothSupportService.STATE_CONNECTED:
                    mTitle.setText(R.string.title_connected_to);
                    mTitle.append(mConnectedDeviceName);
               //     mConversationArrayAdapter.clear();
                    break;
                case BluetoothSupportService.STATE_CONNECTING:
                    mTitle.setText(R.string.title_connecting);
                    break;
                case BluetoothSupportService.STATE_LISTEN:
                case BluetoothSupportService.STATE_NONE:
                    mTitle.setText(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
             
                if(readMessage!=null){
                	i++;
                	 Log.i(TAG, "VALUES"+i);
                	 
                }
             
                
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }

		
    };

    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // Get the BLuetoothDevice object
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                mChatService.connect(device);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                //setupChat();
            } else {
                // User did not enable Bluetooth or an error occured
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.scan:
            // Launch the DeviceListActivity to see devices and do scan
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            return true;
        case R.id.discoverable:
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
            
       
        }
        return false;
    }

}