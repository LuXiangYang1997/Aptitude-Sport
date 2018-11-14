package com.huasport.smartsport.util.edittext;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EdittextUtil {

    private EditText editText;
    private EdittextListener edittextListener;

    public EdittextUtil(EditText editText,EdittextListener edittextListener) {
        this.editText = editText;
        this.edittextListener = edittextListener;
        init();
    }

    private void init() {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                edittextListener.beforeTextChanged(s,start,count,after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edittextListener.onTextChanged(s,start,before,count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edittextListener.afterTextChanged(s);
            }
        };

        editText.addTextChangedListener(textWatcher);

    }


}
