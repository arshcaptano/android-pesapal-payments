package io.captano.pesapal.view.activity;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.captano.pesapal.R;
import io.captano.pesapal.view.adapter.MainNavAdapter;
import io.captano.pesapal.view.fragment.HomeFragment;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {
    private TextView tvNetMain;
    private MaterialToolbar tbMain;
    private ViewPager vpMain;
    private BottomNavigationView bnvMain;

    private MainNavAdapter mainNavAdapter;

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

        tbMain = findViewById(R.id.tbMain);
        setSupportActionBar(tbMain);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tbMain.setVisibility(View.GONE);

        tbMain.setNavigationOnClickListener(v -> {
            finish();

            overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        vpMain = findViewById(R.id.vpMain);
        bnvMain = findViewById(R.id.bnvMain);

        setViewPager();
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

    private void setViewPager() {
        mainNavAdapter = new MainNavAdapter(getSupportFragmentManager());

        mainNavAdapter.addFragment(new HomeFragment());

        vpMain.setAdapter(mainNavAdapter);
    }


    private void setListeners() {
        bnvMain.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nv_home:
                    tbMain.setVisibility(View.GONE);
                    return true;
                case R.id.nv_settings:
                    tbMain.setVisibility(View.VISIBLE);

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setDisplayShowTitleEnabled(false);
                    }
                    return true;
            }

            return false;
        });
    }
}