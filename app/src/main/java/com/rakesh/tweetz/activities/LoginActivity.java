package com.rakesh.tweetz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.rakesh.tweetz.R;
import com.rakesh.tweetz.TweetzClient;

public class LoginActivity extends OAuthLoginActionBarActivity<TweetzClient> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure(Exception e) {

    }


    public void loginToRest(View view) {
        getClient().connect();
    }
}
