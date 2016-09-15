package edu.sjsu.cs175.memorygame;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    private GridLayout mGamePanel;
//    private IconButton[][] mIconButtonsArray;
    private Stack<Integer> mIconShuffledStack;
//    private int mScore = 0;
    private Queue<IconButton> clickedIconQueue;
    private final static int mPanelWidth = 5, mPanelLength = 4;
    private TextView mScoreTextView;
    private List<Integer> mIconResourceList;
    private float scale;
    private static int BLOCK_WIDTH = 90;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initViews();
        initData();
        if(AppData.isStarted)
            openDialog();
        else
            startNewGame();

    }
    private void initViews(){
        mGamePanel = (GridLayout)findViewById(R.id.gameGridPanel);
        mScoreTextView = (TextView)findViewById(R.id.scoreTextView);
    }
    private void initData(){
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        loadIconResources();
    }



    private void startNewGame(){
        //shuffle icons
        mIconShuffledStack = new Stack<Integer>();
        addResourcesToQueue();
        addResourcesToQueue();
        //build game panel
        buildGamePanel();
        //init display and click queue
        AppData.score = 0;
        mScoreTextView.setText(String.format(getString(R.string.point),AppData.score));
        clickedIconQueue = new LinkedList<IconButton>();
        AppData.isStarted = true;
    }

    private void resumeGame(){
        mScoreTextView.setText(String.format(getString(R.string.point),AppData.score));
        for(int i = 0; i < mPanelWidth; i++){
            for(int j = 0; j<mPanelLength; j++){
                mGamePanel.addView(AppData.mIconButtonsArray[i][j]);
            }
        }
        clickedIconQueue = new LinkedList<IconButton>();
    }
    private void endGame(){
        AppData.isStarted = false;
    }
    private void addResourcesToQueue(){
        Collections.shuffle(mIconResourceList);
        for(Integer code : mIconResourceList){
            mIconShuffledStack.push(code);
        }
    }
    private void loadIconResources(){
        mIconResourceList = new ArrayList<>();
        mIconResourceList.add(R.drawable.icon_base_jumping);
        mIconResourceList.add(R.drawable.icon_climbing);
        mIconResourceList.add(R.drawable.icon_floating_guru);
        mIconResourceList.add(R.drawable.icon_football);
        mIconResourceList.add(R.drawable.icon_frisbee);
        mIconResourceList.add(R.drawable.icon_golf);
        mIconResourceList.add(R.drawable.icon_handball);
        mIconResourceList.add(R.drawable.icon_meditation_guru);
        mIconResourceList.add(R.drawable.icon_paddling);
        mIconResourceList.add(R.drawable.icon_regular_biking);
    }
    private void buildGamePanel(){
        AppData.mIconButtonsArray = new IconButton[mPanelWidth][mPanelLength];
        for(int i = 0; i < mPanelWidth; i++){
            for(int j = 0; j<mPanelLength; j++){
                IconButton b = new IconButton(getApplicationContext());
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.height = (int)(BLOCK_WIDTH * scale + 0.5f);
                params.width = (int)(BLOCK_WIDTH * scale + 0.5f);
                params.setMargins(5,5,5,5);
                b.setLayoutParams(params);
                b.setOnClickListener(this);
                b.setIconCode(mIconShuffledStack.pop());
                b.setBackground(getDrawable(R.drawable.icon_cover));
                AppData.mIconButtonsArray[i][j] = b;
                mGamePanel.addView(b);
            }
        }
    }

    private void checkClick(){
        List ibs= new ArrayList(clickedIconQueue);
        IconButton ibEarly = (IconButton)ibs.get(0);
        IconButton ibLater = (IconButton)ibs.get(1);
        if(!ibEarly.equals(ibLater) &&
                ibEarly.getIconCode() == ibLater.getIconCode()){
            ibEarly.setVisibility(View.INVISIBLE);
            ibLater.setVisibility(View.INVISIBLE);
            AppData.score++;
            if(AppData.score >= 10) {
                mScoreTextView.setText(String.format(getString(R.string.point), AppData.score + "\nYou Win!"));
                endGame();
            }else
                mScoreTextView.setText(String.format(getString(R.string.point),AppData.score));

        }
    }
    @Override
    public void onClick(View view) {
        IconButton ib = (IconButton)view;
        ib.setBackground(getDrawable(ib.getIconCode()));
        FlipTask ft = new FlipTask(ib, getDrawable(R.drawable.icon_cover));
        ft.execute();
        //store the view
        if(clickedIconQueue.size()>=2){
            clickedIconQueue.remove();
            clickedIconQueue.add(ib);
            checkClick();
        }else if(clickedIconQueue.size() == 0){
            clickedIconQueue.add(ib);
        }else if(clickedIconQueue.size() == 1){
            clickedIconQueue.add(ib);
            checkClick();
        }

    }
    public void openDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to resume your last game?");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(GameActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                resumeGame();
            }
        });

        alertDialogBuilder.setNegativeButton("No,start a new game",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startNewGame();
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGamePanel.removeAllViews();
    }
}