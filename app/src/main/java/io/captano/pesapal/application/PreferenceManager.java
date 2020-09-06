package io.captano.pesapal.application;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "pesaPalPREF";

    private static final String KEY_CONSUMER_KEY = "A2583893X";
    private static final String KEY_CONSUMER_SECRET = "G7495739H";
    private static final String KEY_CURRENCY = "P7495732E";

    public PreferenceManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    public void setConsumerKey(String consumerKey) {
        editor.putString(KEY_CONSUMER_KEY, consumerKey);
        editor.commit();
    }

    public String getConsumerKey() {
        return pref.getString(KEY_CONSUMER_KEY, null);
    }

    public void setConsumerSecret(String consumerSecret) {
        editor.putString(KEY_CONSUMER_SECRET, consumerSecret);
        editor.commit();
    }

    public String getConsumerSecret() {
        return pref.getString(KEY_CONSUMER_SECRET, null);
    }

    public void setCurrency(String currency) {
        editor.putString(KEY_CURRENCY, currency);
        editor.commit();
    }

    public String getCurrency() {
        return pref.getString(KEY_CURRENCY, null);
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}
