package com.adambarnikaszabizoli.taskmanager.taskmanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.LoginRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.VolleyRequestManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

public class LoginTask extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_task);
        Button login = (Button) findViewById(R.id.bt_login);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                executeLoginRequest();
			}
		});
	}

	public void executeLoginRequest() {
        Map<String,String> credentials = new HashMap<String, String>();
        credentials.put("email", ((EditText) findViewById(R.id.ed_email)).getText().toString());
        credentials.put("password", ((EditText)findViewById(R.id.ed_pass)).getText().toString());
//        credentials.put("email", "nzoli@halcyonmobile.com");
//        credentials.put("password", "akarmi123");
        LoginRequest loginRequest = new LoginRequest(new Response.Listener() {
            public void onResponse(Object response) {
                System.out.println("Success Request");
                System.out.println("TOKEN IS: " + TaskManagerApplication.getToken());
                Intent i = new Intent(LoginTask.this, TaskListActivity.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                System.out.println("BAD REQUEST");
                Toast.makeText(getApplicationContext(), "Invalid email or password.",Toast.LENGTH_LONG).show();
            }
        }, credentials);
        VolleyRequestManager.getSharedInstance(this).getRequestQueue().add(loginRequest);
    }
}
