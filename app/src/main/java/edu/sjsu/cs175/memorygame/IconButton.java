package edu.sjsu.cs175.memorygame;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Ming on 9/13/16.
 */
public class IconButton extends ImageButton {
    private int iconCode;
//    private
    public IconButton(Context context) {
        super(context);
    }
    public void setIconCode(int code){
        this.iconCode = code;
    }
    public int getIconCode(){
        return this.iconCode;
    }
}
