package io.captano.pesapal.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import io.captano.pesapal.R;

@SuppressWarnings("FieldCanBeLocal")
public class TxFragment extends Fragment {

    public TxFragment() { }

    public static TxFragment newInstance() {
        return new TxFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tx, container, false);

        // TODO: Add views

        return view;
    }
}
