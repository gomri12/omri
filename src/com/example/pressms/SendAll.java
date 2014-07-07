package com.example.pressms;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendAll extends Activity{
	String[] phone = new String[3];
	String[] contact = new String[3];
	boolean[] setup = new boolean[3];
	final String[] TAG ={"#name","#ων"};
	EditText msgAll;
	SharedPreferences prefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_layout);
		prefs = getPreferences(MODE_PRIVATE);
		Bundle extras = getIntent().getExtras();
		if (extras!=null){
			phone[0] = extras.getString("phone01");
			phone[1] = extras.getString("phone02");
			phone[2] = extras.getString("phone03");
			contact[0] = extras.getString("contact01");
			contact[1] = extras.getString("contact02");
			contact[2] = extras.getString("contact03");
			setup[0] = extras.getBoolean("setup01");
			setup[1] = extras.getBoolean("setup02");
			setup[2] = extras.getBoolean("setup03");
			for (int i=0; i<setup.length;i++){
				if (!setup[i])Toast.makeText(getApplicationContext(), ""+contact[i]+" : "+phone[i], Toast.LENGTH_SHORT).show();
			}
		}
		else {
			Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		Button all = (Button)findViewById(R.id.btn_send_all);
		msgAll = (EditText)findViewById(R.id.txt_all);
		msgAll.setText(prefs.getString("msg", ""));
		all.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				for (int i=0; i<setup.length;i++) if (!setup[i]) sendMessage(i);
				Toast.makeText(getApplicationContext(), "All was Sent", Toast.LENGTH_SHORT).show();
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("msg",msgAll.getText().toString());
				editor.commit();
			}
		});
	}
	public void sendMessage(int n) {
		// TODO Auto-generated method stub
		String msg = msgAll.getText().toString();
		msg = manipulate(msg,n);
		try{
			SmsManager smsManager = SmsManager.getDefault();
			Log.i("sms","smsManager created");
			smsManager.sendTextMessage(phone[n], null, msg, null, null);
		}catch(Exception e){
			Toast.makeText(getApplicationContext(), "Somthing went wrong: "+msg, Toast.LENGTH_SHORT).show();
			msgAll.setText(msg);
		}
	}
	private String manipulate(String msg,int n) {
		// TODO Auto-generated method stub
		String alterMsg=msg;
		for (int i=0;i<TAG.length;i++){
			if(msg.contains(TAG[i])){
				alterMsg = msg.replaceAll(TAG[i], contact[n]);
			}
		}
		return alterMsg;
	}
}
