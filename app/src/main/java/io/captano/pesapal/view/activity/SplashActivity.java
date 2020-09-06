package io.captano.pesapal.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import io.captano.pesapal.R;


@SuppressWarnings("FieldCanBeLocal")
public class SplashActivity extends AppCompatActivity {
    private final Context context = this;

    private ImageView ivSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Set status bar icons to a dark contrast colour
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        ivSplash = findViewById(R.id.ivSplash);

        Animation ivAnimation = AnimationUtils.loadAnimation(context, R.anim.splash_transition);
        ivSplash.startAnimation(ivAnimation);


        actionStart();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);

        overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
    }

    private void actionStart() {
        final Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            startActivity(i);

            overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        }, 3000);
    }

}