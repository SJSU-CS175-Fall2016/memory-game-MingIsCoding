package edu.sjsu.cs175.memorygame;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;

/**
 * Created by Ming on 9/14/16.
 */
public class FlipTask extends AsyncTask {
    private IconButton ib;
    private Drawable defaultIcon;
    public FlipTask(IconButton ib, Drawable di){
        this.ib = ib;
        this.defaultIcon = di;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
//        int time = Integer.parseInt(objects[0]);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publishProgress();
        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        ib.setBackground(defaultIcon);
        super.onProgressUpdate(values);

    }
}
