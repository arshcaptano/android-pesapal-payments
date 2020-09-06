package io.captano.pesapal.util;

public class Config {
    public static final String BASE_URL = "";

    public static final int CLICK_INTERVAL = 2000;

    // Volley request configs
    public static final int TIMEOUT_MS = 30000; // Milliseconds
    public static final int RETRIES = 1; // Retries for failed requests
    public static final float BACKOFF_MULTIPLIER = 1f; // Multiplier added for 1st failed request and so on
}
