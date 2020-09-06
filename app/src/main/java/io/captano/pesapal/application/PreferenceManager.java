package io.captano.pesapal.application;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "pesaPalPREF";

    public PreferenceManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}
