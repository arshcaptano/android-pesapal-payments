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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.zip.CRC32;

import io.captano.pesapal.R;

public class Util {


    public static String generateReference(String phoneNumber) {
        String time = getDateTimeNow("yyyyMMddHHmmssSSS");

        return String.valueOf(randomize()).toUpperCase() + shrink(phoneNumber) + time;
    }

    public static String getDateTimeNow(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }

    private static long shrink(String input) {
        CRC32 crc = new CRC32();
        crc.update(input.getBytes());
        return crc.getValue();
    }

    private static char randomize() {
        Random rnd = new Random();
        return (char) (rnd.nextInt(26) + 'A');
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
