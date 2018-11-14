package com.huasport.smartsport.util.edittext;

import android.text.Editable;


public interface EdittextListener {

    /**
     * 输入之前
     * @param s
     * @param start
     * @param count
     * @param after
     */
    void beforeTextChanged(CharSequence s, int start, int count, int after);

    /**
     * text改变
     * @param s
     * @param start
     * @param count
     * @param after
     */
    void onTextChanged(CharSequence s, int start, int count, int after);

    /**
     * 输入之后
     * @param s
     */
    void afterTextChanged(Editable s);

}
