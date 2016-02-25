package edu.rit.se.activity;

import edu.rit.se.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class LoginActivity extends Activity {
	 String userName, passWord;
     EditText username, password;
     Button login;    
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.login);
       // UI elements gets bind in form of Java Objects
       username = (EditText)findViewById(R.id.username);
       password = (EditText)findViewById(R.id.password);
       login = (Button)findViewById(R.id.login);
       // now we have got the handle over the UI widgets
       // setting listener on Login Button
       // i.e. OnClick Event
       login.setOnClickListener(loginListener);  
   }
   private OnClickListener loginListener = new OnClickListener() {
     public void onClick(View v) {
//getting inputs from user and performing data operations
                  
           if(username.getText().toString().equals("deepak") &&
                       password.getText().toString().equals("garg")){
//responding to the User inputs
                 Toast.makeText(getApplicationContext(), "Login Successfully !!!", Toast.LENGTH_LONG).show();      
           }else
                 Toast.makeText(getApplicationContext(), "Login Not Successful !!!", Toast.LENGTH_LONG).show();                           
     }
   };

}
