package edu.sjsu.cs175.memorygame;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    //RulesFragment mRuleFragement;
    DialogFragment newFragment;
    //Button  mRuleBtn;
    @BindView(R.id.start_btn) Button mStartBtn;
    @BindView(R.id.rule_btn) Button mRuleBtn;
    TextView mGreetingView;
    final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        System.out.println("onCreating");
        //mStartBtn = (Button)findViewById(R.id.start_btn);
        mRuleBtn = (Button)findViewById(R.id.rule_btn);
        mGreetingView = (TextView)findViewById(R.id.greeting_txt_view);


        //newFragment = (DialogFragment)getFragmentManager().findFragmentById(R.id.rule_fragament);

        /*mRuleFragement.getmCloseBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }
    @OnClick(R.id.start_btn)
    public void startGame(View view) {
        Intent intent = new Intent(MainActivity.this,GameActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.rule_btn)
    public void showRules(View view) {
        //Intent intent = new Intent(MainActivity.this,RulesActivity.class);
        //startActivity(intent);
        showDialog();
    }

    void showDialog() {
        // Create the fragment and show it as a dialog.
        newFragment = RulesFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }
    void closeDialog(View v){
        Log.d(TAG,"closing dialog");
        newFragment.dismiss();
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        System.out.println("onSaveInstanceState");


    }
}
