package io.captano.pesapal.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import io.captano.pesapal.R;
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
        currency = getContext().getResources().getStringArray(R.array.currency);
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
                    Util.vibrate(getContext(), 200);
                    Util.notify(getContext(), getString(R.string.currency_required));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Util.vibrate(getContext(), 200);
                Util.notify(getContext(), getString(R.string.currency_required));
            }

        });

        cdlSubmitHome.setOnClickListener(View -> {
            validate();
        });
    }

    private void validate() {
        // TODO
    }
}
