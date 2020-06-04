package com.example.customedit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * @author: Administrator
 * @date: 2020-06-04
 */
public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {


    private Context context;
    private int maxWidth;
    private int maxHeight;


    @Override
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public CustomEditText(Context context) {
        super(context);
        this.context = context;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        initView();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        initView();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        initView();
    }


    float oldY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldY = event.getY();
                requestFocus();
                break;
            case MotionEvent.ACTION_MOVE:
                float newY = event.getY();
                if (Math.abs(oldY - newY) > 20) {
                    clearFocus();
                }
                break;
            case MotionEvent.ACTION_UP:

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

//    public SpannableString insertImg(String path) {
//        Editable edit_text = getEditableText();
//        int index = getSelectionStart(); // 获取光标所在位置
//        Log.e("光标位置", index + "");
//        SpannableString newLine = new SpannableString("\n");
////        edit_text.insert(index, newLine);//插⼊图⽚前换⾏
//        File file = new File(path);
//        Drawable drawable = null;
//        String imgPath = path;
//        path = "☆TextSizeImg" + path + "☆";
//        SpannableString spannableString = new SpannableString(path);
//
//        drawable = getResources().getDrawable(R.drawable.test);
//        maxHeight = maxWidth * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
//        drawable.setBounds(0, 0, maxWidth, maxHeight);
//        spannableString.setSpan(new ImageSpan(drawable), 0, path.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//
//        if (index < 0 || index >= edit_text.length()) {
//            edit_text.append(spannableString);
//        } else {
//            edit_text.insert(index, spannableString);
//        }
////        edit_text.insert(index, newLine);//插⼊图⽚后换⾏
//        return spannableString;
//    }

    /*相机 相册 插入图片
     * path  插入文本中显示
     * realPath 图片实际位置
     *
     * 图片在本地可以直接加载
     * */
    public SpannableString insertImgFormCA(String path) {
        Editable edit_text = getEditableText();
        int index = getSelectionStart(); // 获取光标所在位置
        Log.e("光标位置", index + "");
        SpannableString newLine = new SpannableString("\n");
        edit_text.insert(index, newLine);//插⼊图⽚前换⾏

        Drawable drawable = getResources().getDrawable(R.drawable.test);
        path = "☆TextSizeImg" + path + "☆";
        SpannableString spannableString = new SpannableString(path);

        maxHeight = maxWidth * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
        drawable.setBounds(0, 0, maxWidth, maxHeight);
        spannableString.setSpan(new ImageSpan(drawable), 0, path.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.insert(index, newLine);//插⼊图⽚后换⾏
        return spannableString;
    }

    public void initView() {
        /**
         * 输入框内容变化监听<br/>
         * 1.当文字内容产生变化的时候实时更新UI
         */
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 文字改变刷新UI
                refreshEditTextUI(s.toString());
            }
        });
    }

    /**
     * EditText内容修改之后刷新UI
     *
     * @param
     */
    private void refreshEditTextUI(String content) {

        /**
         * 内容变化时操作<br/>
         * 1.查找匹配所有话题内容 <br/>
         * 2.设置话题内容特殊颜色
         */

        if (TextUtils.isEmpty(content)) {
            return;
        }


        Editable editable = getText();

        Log.e("------------刷新内容", editable.toString());
        String[] strs = editable.toString().split("☆");


        int itemStart = 0;
        int itemEnd = 0;
        int itemIndex = 0;


        for (int i = 0; i < strs.length; i++) {

            String item = strs[i];
            itemIndex = editable.toString().indexOf(item);

            if (i > 0) {
                itemStart = itemIndex + 10;
                itemEnd = itemStart + item.length() - 10;
            } else {
                itemStart = itemIndex;
                itemEnd = item.length();
            }

            if (item.startsWith("TextSize14")) {
//                editable.setSpan(new StyleSpan(Typeface.NORMAL), itemStart, itemEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                editable.setSpan(new AbsoluteSizeSpan(14, true), itemStart, itemEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (item.startsWith("TextSize16")) {
//                editable.setSpan(new StyleSpan(Typeface.NORMAL), itemStart, itemEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                editable.setSpan(new AbsoluteSizeSpan(16, true), itemStart, itemEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (item.startsWith("TextSize18")) {
//                editable.setSpan(new StyleSpan(Typeface.BOLD), itemStart, itemEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                editable.setSpan(new AbsoluteSizeSpan(18, true), itemStart, itemEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (item.startsWith("TextSizeImg")) {

            } else {

            }
        }
    }

    /*修改字体大小  0小标题 1正文 2大标题 */
    public SpannableString changeSize(int size) {

        String indi = "";
        switch (size) {
            case 0:
                indi = "☆TextSize14";
                break;
            case 1:
                indi = "☆TextSize16";
                break;
            case 2:
                indi = "☆TextSize18";
                break;
        }

        Editable editable = getEditableText();
        int index = getSelectionStart();
        SpannableString spannableString = new SpannableString(indi);
        Bitmap bitmap = null;
        ImageSpan imageSpan = new ImageSpan(context, bitmap);
        spannableString.setSpan(imageSpan, 0, indi.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (index < 0 || index >= editable.length()) {
            editable.append(spannableString);
        } else {
            editable.insert(index, spannableString);
        }
        return spannableString;
    }

    public String getContentList() {
        JSONArray jsonArray = new JSONArray();
        String[] strs = this.getEditableText().toString().split("☆");
        for (int i = 0; i < strs.length; i++) {
            String item = strs[i];

            JSONObject jsonObject = new JSONObject();
            if (item.startsWith("TextSize14")) {
                jsonObject.put("font", 14);
                jsonObject.put("bold", 0);
                jsonObject.put("title", item.substring(10));
            } else if (item.startsWith("TextSize16")) {
                jsonObject.put("font", 16);
                jsonObject.put("bold", 0);
                jsonObject.put("title", item.substring(10));
            } else if (item.startsWith("TextSize18")) {
                jsonObject.put("font", 18);
                jsonObject.put("bold", 1);
                jsonObject.put("title", item.substring(10));
            } else if (item.startsWith("TextSizeImg")) {
                jsonObject.put("font", 14);
                jsonObject.put("bold", 0);
                jsonObject.put("title", "[image]");
                jsonObject.put("image", item.substring(11));
            } else {
                jsonObject.put("font", 14);
                jsonObject.put("bold", 0);
                jsonObject.put("title", item);
            }
            jsonArray.add(jsonObject);
        }


        return jsonArray.toJSONString();
    }

    //    获取图片
//    public String getFirstImg() {
//        getContentList();
//        String img = null;
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getTitle().equals("[image]")) {
//                img = list.get(i).getImage();
//                Log.e("获取tupian" + i, img);
//                return img;
//            }
//        }
//
//        return null;
//    }

    //    获取内容缩略
//    public String getSmallContent() {
//        getContentList();
//        String content = "";
//        for (int i = 0; i < list.size(); i++) {
//            RecordContentModel item = list.get(i);
//            if(item.getTitle().)
//        }
//    }

    public void setContent() {

    }
}
