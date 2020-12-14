package com.dapeng.utils_lib;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SplitUtils {

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    /**
     * 正则表达式：验证手机号 (模糊匹配)
     */
    public static final String REGEX_PHONE_LIKE="^1[3-9]\\d{9}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_PHONE_LIKE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *  15全是数字，18位最后一位可能是X/x
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 正则匹配 返回值是一个SpannableString 即经过变色处理的数据
     */
    public static SpannableString matcherSearchText(int color, String text, String keyword) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableString("");
        }
        SpannableString spannableString = new SpannableString(text);
        //条件 keyword
        Pattern pattern = Pattern.compile(keyword);
        //匹配
        Matcher matcher = pattern.matcher(text.toLowerCase());
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            //ForegroundColorSpan 需要new 不然也只能是部分变色
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        //返回变色处理的结果
        return spannableString;
    }

    /**
     * 匹配双引号及其内容给设置颜色
     *
     * @param color
     * @param text
     * @return
     */
    public static SpannableString matcherDoubleMarksText(int color, String text) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableString("");
        }
        SpannableString spannableString = new SpannableString(text);
        //条件 keyword
        Pattern pattern = Pattern.compile("(?<=[“|\"]).*?(?=[”|\"])");
        //匹配
        Matcher matcher = pattern.matcher(text.toLowerCase());
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            //ForegroundColorSpan 需要new 不然也只能是部分变色
            try {
                spannableString.setSpan(new ForegroundColorSpan(color), start - 1, end + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //返回变色处理的结果
        return spannableString;
    }

    /**
     * 匹配¥￥元或数字
     * @param color
     * @param text
     * @return
     */
    public static SpannableString matcherCouponPriceMarksText(int color,String text){
        if (TextUtils.isEmpty(text)) {
            return new SpannableString("");
        }
        SpannableString spannableString = new SpannableString(text);
        //条件 keyword
        Pattern pattern = Pattern.compile("([¥|￥|元])|([\\d.]+)");
        //匹配
        Matcher matcher = pattern.matcher(text.toLowerCase());
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            //ForegroundColorSpan 需要new 不然也只能是部分变色
            try {
                spannableString.setSpan(new ForegroundColorSpan(color), start , end , Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //返回变色处理的结果
        return spannableString;
    }

    /**
     * 数字变色
     * @param color
     * @param text
     * @return
     */
    public static SpannableString matcherSinglePriceMarksText(int color,String text){
        if (TextUtils.isEmpty(text)) {
            return new SpannableString("");
        }
        SpannableString spannableString = new SpannableString(text);
        //条件 keyword
        Pattern pattern = Pattern.compile("([\\d.]+)");
        //匹配
        Matcher matcher = pattern.matcher(text.toLowerCase());
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            //ForegroundColorSpan 需要new 不然也只能是部分变色
            try {
                spannableString.setSpan(new ForegroundColorSpan(color), start , end , Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //返回变色处理的结果
        return spannableString;
    }
    /**
     * 匹配获取?前面路径
     * @param url
     * @return
     */
    public static String matcherInviteUrl(String url){
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.contains("?")) {
            return url.split("\\?")[0];
        }else {
            return url;
        }
    }
}
