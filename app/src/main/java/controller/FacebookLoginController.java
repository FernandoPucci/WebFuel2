package controller;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import util.FacebookConstantes;
import util.JsonUserBundles;

/**
 * Created by fernando-pucci on 01/06/16.
 */
public class FacebookLoginController {

    private LoginResult loginResult;
    private Bundle bFacebookData;


    private Profile profile = null;
    private ProfileTracker profileTracker = null;
    private AccessToken acessToken = null;
    private AccessTokenTracker accessTokenTracker = null;

    private String jsonUserData = "";

    public FacebookLoginController(LoginResult loginResult) {


        profile = Profile.getCurrentProfile();
        acessToken = loginResult.getAccessToken();

           }


    public Profile updateFacebookProfile(){


                if (profile == null) {
                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            // profile2 is the new profile
                            Log.i("facebook - profile 2:  ", profile2.getFirstName());

                            Profile.setCurrentProfile(profile2);

                        }

                    };

                    profileTracker.startTracking();

                    accessTokenTracker = new AccessTokenTracker() {
                        @Override
                        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                          if(currentAccessToken != oldAccessToken){

                            acessToken = currentAccessToken;

                          }
                        }
                    };

                    profile = Profile.getCurrentProfile();


                } else {

                    profile = Profile.getCurrentProfile();

                }

        return profile;

    }

    public String getLoggedUserData() {


        //recupera GraphRequest
        GraphRequest request = GraphRequest.newMeRequest(acessToken, new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                // Monta JSON com dados do usuario
                bFacebookData = getFacebookDataController(object);
                jsonUserData = bFacebookData.toString();

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString(FacebookConstantes.KEY_FIELDS_LOGIN, FacebookConstantes.FIELDS_LOGIN); // Parámetros que pedimos a facebook
        request.setParameters(parameters);
        request.executeAsync();

        return this.jsonUserData;

    }

    public Bundle getBundleFacebookData(){

        return this.bFacebookData;
    }

    private Bundle getFacebookDataController(JSONObject object) {

        Bundle bundle = new Bundle();

        try {

            String id = object.getString(JsonUserBundles.ID);


            URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");

            bundle.putString(JsonUserBundles.PROFILE_PIC, profile_pic.toString());


            bundle.putString(JsonUserBundles.ID_FACEBOOK, id);

            if (object.has(JsonUserBundles.FIRST_NAME))
                bundle.putString(JsonUserBundles.FIRST_NAME, object.getString(JsonUserBundles.FIRST_NAME));
            if (object.has(JsonUserBundles.LAST_NAME))
                bundle.putString(JsonUserBundles.LAST_NAME, object.getString(JsonUserBundles.LAST_NAME));
            if (object.has(JsonUserBundles.EMAIL))
                bundle.putString(JsonUserBundles.EMAIL, object.getString(JsonUserBundles.EMAIL));
            if (object.has(JsonUserBundles.GENDER))
                bundle.putString(JsonUserBundles.GENDER, object.getString(JsonUserBundles.GENDER));
            if (object.has(JsonUserBundles.BIRTHDAY))
                bundle.putString(JsonUserBundles.BIRTHDAY, object.getString(JsonUserBundles.BIRTHDAY));
            if (object.has(JsonUserBundles.LOCATION))
                bundle.putString(JsonUserBundles.LOCATION, object.getJSONObject(JsonUserBundles.LOCATION).getString(JsonUserBundles.NAME));


        } catch (JSONException e) {

            e.printStackTrace();

        } catch (MalformedURLException e) {

            e.printStackTrace();
            return null;
        }

        return bundle;
    }


}
