package com.huasport.smartsport.ui.pcenter.attention.bean;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {

    // 号码
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

    //上下文对象
    private Context context;
    //联系人提供者的uri
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private String phonestr = "";

    public PhoneUtil(Context context) {
        this.context = context;
    }

    //获取所有联系人
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public List<PhoneAddressBookBean> getPhone() {
        List<PhoneAddressBookBean> phoneDtos = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(phoneUri, new String[]{NUM, NAME}, null, null, null);
        while (cursor.moveToNext()) {
            String string = cursor.getString(cursor.getColumnIndex(NUM));
            String s = removeSpace(string);
            PhoneAddressBookBean phoneDto = new PhoneAddressBookBean(cursor.getString(cursor.getColumnIndex(NAME)), s);
            phoneDtos.add(phoneDto);
        }
        return phoneDtos;
    }

    /**
     * 去除空格回车符等
     *
     * @param s
     */
    public String removeSpace(String s) {

        String regex = "\\s";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.replaceAll("");
    }

}
