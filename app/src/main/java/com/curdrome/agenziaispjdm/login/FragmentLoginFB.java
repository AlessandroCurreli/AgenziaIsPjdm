package com.curdrome.agenziaispjdm.login;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curdrome.agenziaispjdm.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLoginFB extends android.support.v4.app.Fragment {
    protected String actvityName;
    private TextView mTextDetails;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;

    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("CurDroMe", "onSuccess");
            //Profile profile = Profile.getCurrentProfile();
            GraphRequest request = GraphRequest.newMeRequest
                    (loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                    {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response)
                        {
                            // Application code
                            Log.v("LoginActivity", response.toString());
                            try {
                                Log.d("CurDroMe_FB", "utente nome " + object.getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            /*
                            //System.out.println("Check: " + response.toString());
                            try
                            {
                                if (getActivity().getClass().toString().contains("Login")){
                                    //se l'Activity chiamante è la Login, passa solamente i dati per il Login
                                    LoginActivity activity = (LoginActivity) getActivity();
                                    activity.loginConnection(object.getString("email"), object.getString("id"));
                                }else{
                                    //se l'Activity chiamante è Register, passa solamente i dati per la Register
                                    RegisterActivity activity =(RegisterActivity) getActivity();
                                    activity.registerConnection(object.getString("email"),object.getString("firstname"),object.getString("lastname"),0.0,object.getString("id"));
                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }*/

                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name");
            request.setParameters(parameters);
            request.executeAsync();

        }


        @Override
        public void onCancel() {
            Log.d("CurDroMe", "Login FB onCancel");
        }

        @Override
        public void onError(FacebookException e) {
            Log.d("CurDroMe", "Login FB onError " + e);
        }
    };


    public FragmentLoginFB() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //String activityName = getActivity().getClass().toString();

        mCallbackManager = CallbackManager.Factory.create();
        setupTokenTracker();
        setupProfileTracker();

        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_fb, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupLoginButton(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
    }

    @Override
    public void onStop() {
        super.onStop();
        mTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }



    private void setupTokenTracker() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("CurDroMe AI", "" + currentAccessToken);
            }
        };
    }

    private void setupProfileTracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d("CurDroMe AI", "" + currentProfile);
            }
        };
    }

    private void setupLoginButton(View view) {
        LoginButton mButtonLogin = (LoginButton) view.findViewById(R.id.button_login_fb);
        mButtonLogin.setFragment(this);
        mButtonLogin.setReadPermissions("public_profile");
        mButtonLogin.registerCallback(mCallbackManager, mFacebookCallback);
    }

}

