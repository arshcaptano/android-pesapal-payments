package io.captano.pesapal.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.captano.pesapal.R;

public class Util {

    public static void vibrate(Context context, long milliSeconds/*Vibrate for x milliseconds*/) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        assert vibrator != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(VibrationEffect.createOneShot(milliSeconds, VibrationEffect.DEFAULT_AMPLITUDE));
        else
            // Deprecated in API 26
            vibrator.vibrate(milliSeconds);
    }

    public boolean IsInternetConnected(Context context) {
        @SuppressLint("WrongConstant") ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
        if (cm == null) {
            return false;
        } else {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
    }

    public static void notify(Context context, String message) {
        TextView tvTitle = new TextView(context);
        tvTitle.setText(context.getString(R.string.notification));
        tvTitle.setMinHeight(250);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setPadding(30, 30, 30, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        tvTitle.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_AppCompatAlertDialog);
        builder.setCustomTitle(tvTitle);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton(context.getString(R.string.ok), dialogClickListener);
        builder.show();
    }

    public static String getSha256Hash(String password) {
        try {
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException ignored) {
            }
            digest.reset();
            return bin2hex(digest.digest(password.getBytes()));
        } catch (Exception ignored) {
            return null;
        }
    }

    private static String bin2hex(byte[] data) {
        StringBuilder hex = new StringBuilder(data.length * 2);
        for (byte b : data)
            hex.append(String.format("%02x", b & 0xFF));
        return hex.toString();
    }

}
