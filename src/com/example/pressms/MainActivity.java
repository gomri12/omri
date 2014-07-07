package com.example.pressms;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/* 
 * Please note this is beta.
 * Code IS unreadable because I was lazy and noobie in android.
 * My GOD be with you
 * 
 */
public class MainActivity extends Activity {
	//saves and loads preferences (texts,phones,contacts and setup status)
	SharedPreferences prefs;
	
	//Keys for prefs Map
	final String CNT_1 = "contact01";
	final String CNT_2 = "contact02";
	final String CNT_3 = "contact03";
	final String MSG_1 = "massage01";
	final String MSG_2 = "massage02";
	final String MSG_3 = "massage03";
	final String STP_1 = "setup01";
	final String STP_2 = "setup02";
	final String STP_3 = "setup03";
	final String PHN_1 = "phone01";
	final String PHN_2 = "phone02";
	final String PHN_3 = "phone03";
	//End of Keys
	
	// three contacts currently supported,
	// future thoughts: dynamic ,add ,delete.
	Button[] contact = new Button[3];
	String[] phone = new String[3];
	Boolean[] setup = new Boolean[3];
	EditText[] msgTxt = new EditText[3];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// load prefs from memory , or load the default.
		prefs = getPreferences(MODE_PRIVATE);
		contact[0] = (Button)findViewById(R.id.contact01);
		contact[0].setText(prefs.getString(CNT_1, "set"));
		contact[1] = (Button)findViewById(R.id.contact02);
		contact[1].setText(prefs.getString(CNT_2, "set"));
		contact[2] = (Button)findViewById(R.id.contact03);
		contact[2].setText(prefs.getString(CNT_3, "set"));
		msgTxt[0] = (EditText)findViewById(R.id.message01);
		msgTxt[0].setText(prefs.getString(MSG_1, ""));
		msgTxt[1] = (EditText)findViewById(R.id.message02);
		msgTxt[1].setText(prefs.getString(MSG_2, ""));
		msgTxt[2] = (EditText)findViewById(R.id.message03);
		msgTxt[2].setText(prefs.getString(MSG_3, ""));
		setup[0] = prefs.getBoolean(STP_1, true);
		setup[1] = prefs.getBoolean(STP_2, true);
		setup[2] = prefs.getBoolean(STP_3, true);
		phone[0] = prefs.getString(PHN_1, "");
		phone[1] = prefs.getString(PHN_2, "");
		phone[2] = prefs.getString(PHN_3, "");
		// load end
		
		
		contact[0].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// if setup is needed - start SetContact activity
				if (setup[0]){
					Intent set = new Intent(MainActivity.this,SetContact.class);
					startActivityForResult(set,1); // code 1
				}
				else sendMessage(0);
			}
		});
		contact[1].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// if setup is needed - start SetContact activity
				if (setup[1]){
					Intent set = new Intent(MainActivity.this,SetContact.class);
					startActivityForResult(set,2); // code 2
				}
				else sendMessage(1);
			}
		});
		contact[2].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// if setup is needed - start SetContact activity
				if (setup[2]){
					Intent set = new Intent(MainActivity.this,SetContact.class);
					startActivityForResult(set,3); // code 3
				}
				else sendMessage(2);
			}
		});
		
	}
	/**Saves all preferences */
		@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(STP_1, setup[0]);
		editor.putBoolean(STP_2, setup[1]);
		editor.putBoolean(STP_3, setup[2]);

			editor.putString(MSG_1, msgTxt[0].getText().toString());
			editor.putString(CNT_1, contact[0].getText().toString());
			editor.putString(PHN_1, phone[0]);


			editor.putString(MSG_2, msgTxt[1].getText().toString());
			editor.putString(CNT_2, contact[1].getText().toString());
			editor.putString(PHN_2, phone[1]);


			editor.putString(MSG_3, msgTxt[2].getText().toString());
			editor.putString(CNT_3, contact[2].getText().toString());
			editor.putString(PHN_3, phone[2]);

		editor.commit();
	}
		/** sends message to contact in index n*/
		public void sendMessage(int n) {
				// TODO Auto-generated method stub
				String msg = msgTxt[n].getText().toString();
				try{
					SmsManager smsManager = SmsManager.getDefault();
					Log.i("sms","smsManager created");
					smsManager.sendTextMessage(phone[n], null, msg, null, null);
					Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_SHORT).show();
				}catch(Exception e){
					Toast.makeText(getApplicationContext(), "Somthing went wrong: "+msg, Toast.LENGTH_SHORT).show();
					msgTxt[n].setText(msg);
				}
			}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId()==R.id.action_settings){
			for(int i=0;i<3;i++){
				contact[i].setText("Set");
				msgTxt[i].setText("");
				setup[i]=true;
				phone[i]="";
			}
			Toast.makeText(getApplicationContext(), "All prefrences were Resetted", Toast.LENGTH_LONG).show();
		}
		else if (item.getItemId()==R.id.action_send_all) {
			Intent all = new Intent(MainActivity.this,SendAll.class);
			// pass to SendAll activity relative Info
			all.putExtra(CNT_1, contact[0].getText().toString());
			all.putExtra(CNT_2, contact[1].getText().toString());
			all.putExtra(CNT_3, contact[2].getText().toString());
			all.putExtra(PHN_1,phone[0]);
			all.putExtra(PHN_2,phone[1]);
			all.putExtra(PHN_3,phone[2]);
			all.putExtra(STP_1,setup[0]);
			all.putExtra(STP_2,setup[1]);
			all.putExtra(STP_3,setup[2]);
			Log.i("SendAllMenu","all good here before");
			startActivity(all);
			Log.i("SendAllMenu","all good here after");
		}
		return super.onOptionsItemSelected(item);
	}
	/** comes back from the SetContact */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            String name = data.getStringExtra("name");
	            String thePhone = data.getStringExtra("phone");
	            Toast.makeText(getApplicationContext(), ""+name, Toast.LENGTH_SHORT).show();
	            contact[0].setText(name);
	            phone[0] = thePhone;
	            Toast.makeText(getApplicationContext(), ""+phone[0], Toast.LENGTH_SHORT).show();
	            setup[0]=false;
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        	setup[0]=true;
	        }
	    }
	    else if (requestCode == 2) {
	        if(resultCode == RESULT_OK){
	            String name = data.getStringExtra("name");
	            String thePhone = data.getStringExtra("phone");
	            Toast.makeText(getApplicationContext(), ""+name, Toast.LENGTH_SHORT).show();
	            contact[1].setText(name);
	            phone[1] = thePhone;
	            Toast.makeText(getApplicationContext(), ""+phone[1], Toast.LENGTH_SHORT).show();
	            setup[1]=false;
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        	setup[1]=true;
	        }
	    }
	    else if (requestCode == 3) {
	        if(resultCode == RESULT_OK){
	            String name = data.getStringExtra("name");
	            String thePhone = data.getStringExtra("phone");
	            Toast.makeText(getApplicationContext(), ""+name, Toast.LENGTH_SHORT).show();
	            contact[2].setText(name);
	            phone[2] = thePhone;
	            Toast.makeText(getApplicationContext(), ""+phone[2], Toast.LENGTH_SHORT).show();
	            setup[2]=false;
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        	setup[2]=true;
	        }
	    }
	    
	}
	

}
