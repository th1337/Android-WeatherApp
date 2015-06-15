package com.worldline.fpl.myweather.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by a607937 on 10/06/2015.
 */
public class BaseActivity extends AppCompatActivity {

    private boolean isDestroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDestroyed = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
    }

    protected boolean isValid() {

        return !isDestroyed;

    }

}
