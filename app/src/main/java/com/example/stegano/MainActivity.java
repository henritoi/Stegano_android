package com.example.stegano;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final long SPLASH_SCREEN_TIME = 3000;

    TextView title;
    Animation top_animation;

    /**
     * Application open screen (Splash screen) animation initialization
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        title = findViewById(R.id.appNameTextView);
        top_animation = AnimationUtils.loadAnimation(this, R.anim.top_animation);

        title.setAnimation(top_animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(title, "tn_app_title");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);

                startActivity(intent, options.toBundle());
                finish();
            }
        }, SPLASH_SCREEN_TIME);
    }
}
