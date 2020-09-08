package io.captano.pesapal.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.kevinomyonga.pesapaldroid.IRequest;
import com.kevinomyonga.pesapaldroid.Pesapal;
import com.kevinomyonga.pesapaldroid.exception.PesapalException;
import com.kevinomyonga.pesapaldroid.ipn.DefaultIpnRequest;
import com.kevinomyonga.pesapaldroid.post.PostRequest;

import io.captano.pesapal.R;
import io.captano.pesapal.object.Payment;

public class ResultActivity extends AppCompatActivity {

    private WebView wvResult;
    private PostRequest request;
    private Payment payment;

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            String callback = request.getCallback();
            if (url.startsWith(callback)) {
                try {

                    IRequest ipn;
                    ipn = new DefaultIpnRequest(url);
                    view.loadUrl(ipn.getURL());
                } catch (PesapalException ignored) {

                }
                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        wvResult = findViewById(R.id.wvResult);
        wvResult.getSettings().setJavaScriptEnabled(true);
        wvResult.getSettings().setBuiltInZoomControls(true);
        wvResult.setWebViewClient(webViewClient);

        getData();
        loadPortal();
    }

    private void getData() {
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String pyt = bundle.getString("payment");
                payment = new Gson().fromJson(pyt, Payment.class);

            }
        } catch (Exception ignored) {
        }
    }

    public void loadPortal() {
        request = createPostRequest();

        try {
            String url = request.getURL();
            wvResult.loadUrl(url);
        } catch (PesapalException ignored) {
        }
    }

    private PostRequest createPostRequest() {
        Pesapal.initialize(payment.consumerKey, payment.consumerSecret);
        Pesapal.setDEMO(true);

        PostRequest.Builder builder = new PostRequest.Builder();

        builder
                .isMobile(true)
                .amount(String.valueOf(payment.getAmount()))
                .description(payment.getDescription())
                .phone(payment.getPhone())
                .mail(payment.getEmail())
                .name(payment.getFirstName(), payment.getLastName());

        return builder.build();
    }

}