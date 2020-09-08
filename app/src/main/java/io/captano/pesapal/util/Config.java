package io.captano.pesapal.util;

public class Config {
    public static final String BASE_URL = "https://demo.pesapal.com/API/";

    public static final String POST_PESAPAL_DIRECT_ORDER = BASE_URL + "PostPesapalDirectOrderV4";
    public static final String POST_PESAPAL_DIRECT_ORDER_MOBILE = BASE_URL + "PostPesapalDirectOrderMobile";

    public static final String QUERY_PAYMENT_STATUS = BASE_URL + "QueryPaymentStatus";
    public static final String QUERY_PAYMENT_DETAILS = BASE_URL + "QueryPaymentDetails";

    public static final String CONSUMER_KEY = "Oe8KxNKAFvN2iwj6EeMTAJtsS3IVUlt3";
    public static final String CONSUMER_SECRET = "yu2Mpfljg5UYGlD6OBf3qRIr+WQ=";
    private static final String CALLBACK = "https://demo.pesapal.com/api/querypaymentstatus";

}
