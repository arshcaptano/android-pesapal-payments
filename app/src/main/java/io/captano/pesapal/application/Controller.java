package io.captano.pesapal.application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Controller extends Application {

    public static final String TAG = Controller.class.getSimpleName();

    private static Controller instance;
    private PreferenceManager preferenceManager;
    private RequestQueue requestQueue; // Volley request queue

    public static synchronized Controller getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null)
            preferenceManager = new PreferenceManager(this);

        return preferenceManager;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

}

