package io.captano.pesapal.util;

public class Config {
    public static final String BASE_URL = "https://demo.pesapal.com/API/";

    public static final String POST_PESAPAL_DIRECT_ORDER = BASE_URL + "PostPesapalDirectOrderMobile"; // PostPesapalDirectOrderV4
    public static final String QUERY_PAYMENT_STATUS = BASE_URL + "QueryPaymentStatus";
    public static final String QUERY_PAYMENT_DETAILS = BASE_URL + "QueryPaymentDetails";

    private static final String SOAP_ACTION = "";
    private static final String NAMESPACE = "http://www.pesapal.com";
    private static final String METHOD_POST_PESAPAL_DIRECT_ORDER = "PesapalDirectOrderInfo";

    // Query parameters
    public static final String PESAPAL_NOTIFICATION_TYPE = "CHANGE";
    public static final String OAUTH_CALLBACK = "";
    public static final String OAUTH_NONCE = "";
    public static final String OAUTH_SIGNATURE = "";
    public static final String OAUTH_SIGNATURE_METHOD = "HMAC-SHA1";
    public static final String OAUTH_TIMESTAMP = ""; // long unixTime = System.currentTimeMillis() / 1000L;
    public static final String OAUTH_VERSION = "1.0";

    public static final int CLICK_INTERVAL = 2000;

    // Volley request configs
    public static final int TIMEOUT_MS = 30000; // Milliseconds
    public static final int RETRIES = 1; // Retries for failed requests
    public static final float BACKOFF_MULTIPLIER = 1f; // Multiplier added for 1st failed request and so on
}
