package com.adambarnikaszabizoli.taskmanager.taskmanager;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.GoogleAuthRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.LoginRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.VolleyRequestManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
//import com.google.android.gms.plus;

public class ExampleActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // version : 6171000
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;
    private static final int RECOVERABLE_REQUEST_CODE = 101;
//    private static final String SERVER_CLIENT_ID ="668950079827-uchdfnqlmj7rp759jo6shfcfqh3kgvl8.apps.googleusercontent.com";
//    private static final String SERVER_CLIENT_ID ="13601182052-hdcr783ne4a9430vgutb7bpionpe6npn.apps.googleusercontent.
    private static final String SERVER_CLIENT_ID ="13601182052-hdcr783ne4a9430vgutb7bpionpe6npn.apps.googleusercontent.com";
    private String mToken;
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    private boolean mSignInClicked;
    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
        if (requestCode == RECOVERABLE_REQUEST_CODE) {
            Bundle extra = data.getExtras();
            String oneTimeToken = extra.getString("authtoken");
            Log.i("HEllo", "one time token " + oneTimeToken);
//            onGoogleSignInSuccessful();
            mToken = oneTimeToken;
            System.out.println("yoyoyoyoyo vege");
            loginToHerokuViaGoogle(oneTimeToken);
//            sendSocialRequest(GOOGLE_PROVIDER_NAME, oneTimeToken);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
                getGoogleProfileInfo();
                getAccessToken();
            }
        });

        //from loginactivity
        Button login = (Button) findViewById(R.id.bt_login);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                executeLoginRequest();
            }
        });

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("pista", "Conected ocsem");
//        getGoogleProfileInfo();
//        getAccessToken();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("PISTa", "On connection failed");
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
        if (!mIntentInProgress) {
            // Store the ConnectionResult so that we can use it later when the user clicks
            // 'sign-in'.
            mConnectionResult = result;
            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                signInGoogle();
            }
        }
    }

    public void signInGoogle() {
        if (mConnectionResult != null && mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    private void getGoogleProfileInfo() {
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            Log.i("pista", "Current person = " + currentPerson);
            Person.Image image = currentPerson.getImage();
            String imageUrl = image.getUrl();
            //set default image dimension
            if (imageUrl.contains("sz=50"))
                imageUrl = imageUrl.replace("sz=50", "sz=126");
        }
    }

    private void getAccessToken() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPostExecute(String accessToken) {
                Log.i("pista", "Access token = " + accessToken);
                loginToHerokuViaGoogle(accessToken);
            }

            @Override
            protected String doInBackground(Void... params) {
                String scopesString = Scopes.PLUS_LOGIN + " " + "https://www.googleapis.com/auth/userinfo.email" + " " +
                        "https://www.googleapis.com/auth/calendar";
                String scopes = String.format("oauth2:server:client_id:" + SERVER_CLIENT_ID + ":api_scope:" + scopesString);
              
                String accessToken = null;
                try {
                    accessToken = GoogleAuthUtil.getToken(ExampleActivity.this, Plus.AccountApi.getAccountName(mGoogleApiClient), scopes, new Bundle());
                } catch (UserRecoverableAuthException e) {
                    startActivityForResult(e.getIntent(), RECOVERABLE_REQUEST_CODE);
                    e.printStackTrace();
                    return "";
                } catch (GoogleAuthException e) {
                    Log.i("pista", "Over here googleUathException" +e.getMessage());
                    return "";
                } catch (IOException e) {
                    Log.i("pista", "Over here IoException");
                    return "";
                }
                return accessToken;
            }
        }.execute();
    }
    public void signOutGoogle() {
        Log.i("pista", "Sign out");
        Log.i("pista", "is connected " + mGoogleApiClient.isConnected());
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {

                            if (mToken != null) {
                                Log.i("pista", "Invalidate access token");
                                AccountManager am = AccountManager.get(ExampleActivity.this);
                                am.invalidateAuthToken("com.google", mToken);
                            }
                        }
                    });
        }
    }


    public void executeLoginRequest() {
        Map<String,String> credentials = new HashMap<String, String>();
        credentials.put("email", ((EditText) findViewById(R.id.ed_email)).getText().toString());
        credentials.put("password", ((EditText)findViewById(R.id.ed_pass)).getText().toString());
        LoginRequest loginRequest = new LoginRequest(new Response.Listener() {
            public void onResponse(Object response) {
                System.out.println("Success Request");
                System.out.println("TOKEN IS: " + TaskManagerApplication.getToken());
                Intent i = new Intent(ExampleActivity.this, TaskListActivity.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                System.out.println("BAD REQUEST");
                Toast.makeText(getApplicationContext(), "Invalid email or password.", Toast.LENGTH_LONG).show();
            }
        }, credentials);
        VolleyRequestManager.getSharedInstance(this).getRequestQueue().add(loginRequest);
    }


    public void loginToHerokuViaGoogle(String token) {

        System.out.println("TOKEN IS GOOGLE : " + token);

        if (token != null) {
            Map<String,String> credentials = new HashMap<String, String>();
            credentials.put("code", token);
            GoogleAuthRequest googleAuthRequest = new GoogleAuthRequest(new Response.Listener() {
                public void onResponse(Object response) {
                    Intent i = new Intent(ExampleActivity.this, TaskListActivity.class);
                    startActivity(i);
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    System.out.println("BAD REQUEST");
                    Toast.makeText(getApplicationContext(), "Something went wrong with google code auth.", Toast.LENGTH_LONG).show();
                }
            }, credentials);
            VolleyRequestManager.getSharedInstance(this).getRequestQueue().add(googleAuthRequest);
        }
    }

}
