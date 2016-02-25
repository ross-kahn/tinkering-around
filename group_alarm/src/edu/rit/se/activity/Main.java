package edu.rit.se.activity;

import edu.rit.se.R;
import android.app.Activity;
import android.os.Bundle;

public class Main extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}
}
