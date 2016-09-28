package edu.sjsu.cs175.memorygame;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RulesFragment extends DialogFragment {

    private Button mCloseBtn;


    public RulesFragment() {
        // Required empty public constructor
    }

    public static RulesFragment newInstance() {
        RulesFragment fragment = new RulesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCloseBtn = (Button)getActivity().findViewById(R.id.close_rule_btn);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rules, container, false);

        // Inflate the layout for this fragment
        return v;
    }
    void closeDialog(View v){
        dismiss();
    }
}
