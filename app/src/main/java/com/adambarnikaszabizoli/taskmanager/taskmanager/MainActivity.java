package com.adambarnikaszabizoli.taskmanager.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.LoginRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.VolleyRequestManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.AccountPicker;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Token already exists: " + TaskManagerApplication.getToken());
        startActivity(new Intent(getBaseContext(), ExampleActivity.class));

    }

    static final int REQUEST_CODE_PICK_ACCOUNT = 1000;

    private void pickUserAccount() {
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }
}
