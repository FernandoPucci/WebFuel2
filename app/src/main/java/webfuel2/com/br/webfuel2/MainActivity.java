package webfuel2.com.br.webfuel2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import util.Util;

public class MainActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private Profile profile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //verificacao de acesso via facebook e log nas 'measures ad' do Facebook
            FacebookSdk.sdkInitialize(this.getApplicationContext());
            AppEventsLogger.activateApp(this);
        } catch (Exception ex) {

            Log.i(Util.LOG_INFO, "\n\n>>>>>> ERRO : " + ex.getMessage());

        }


        setContentView(R.layout.activity_main);


        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);

        List<String> permissionNeeds = Arrays.asList("email", "public_profile", "user_friends");

        loginButton.setReadPermissions(permissionNeeds);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(Util.LOG_INFO, "onSuccess");
                Log.i(Util.LOG_INFO, loginResult.getAccessToken().getUserId());

                Set<String> listaPermissoes = loginResult.getRecentlyGrantedPermissions();

                for (String s : listaPermissoes) {

                    Log.i(Util.LOG_INFO, s);

                }


                //recupera graphrequest
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);
                        Log.i(Util.LOG_INFO,object.toString());        ;
                        Log.i(Util.LOG_INFO,bFacebookData.toString());

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();


                profile = Profile.getCurrentProfile();

                if (profile == null) {
                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            // profile2 is the new profile
                            Log.i("facebook - profile", profile2.getFirstName());
                            Log.i(Util.LOG_INFO, profile2.getId());
                            Log.i(Util.LOG_INFO, profile2.getFirstName());
                            Log.i(Util.LOG_INFO, profile2.getLastName());
                            Log.i(Util.LOG_INFO, profile2.getName());
                            Log.i(Util.LOG_INFO, profile2.getName());
                            Log.i(Util.LOG_INFO, profile2.getProfilePictureUri(1024, 1024).getPath());
                            Profile.setCurrentProfile(profile2);
                            //   profileTracker.stopTracking();
                        }

                    };
                    profileTracker.startTracking();
                    // no need to call startTracking() on profileTracker
                    // because it is called by its constructor, internally.

                    profile = Profile.getCurrentProfile();


                } else {

                    profile = Profile.getCurrentProfile();

                    Log.i(Util.LOG_INFO, profile.getId());
                    Log.i(Util.LOG_INFO, profile.getFirstName());
                    Log.i(Util.LOG_INFO, profile.getLastName());
                    Log.i(Util.LOG_INFO, profile.getName());
                    Log.i(Util.LOG_INFO, profile.getProfilePictureUri(1024, 1024).getPath());

                }


                Log.i(Util.LOG_INFO, profile == null ? "NULO!!" : "OK!!!!!!!!!!!!");

//                final Profile profile = Profile.getCurrentProfile();
//
//                if (profile != null) {
//                    Log.i(Util.LOG_INFO, "Profile diferente de nulo");
//                }else{
//                    Log.i(Util.LOG_INFO, "Profile NULL!!");
//                }
//
//                Log.i(Util.LOG_INFO, profile.getId());
//                Log.i(Util.LOG_INFO, profile.getFirstName());
//                Log.i(Util.LOG_INFO, profile.getLastName());
//                Log.i(Util.LOG_INFO, profile.getName());
//                Log.i(Util.LOG_INFO, profile.getProfilePictureUri(1024,1024).getPath());


                //Toast.makeText(FacebookLogin.this,"Wait...",Toast.LENGTH_SHORT).show();
                /*GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String email_id = object.getString("email");
                                    String gender = object.getString("gender");
                                    String facebook_id = profile.getId();
                                    String f_name = profile.getFirstName();
                                    String m_name = profile.getMiddleName();
                                    String l_name = profile.getLastName();
                                    String full_name = profile.getName();
                                    String profile_image = profile.getProfilePictureUri(400, 400).toString();

                                    Log.i(Util.LOG_INFO, facebook_id);
                                    Log.i(Util.LOG_INFO, f_name);
                                    Log.i(Util.LOG_INFO, m_name);
                                    Log.i(Util.LOG_INFO, l_name);
                                    Log.i(Util.LOG_INFO, full_name);
                                    Log.i(Util.LOG_INFO, profile_image);
                                    Log.i(Util.LOG_INFO, gender);
                                    Log.i(Util.LOG_INFO, email_id + "");

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();

                                }

                            }

                        });*/


            }

            @Override
            public void onCancel() {
                Log.i(Util.LOG_INFO, "onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });


        //BARRA DE FERRAMENTAS SUPERIOR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //BOTAO LATERAL
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profile = Profile.getCurrentProfile();
                Log.i(Util.LOG_INFO, profile == null ? "NULO!!" : "OK!!!!!!!!!!!!");


//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private Bundle getFacebookData(JSONObject object) {

        Bundle bundle = new Bundle();

        try {

            String id = object.getString("id");


            URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
            Log.i("profile_pic", profile_pic + "");
            bundle.putString("profile_pic", profile_pic.toString());


            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        return bundle;
    }
}
