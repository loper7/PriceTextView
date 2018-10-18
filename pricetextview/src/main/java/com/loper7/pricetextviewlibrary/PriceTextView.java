package com.loper7.pricetextviewlibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * @author LOPER7
 * @date 2017/10/13 13:44
 * @Description: ${todo}(生活不止眼前的苟且)
 */

public class PriceTextView extends TextView {

    //变大字体倍数
    private float bigTimes;
    private String showPrice = "";

    public PriceTextView(Context context) {
        super(context);
        bigTimes = 1.5f;
    }

    public PriceTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bigTimes = 1.5f;
    }

    public PriceTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bigTimes = 1.5f;
    }


    /**
     * 格式化显示
     *
     * @param text price
     * @return 0.00
     */
    @Deprecated
    public PriceTextView parsePrice(String text) {

        double price;
        try {
            price = Double.parseDouble(text);
        } catch (Exception e) {
            price = 0.00;
        }
        showPrice = double2String(price);

        return this;
    }

    /**
     * 显示常态
     */
    @Deprecated
    public void show() {
        super.setText(showPrice);
    }

    /**
     * 显示符号
     */
    @Deprecated
    public PriceTextView showSymbol(String symbol) {
        if (TextUtils.isEmpty(symbol))
            symbol = "";
        super.setText(symbol + showPrice);

        return this;
    }

    /**
     * 显示单位
     */
    @Deprecated
    public PriceTextView showUnit(String unit) {
        if (TextUtils.isEmpty(unit))
            unit = "";
        super.setText(showPrice + unit);

        return this;
    }

    /**
     * 设置变大倍数
     *
     * @param f
     */
    @Deprecated
    public PriceTextView setBigTimes(float f) {
        this.bigTimes = f;
        return this;
    }

    /**
     * 设置、放大字体倍数
     *
     * @param bigTimes 倍数
     * @param text     文字
     */
    public void setText(float bigTimes, CharSequence text) {
        this.bigTimes = bigTimes;
        super.setText(text);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (TextUtils.isEmpty(text)) {
            super.setText(text, type);
            return;
        }

        showPrice = text.toString();
        int dotIndex = showPrice.indexOf(".");
        int startIndex = 0;

        //去掉首位单位符号
        for (int i = 0; i < showPrice.length(); i++) {
            int chrStart = showPrice.charAt(i);
            if (chrStart >= 48 && chrStart <= 57) {
                startIndex = i;
                break;
            } else {
                startIndex = i + 1;
            }

        }
        //字符串中没有数字
        if (startIndex >= showPrice.length()) {
            super.setText(text, type);
            return;
        }
        //末尾变小判断
        if (dotIndex == -1) {
            for (int i = startIndex; i < showPrice.length(); i++) {
                int cgrEnd = showPrice.charAt(i);
                if (cgrEnd < 48 || cgrEnd > 57) {
                    dotIndex = i;
                    break;
                }
            }
            if (dotIndex <= 0)
                dotIndex = showPrice.length();
        }
        //开始位置大于结束位置
        if (startIndex >= dotIndex) {
            super.setText(text, type);
            return;
        }

        Spannable span = new SpannableString(showPrice);
        span.setSpan(new RelativeSizeSpan(bigTimes == 0 ? 1.5f : bigTimes), startIndex, dotIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        super.setText(span, type);
    }

    /**
     * 获取价格
     *
     * @return
     */
    public double getPrice() {
        try {
            return Double.parseDouble(getStringPrice());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取价格
     *
     * @return
     */
    public String getStringPrice() {
        String value = showPrice;
        //去除前面的单位字符
        value = formatStartPrice(value);
        //去除末尾的单位字符
        value = formatEndPrice(value);

        return value;
    }

    /**
     * double类型转两位小数字符
     *
     * @param d
     * @return
     */
    private String double2String(double d) {
        return new DecimalFormat("##0.00").format(d);
    }

    /**
     * 去除头部单位
     *
     * @param value
     * @return
     */
    private String formatStartPrice(String value) {
        String newValue = value;
        for (int i = 0; i < value.length(); i++) {
            int cgrStart = value.charAt(i);
            if (cgrStart < 48 || cgrStart > 57) {
                newValue = value.substring(i + 1, value.length());
            } else
                break;
        }
        return newValue;
    }

    /**
     * 去除末尾单位
     *
     * @param value
     * @return
     */
    private String formatEndPrice(String value) {
        String newValue = value;
        for (int i = 0; i < value.length(); i++) {
            int cgrEnd = value.charAt(value.length() - (i + 1));
            if (cgrEnd < 48 || cgrEnd > 57) {
                newValue = value.substring(0, value.length() - (i + 1));
            } else
                break;
        }
        return newValue;
    }
}
