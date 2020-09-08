package io.captano.pesapal.view.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.math.BigDecimal;

import io.captano.pesapal.R;
import io.captano.pesapal.application.PreferenceManager;
import io.captano.pesapal.object.Payment;
import io.captano.pesapal.util.Config;
import io.captano.pesapal.util.Util;
import io.captano.pesapal.view.activity.ResultActivity;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;
import oauth.signpost.signature.HmacSha1MessageSigner;

@SuppressWarnings("FieldCanBeLocal")
public class HomeFragment extends Fragment {

    private final int PAYMENT_ACTIVITY_REQUEST_CODE = 1;

    private CoordinatorLayout cdlSubmitHome;
    private AutoCompleteTextView actvFirstNameHome;
    private AutoCompleteTextView actvLastNameHome;
    private AutoCompleteTextView actvEmailHome;
    private AutoCompleteTextView actvPhoneHome;
    private TextView tvCurrencyHome;
    private AutoCompleteTextView actvAmountHome;
    private AutoCompleteTextView actvDescriptionHome;

    private PreferenceManager preferenceManager;

    private String defaultCurrency;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cdlSubmitHome = view.findViewById(R.id.cdlSubmitHome);
        actvFirstNameHome = view.findViewById(R.id.actvFirstNameHome);
        actvLastNameHome = view.findViewById(R.id.actvLastNameHome);
        actvEmailHome = view.findViewById(R.id.actvEmailHome);
        actvPhoneHome = view.findViewById(R.id.actvPhoneHome);
        tvCurrencyHome = view.findViewById(R.id.tvCurrencyHome);
        actvAmountHome = view.findViewById(R.id.actvAmountHome);
        actvDescriptionHome = view.findViewById(R.id.actvDescriptionHome);

        preferenceManager = new PreferenceManager(getContext());
        defaultCurrency = getPreferenceManager().getCurrency();
        if (defaultCurrency != null)
            tvCurrencyHome.setText(defaultCurrency);
        else
            tvCurrencyHome.setText("KES");

        setListeners();

        return view;
    }

    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null)
            preferenceManager = new PreferenceManager(getContext());

        return preferenceManager;
    }

    private void setListeners() {
        cdlSubmitHome.setOnClickListener(View -> {
            validate();
        });
    }

    private void validate() {
        Payment payment = new Payment();

        String firstName = actvFirstNameHome.getText().toString().trim();
        if (firstName.isEmpty()) {
            actvFirstNameHome.setError(getContext().getString(R.string.required));
            return;
        }
        payment.setFirstName(firstName);

        String lastName = actvLastNameHome.getText().toString().trim();
        if (lastName.isEmpty()) {
            actvLastNameHome.setError(getContext().getString(R.string.required));
            return;
        }
        payment.setLastName(lastName);

        String email = actvEmailHome.getText().toString().trim();
        if (email.isEmpty()) {
            actvEmailHome.setError(getContext().getString(R.string.required));
            return;
        }
        payment.setEmail(email);

        String phone = actvPhoneHome.getText().toString().trim();
        if (phone.isEmpty()) {
            actvPhoneHome.setError(getContext().getString(R.string.required));
            return;
        }
        payment.setPhone(phone);

        payment.setCurrency(defaultCurrency);

        String amount = actvAmountHome.getText().toString().trim();
        if (amount.isEmpty()) {
            actvAmountHome.setError(getContext().getString(R.string.required));
            return;
        }
        payment.setAmount(new BigDecimal(amount));

        String description = actvDescriptionHome.getText().toString().trim();
        if (description.isEmpty()) {
            actvDescriptionHome.setError(getContext().getString(R.string.required));
            return;
        }
        payment.setDescription(description);

        processRequest(payment);
    }

    private void processRequest(Payment payment) {
        payment.setConsumerKey(preferenceManager.getConsumerKey());
        payment.setConsumerSecret(preferenceManager.getConsumerSecret());
        payment.setReference(Util.generateReference(payment.getPhone()));

        Intent intent = new Intent(getContext(), ResultActivity.class);
        intent.putExtra("payment", new Gson().toJson(payment));
        startActivity(intent);

        ((Activity) getContext()).overridePendingTransition(R.xml.fade_in, R.xml.fade_out);

        resetForm();
    }

    private void resetForm() {
        actvFirstNameHome.getText().clear();
        actvLastNameHome.getText().clear();
        actvEmailHome.getText().clear();
        actvPhoneHome.getText().clear();
        actvAmountHome.getText().clear();
        actvDescriptionHome.getText().clear();
    }


}
