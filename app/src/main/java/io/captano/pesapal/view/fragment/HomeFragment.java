package io.captano.pesapal.view.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import java.math.BigDecimal;

import io.captano.pesapal.R;
import io.captano.pesapal.object.Payment;
import io.captano.pesapal.util.Util;

@SuppressWarnings("FieldCanBeLocal")
public class HomeFragment extends Fragment {
    private CoordinatorLayout cdlSubmitHome;
    private AutoCompleteTextView actvFirstNameHome;
    private AutoCompleteTextView actvLastNameHome;
    private AutoCompleteTextView actvEmailHome;
    private AutoCompleteTextView actvPhoneHome;
    private Spinner spCurrencyHome;
    private AutoCompleteTextView actvAmountHome;
    private AutoCompleteTextView actvDescriptionHome;

    private String[] currency;
    private String selectedCurrency;

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

        currency = getContext().getResources().getStringArray(R.array.currency);

        cdlSubmitHome = view.findViewById(R.id.cdlSubmitHome);
        actvFirstNameHome = view.findViewById(R.id.actvFirstNameHome);
        actvLastNameHome = view.findViewById(R.id.actvLastNameHome);
        actvEmailHome = view.findViewById(R.id.actvEmailHome);
        actvPhoneHome = view.findViewById(R.id.actvPhoneHome);

        spCurrencyHome = view.findViewById(R.id.spCurrencyHome);
        if (currency != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, currency);
            spCurrencyHome.setAdapter(adapter);
        }

        actvAmountHome = view.findViewById(R.id.actvAmountHome);
        actvDescriptionHome = view.findViewById(R.id.actvDescriptionHome);

        // TODO: Add views

        setListeners();

        return view;
    }

    private void setListeners() {
        spCurrencyHome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {

                try {
                    selectedCurrency = spCurrencyHome.getSelectedItem().toString();
                } catch (Exception e) {
                    Util.notify(getContext(), getString(R.string.currency_required));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedCurrency = currency[0];
            }

        });

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
        payment.setPhone(phone);

        if (selectedCurrency != null) {
            payment.setCurrency(selectedCurrency);
        }

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
        int PAYMENT_ACTIVITY_REQUEST_CODE = 1;
        payment.setReference(Util.generateReference(payment.getPhone()));//transaction unique reference
//        payment.setAccount("");

        ComponentName cn = new ComponentName(getContext(), "com.pesapal.pesapalandroid.PesapalPayActivity");

        Intent intent = new Intent().setComponent(cn);
        intent.putExtra("payment", String.valueOf(payment));
        startActivityForResult(intent, PAYMENT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        resetForm();
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
