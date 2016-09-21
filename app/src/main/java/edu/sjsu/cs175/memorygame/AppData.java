package edu.sjsu.cs175.memorygame;

import java.io.Serializable;

/**
 * Created by Ming on 9/13/16.
 */
public class AppData implements Serializable{
    static boolean isStarted = false;
    static IconButton[][] mIconButtonsArray = null;
    static int score = 0;
//    static boolean isOrientationChanged = false;
    static int lastOrientation = 0;
}
