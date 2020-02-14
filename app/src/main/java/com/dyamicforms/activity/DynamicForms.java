package com.dyamicforms.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dyamicforms.R;
import com.dyamicforms.model.MFeedback;

public class DynamicForms {

    public static TextView setTextView(Context mContext, LinearLayout linearLayout, String text) {
        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, dpToPx(16), 0, 0);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(R.style.tvMedium14);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setTypeface(mContext.getResources().getFont(R.font.poppins_medium));
        }
        textView.setTextColor(mContext.getResources().getColor(R.color.black_color));
        linearLayout.addView(textView);
        return textView;
    }

    public static EditText setEditText(Context mContext, LinearLayout linearLayout, int id) {
        EditText editText = new EditText(mContext);
        editText.setTag(id);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, dpToPx(16), 0, 0);
        editText.setLayoutParams(layoutParams);
        editText.setPadding(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setBackground(mContext.getDrawable(R.drawable.ic_feedback_edittext));
        }
        linearLayout.addView(editText);
        return editText;
    }

    public static RadioGroup setRadioGroup(Context mContext, LinearLayout linearLayout, MFeedback.Option option, int id) {
        RadioGroup radioGroup = new RadioGroup(mContext);
        radioGroup.setTag(id);
        RadioButton radioButton1 = new RadioButton(mContext);
        radioButton1.setText(option.getOption1());
        RadioButton radioButton2 = new RadioButton(mContext);
        radioButton2.setText(option.getOption2());
        RadioButton radioButton3 = new RadioButton(mContext);
        radioButton3.setText(option.getOption3());
        RadioButton radioButton4 = new RadioButton(mContext);
        radioButton4.setText(option.getOption4());
        radioGroup.addView(radioButton1);
        radioGroup.addView(radioButton2);
        radioGroup.addView(radioButton3);
        radioGroup.addView(radioButton4);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            radioButton1.setButtonTintList(mContext.getResources().getColorStateList(R.color.dark_blue));
            radioButton2.setButtonTintList(mContext.getResources().getColorStateList(R.color.dark_blue));
            radioButton3.setButtonTintList(mContext.getResources().getColorStateList(R.color.dark_blue));
            radioButton4.setButtonTintList(mContext.getResources().getColorStateList(R.color.dark_blue));
        }
        linearLayout.addView(radioGroup);
        return radioGroup;
    }

    public static CheckBox setCheckBox(Context mContext, LinearLayout linearLayout, String text, int id) {
        CheckBox checkBox = new CheckBox(mContext);
        checkBox.setTag(id);
        checkBox.setText(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkBox.setButtonTintList(mContext.getResources().getColorStateList(R.color.dark_blue));
        }
        linearLayout.addView(checkBox);
        return checkBox;
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
