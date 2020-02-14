package com.dyamicforms.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dyamicforms.R;
import com.dyamicforms.databinding.ActivityFormBinding;
import com.dyamicforms.model.MCheckboxAnswer;
import com.dyamicforms.model.MEditTextAnswer;
import com.dyamicforms.model.MFeedback;
import com.dyamicforms.model.MRadioButtonAnswer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FormActivity extends AppCompatActivity{

    private ActivityFormBinding binding;
    private Context mContext;
    private ArrayList<MFeedback> mFeedbackArrayList = new ArrayList<>();
    private int id;
    private ArrayList<MEditTextAnswer> editTextAnswerArrayList = new ArrayList<>();
    private ArrayList<MRadioButtonAnswer> mRadioButtonAnswerArrayList = new ArrayList<>();
    private ArrayList<MCheckboxAnswer> mCheckboxAnswerArrayList = new ArrayList<>();
    private ArrayList<String> checkboxAnswer = new ArrayList<>();
    private boolean isRadio, isCheck, isEdit;
    private int countEdittext = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        mContext = this;
        isRadio = isCheck = isEdit = false;
        clickListener();
        addData();
        setData();
    }

    private void addData() {
        mFeedbackArrayList.clear();
        MFeedback mFeedback = new MFeedback("1. How many days do we have in a week?",Constant.TEXT,null);
        mFeedbackArrayList.add(mFeedback);
    }

    private void clickListener() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.btnCancel.setOnClickListener(v -> onBackPressed());
        binding.btnSubmit.setOnClickListener(v -> {
            editTextAnswerArrayList.clear();
            mRadioButtonAnswerArrayList.clear();
            checkboxAnswer.clear();
            mCheckboxAnswerArrayList.clear();
            countEdittext = 0;
            for (int i = 0; i < binding.llForm.getChildCount(); i++) {
                View view = binding.llForm.getChildAt(i);
                if (view instanceof EditText) {
                    countEdittext++;
                    String answer = ((EditText) view).getText().toString();
                    if (!answer.isEmpty()) {
                        MEditTextAnswer mEditTextAnswer = new MEditTextAnswer(Integer.parseInt(String.valueOf(view.getTag())), answer);
                        editTextAnswerArrayList.add(mEditTextAnswer);
                    }
                }
                if (view instanceof RadioGroup) {
                    int i1 = ((RadioGroup) view).getCheckedRadioButtonId();
                    if (i1 != -1) {
                        View radioButton = ((RadioGroup) view).findViewById(i1);
                        int i2 = ((RadioGroup) view).indexOfChild(radioButton);
                        RadioButton radioButton1 = (RadioButton) ((RadioGroup) view).getChildAt(i2);
                        String radioButtonAnswer = radioButton1.getText().toString();
                        MRadioButtonAnswer mRadioButtonAnswer = new MRadioButtonAnswer(Integer.parseInt(String.valueOf(view.getTag())), radioButtonAnswer);
                        mRadioButtonAnswerArrayList.add(mRadioButtonAnswer);
                    }

                }
                if (view instanceof CheckBox) {
                    if (((CheckBox) view).isChecked()) {
                        checkboxAnswer.add(String.valueOf(((CheckBox) view).getText()));
                        MCheckboxAnswer mCheckboxAnswer = new MCheckboxAnswer(Integer.parseInt(String.valueOf(view.getTag())), checkboxAnswer);
                        mCheckboxAnswerArrayList.add(mCheckboxAnswer);
                    }
                } else {
                    Log.e("uff!", "no");
                }
            }

            JSONArray jsonArray = new JSONArray();
            JSONObject editTextJsonObject = new JSONObject();
            JSONObject checkBoxJsonObject = new JSONObject();
            JSONObject radioButtonJsonObject = new JSONObject();

            for (MEditTextAnswer mEditTextAnswer1 : editTextAnswerArrayList) {
                try {
                    editTextJsonObject.put(Constant.ID, mEditTextAnswer1.getId());
                    editTextJsonObject.put(Constant.ANSWER, mEditTextAnswer1.getAnswer());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("hurrah", "Edit Text Id " + mEditTextAnswer1.getId() + " " + " Edit Text Answer" + mEditTextAnswer1.getAnswer());
            }

            for (MRadioButtonAnswer mRadioButtonAnswer1 : mRadioButtonAnswerArrayList) {
                try {
                    radioButtonJsonObject.put(Constant.ID, mRadioButtonAnswer1.getId());
                    radioButtonJsonObject.put(Constant.ANSWER, mRadioButtonAnswer1.getAnswer());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("hurrah", "Radio Button Id - " + mRadioButtonAnswer1.getId() + "Radio Button Answer: " + mRadioButtonAnswer1.getAnswer() + "size" + mRadioButtonAnswerArrayList.size());
            }

            for (MCheckboxAnswer mCheckboxAnswer : mCheckboxAnswerArrayList) {
                try {
                    checkBoxJsonObject.put(Constant.ID, mCheckboxAnswer.getId());
                    checkBoxJsonObject.put(Constant.ANSWER, mCheckboxAnswer.getAnswer());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("hurrah", "Radio Button Id - " + mCheckboxAnswer.getId() + "Radio Button Answer: " + mCheckboxAnswer.getAnswer() + "size" + mCheckboxAnswerArrayList.size());
            }

            if (isEdit) {
                if (editTextAnswerArrayList.isEmpty()) {
                    Constant.showToast(mContext, "Please fill the answer", 0);
                } else if (editTextAnswerArrayList.size() != countEdittext) {
                    Log.e("hii", "clickListener: " + " size " + editTextAnswerArrayList.size() + " hello" + countEdittext);
                    Constant.showToast(mContext, "Please fill the answer", 0);
                } else {
                    jsonArray.put(editTextJsonObject);
                }
            }

            if (isCheck) {
                if (mCheckboxAnswerArrayList.isEmpty()) {
                    Constant.showToast(mContext, "Please select the checkbox", 0);
                } else {
                    jsonArray.put(checkBoxJsonObject);
                }
            }
            if (isRadio) {
                if (mRadioButtonAnswerArrayList.isEmpty()) {
                    Constant.showToast(mContext, "Please select the radiobutton", 0);
                } else {
                    jsonArray.put(radioButtonJsonObject);
                }
            }
        });
    }

    private void setData() {
        for (int i = 0; i <= mFeedbackArrayList.size(); i++) {
            switch (mFeedbackArrayList.get(i).getType()) {
                case Constant.TEXT:
                    isEdit = true;
                    DynamicForms.setTextView(mContext, binding.llForm, mFeedbackArrayList.get(i).getQuestion());
                    DynamicForms.setEditText(mContext, binding.llForm, mFeedbackArrayList.get(i).getId());
                    break;
                case Constant.SINGLE_CHOICE:
                    isCheck = true;
                    DynamicForms.setTextView(mContext, binding.llForm, mFeedbackArrayList.get(i).getQuestion());
                    DynamicForms.setCheckBox(mContext, binding.llForm, mFeedbackArrayList.get(i).getOptions().getOption1(), mFeedbackArrayList.get(i).getId());
                    DynamicForms.setCheckBox(mContext, binding.llForm, mFeedbackArrayList.get(i).getOptions().getOption2(), mFeedbackArrayList.get(i).getId());
                    DynamicForms.setCheckBox(mContext, binding.llForm, mFeedbackArrayList.get(i).getOptions().getOption3(), mFeedbackArrayList.get(i).getId());
                    DynamicForms.setCheckBox(mContext, binding.llForm, mFeedbackArrayList.get(i).getOptions().getOption4(), mFeedbackArrayList.get(i).getId());
                    break;
                case Constant.MULTI_CHOICE:
                    isRadio = true;
                    DynamicForms.setTextView(mContext, binding.llForm, mFeedbackArrayList.get(i).getQuestion());
                    DynamicForms.setRadioGroup(mContext, binding.llForm, mFeedbackArrayList.get(i).getOptions(), mFeedbackArrayList.get(i).getId());
                    break;
            }
        }
    }
}