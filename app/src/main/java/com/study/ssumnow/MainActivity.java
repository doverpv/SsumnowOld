package com.study.ssumnow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

//[reference]You want to start a new activity and destroy the previous one? If this is what you need, you can use: startActivity(new Intent(this, myActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
