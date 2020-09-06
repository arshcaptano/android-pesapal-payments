package io.captano.pesapal.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.captano.pesapal.R;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {
    private final Context context = this;
    private final Activity activity = this;

    private TextView tvNetMain;
    private BottomNavigationView bnvMain;


    private BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork;
            if (cm != null) {
                activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected)
                    tvNetMain.setVisibility(View.GONE);
                else
                    tvNetMain.setVisibility(View.VISIBLE);
            } else {
                tvNetMain.setVisibility(View.VISIBLE);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNetMain = findViewById(R.id.tvNetMain);

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            registerReceiver(connectionReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        else
            registerReceiver(connectionReceiver, intentFilter);

        bnvMain = findViewById(R.id.bnvMain);

        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            if (connectionReceiver != null)
                unregisterReceiver(connectionReceiver);
        } catch (IllegalArgumentException ignored) {
        }
    }

    private void setListeners() {
        bnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nv_home:

                        return true;
                    case R.id.nv_settings:

                        return true;
                }
                return false;
            }
        });
    }
}