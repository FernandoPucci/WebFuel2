package webfuel2.com.br.webfuel2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
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

import controller.FacebookLoginController;
import util.Util;

public class MainActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private FacebookLoginController facebookLoginController;
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

        // List<String> permissionNeeds = Arrays.asList("email", "public_profile", "user_friends");


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.i(Util.LOG_INFO, "onSuccess");


                        if (facebookLoginController == null) {

                            facebookLoginController = new FacebookLoginController(loginResult);


                        }

                        Log.w(Util.LOG_WARN, facebookLoginController.getLoggedUserData());



//
//

                        //    Log.i(Util.LOG_INFO, profile == null ? "NULO!!" : "OK!!!!!!!!!!!!");


                    }

                    @Override
                    public void onCancel() {
                        Log.i(Util.LOG_INFO, "onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.v(Util.LOG_ERR, exception.getCause().toString());
                    }
                }

        );


        //BARRA DE FERRAMENTAS SUPERIOR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        //BOTAO LATERAL
        FloatingActionButton btnChecaPreco = (FloatingActionButton) findViewById(R.id.abreChecaPreco);

        btnChecaPreco.setOnLongClickListener(new View.OnLongClickListener()

                                             {
                                                 @Override
                                                 public boolean onLongClick(View view) {
                                                     Snackbar.make(view, getString(R.string.txtChecaPreco), Snackbar.LENGTH_LONG)
                                                             .setAction("Action", null).show();

                                                     return false;
                                                 }
                                             }

        );

        btnChecaPreco.setOnHoverListener(new View.OnHoverListener()

                                         {
                                             @Override
                                             public boolean onHover(View view, MotionEvent event) {

                                                 Snackbar.make(view, getString(R.string.txtChecaPrecoHover), Snackbar.LENGTH_SHORT)
                                                         .setAction("Action", null).show();

                                                 return false;
                                             }
                                         }

        );

        btnChecaPreco.setOnClickListener(new View.OnClickListener()

                                         {
                                             @Override
                                             public void onClick(View view) {

                                                 Log.w(Util.LOG_WARN, facebookLoginController.getLoggedUserData());

                                              //   profile = facebookLoginController.updateFacebookProfile();

                                                 Log.i(Util.LOG_INFO, profile == null ? "NULO!!" : "OK!!!!!!!!!!!!");


                                             }
                                         }

        );
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
