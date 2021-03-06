package com.huasport.smartsport.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.huasport.smartsport.constant.StatusVariable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Util {

    private static String s;
    private static File file;

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

    /**
     * 打开关闭软键盘
     */
    public static void softBoard(final Context context) {

        Handler handler =
                new Handler();

        handler.postDelayed(new Runnable() {

            @Override

            public void run() {

                InputMethodManager imm = (InputMethodManager) context

                        .getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm.isActive()) {

                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,

                            InputMethodManager.HIDE_NOT_ALWAYS);

                }

            }

        }, 0);

    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Context context) {
        Activity activity = (Activity) context;
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 拼接ID
     * @param list
     * @return
     */
    public static String strbuilder(List<String> list) {

        StringBuilder str = new StringBuilder();
        //将多个作品ID进行拼接，来实现多选删除
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                str.append(list.get(i));
            } else {
                str.append(list.get(i));
                str.append(",");
            }
            Log.e("String", str.toString());
            s = str.toString();
        }
        return s;
    }

    /**
     * 照片转byte二进制 * @param imagepath 需要转byte的照片路径 * @return 已经转成的byte * @throws Exception
     */
    public static byte[] readStream(String imagepath) throws Exception {
        FileInputStream fs = new FileInputStream(imagepath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer))) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        fs.close();
        return outStream.toByteArray();
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 字节流转成file文件
     *
     * @param bytes
     * @return
     */
    public static File bytesToImageFile(byte[] bytes) {
        try {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+System.currentTimeMillis()+".jpeg");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes, 0, bytes.length);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static File getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath+"//"+fileName+".jpg");
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 改变字体颜色
     * @param color
     * @param text
     * @param keyword
     * @return
     */
    public static SpannableString matcherSearchText(int color, String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    /**
     * 保存截图
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片路径
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "zhinengtiyu");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //当前时间来命名图片
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Util.insertImageAlbum(context, file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

        Toast.makeText(context, "图片保存到本地成功", Toast.LENGTH_SHORT).show();

    }

    //文件插入系统图库
    public static void insertImageAlbum(Context context, String picPath) {
        try {
            String[] picPaths = picPath.split("/");
            String fileName = picPaths[picPaths.length - 1];
            MediaStore.Images.Media.insertImage(context.getContentResolver(), picPath, fileName, "截图");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            LogUtil.i( "内存溢出...");
            e.printStackTrace();
        }
    }
    /**
     * @param view 需要截取图片的view
     * @return 截图
     */
    public static Bitmap getBitmap(View view, Activity activity) throws Exception {

        View screenView = activity.getWindow().getDecorView();
        screenView.setDrawingCacheEnabled(false);
        screenView.buildDrawingCache();

        //获取屏幕整张图片
        Bitmap bitmap = screenView.getDrawingCache();

        if (bitmap != null) {

            //需要截取的长和宽
            int outWidth = view.getWidth();
            int outHeight = view.getHeight();

            //获取需要截图部分的在屏幕上的坐标(view的左上角坐标）
            int[] viewLocationArray = new int[2];
            view.getLocationOnScreen(viewLocationArray);

            //从屏幕整张图片中截取指定区域
            bitmap = Bitmap.createBitmap(bitmap, viewLocationArray[0], viewLocationArray[1], outWidth, outHeight);


            Log.e("Bitmap", bitmap.toString());
        }

        return bitmap;
    }


    /**
     * 获取版本号
     */
    public static int getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            int versioncode = info.versionCode;
            return versioncode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 判断当前wifi网络是否可用
     * Created by 刘成龙 on 2018/5/17.
     */
    public static boolean isWifiConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }

        return false;
    }

    /**
     * 判断当前移动网络是否可用
     * Created by 刘成龙 on 2018/5/17.
     */
    public static boolean isMobileConnected(Context context) {
        boolean temp = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (info != null) {
            temp = info.isAvailable();
        }
        return temp;
    }

    /**
     * 保留两位小数
     *
     * @param count 金额
     * @return
     */
    public static String decimal(double count) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String acount = decimalFormat.format(count);
        return acount;
    }

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpaChat(EditText editText) {
        InputFilter filter_space = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" "))
                    return "";
                else
                    return null;
            }
        };
        InputFilter filter_speChat = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                String speChat = "[`~!@#_$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）— +|{}【】‘；：”“’。，、？~]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(charSequence.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter_space, filter_speChat});
    }

    /**
     * 对字符串处理特殊符号
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
    // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * url转成Bitmap
     * @param url
     * @return
     */
    public static Bitmap decodeUriAsBitmapFromNet(String url) {

        byte[] htmlByteArray = Util.getHtmlByteArray(url);
        Bitmap bitmap = BitmapFactory.decodeByteArray(htmlByteArray, 0, htmlByteArray.length);

        return bitmap;

    }
    public static byte[] getHtmlByteArray(final String url) {
        URL htmlUrl = null;
        InputStream inStream = null;
        try {
            htmlUrl = new URL(url);
            URLConnection connection = htmlUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = inputStreamToByte(inStream);

        return data;
    }

    public static byte[] inputStreamToByte(InputStream is) {
        try {
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();

        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


}
