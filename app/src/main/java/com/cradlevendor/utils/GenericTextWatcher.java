package com.cradlevendor.utils;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.cradlevendor.R;


public class GenericTextWatcher implements TextWatcher {
    private final EditText[] editText;
    private final View view;
    public GenericTextWatcher(View view, EditText editText[])
    {
        this.editText = editText;
        this.view = view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        switch (view.getId()) {

            case R.id.edit_one:
                if (text.length() == 1)
                    editText[1].requestFocus();
                break;
            case R.id.edit_two:

                if (text.length() == 1)
                    editText[2].requestFocus();
                else if (text.length() == 0)
                    editText[0].requestFocus();
                break;
            case R.id.edit_three:
                if (text.length() == 1)
                    editText[3].requestFocus();
                else if (text.length() == 0)
                    editText[1].requestFocus();
                break;
            case R.id.edit_four:
                if (text.length() == 1)
                    editText[4].requestFocus();
                else if (text.length() == 0)
                    editText[2].requestFocus();
                break;
            case R.id.edit_five:
                if (text.length() == 1)
                    editText[5].requestFocus();
                else if (text.length() == 0)
                    editText[3].requestFocus();
                break;
            case R.id.edit_six:
                if (text.length() == 0)
                    editText[4].requestFocus();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }
}
