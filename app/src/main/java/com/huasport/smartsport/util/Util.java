package com.huasport.smartsport.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.WindowManager;

import com.huasport.smartsport.constant.StatusVariable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Util {

    /**
     * 判断手机号是否符合规范
     *
     * @param mobileNums 输入的手机号
     * @return
     */
    public static boolean isPhoneNumber(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(16[0-9])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (EmptyUtil.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    /**
     * 获取版本name
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            int versioncode = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Context context, float bgAlpha) {
        Activity activity = (Activity) context;
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 拍照获取图片路径
     */
    public static String getPathForData(Context context,Intent data){

        Bundle bundle = data.getExtras();
        // 转换图片的二进制流
        Bitmap bitmap = (Bitmap) bundle.get("data");
        File path = new File(context.getCacheDir() + File.separator + System.currentTimeMillis() + ".jpg");
        String picPath = saveMyBitmap(path.getName(), bitmap);
        return picPath;

    }
    /**
     * 保存bitmap到SD卡
     *
     * @param bitName 保存的名字
     * @param mBitmap 图片对像
     *                return 生成压缩图片后的图片路径
     */
    public static String saveMyBitmap(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/" + bitName + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("在保存图片时出错：" + e.toString());
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        } catch (Exception e) {
            return "create_bitmap_error";
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "/sdcard/" + bitName + ".png";
    }

    /**
     * 设置textView中某个字符颜色
     *
     * @param text          字符串
     * @param colorResourse 颜色
     * @param start         开始
     * @param end           结束
     * @return
     */
    public static SpannableStringBuilder setSpan(String text, int colorResourse, int start, int end) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(colorResourse);
        spannableStringBuilder.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    /**
     * 去除换行符
     *
     * @param str
     * @return
     */
    public static String reMoveLast(String str) {

        int a = 0;
        String removeStr = "";
        String producestring = str.replaceAll(" ", "");
        for (int i = producestring.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            String s = String.valueOf(c);
            if (s.equals("\n")||s.equals("<br>")||s.equals("<p>")||s.equals("</p>")||s.equals("<ol>")||s.equals("</ol>")||s.equals("<li>")
                    ||s.equals("<em>")||s.equals("</em>")||s.equals("<div>")||s.equals("</div>")||s.equals("<b>")||s.equals("</b>")||s.equals("<i>")||s.equals("</i>")
                    ||s.equals("<span>")||s.equals("</span>")||s.equals("<strong>")||s.equals("</strong>")||s.equals("&amp;")||s.equals("&lt;")||s.equals("&gt;")
                    ||s.equals("&nbsp;")) {
                a++;
            } else {
                String substring = producestring.substring(0, producestring.length() - a);
                removeStr = substring;
                break;

            }
        }
        if (!EmptyUtil.isEmpty(removeStr)) {
            return removeStr;
        } else {
            return "";
        }
    }

    /**
     * 获取html纯文本
     */
    public static String getHtmlContent(String htmlStr) {

        String txtcontent = Jsoup.parse(htmlStr).text();
        return txtcontent;

    }

    /**
     * 图片自动适应 过宽缩到100%,正常的不放大
     */
    public static String getFinalContent(String htmltext) {
        return StatusVariable.SHEAD + htmltext + "</body></html>";
    }

    /**
     * 将html文本内容中包含img标签的图片，宽度变为屏幕宽度，高度根据宽度比例自适应
     **/
    public static String getAPPNewContent(String htmltext) {
        try {
            org.jsoup.nodes.Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {

                element.attr("vspace", "10");
            }

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }

}
