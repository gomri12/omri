package com.example.pressms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetContact extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_layout);
		final EditText nameV = (EditText)findViewById(R.id.name_text);
		final EditText phoneV = (EditText)findViewById(R.id.phone_text);
		Button done = (Button)findViewById(R.id.set_done);
		done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent returnIntent = new Intent();
				if (!nameV.getText().toString().equals("") && !phoneV.getText().toString().equals("")) {
					returnIntent.putExtra("name",nameV.getText().toString());
					returnIntent.putExtra("phone", phoneV.getText().toString());
					setResult(RESULT_OK,returnIntent);
					finish();
				} else {	
					Toast.makeText(getApplicationContext(), "Fields are empty DUDE", Toast.LENGTH_SHORT).show();
					setResult(RESULT_CANCELED, returnIntent);
					finish();
				}
				
			}
		});

	}

	
}
