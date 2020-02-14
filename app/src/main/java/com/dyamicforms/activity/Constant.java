package com.dyamicforms.activity;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Constant {
    public static final String ID = "id";
    public static final String ANSWER = "answer";
    public static final String TEXT  = "text";
    public static final String MULTI_CHOICE  = "multi-choice";
    public static final String SINGLE_CHOICE  = "single-choice";

    // Show Toast
    public static void showToast(Context context, String msg, int duration) {
        if (msg == null)
            return;

        if (msg.isEmpty())
            return;

        int gravity = Gravity.BOTTOM; // the position of toast
        int xOffset = 0; // horizontal offset from current gravity
        int yOffset = 40;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();
    }

}
