package util;

import android.text.TextUtils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final String TAG = "StringUtils";

    private static final int HEX_VALUE = 0xFF;
    /**
     * 空字符串
     */
    private static final String EMPTY = "";

    /**
     * 私有构造
     */
    private StringUtils() {
    }

    /**
     * 是否为空串(判断是否为null 或 "") isEmpty(null) = true; isEmpty("") = true; isEmpty(" ") = false;
     *
     * @param value 输入的字符串
     * @return 是否为空串
     */
    public static boolean isEmpty(String value) {
        return null == value || 0 == value.length() || "null".equals(value);
    }

    /**
     * 是否为非空字符串
     *
     * @param value 输入的字符串
     * @return 是否为非空字符串
     */
    public static boolean isNotEmpty(String value) {
        return (value != null) && (0 != value.length());
    }

    /**
     * 是否为空格串(判断是否为null 、"" 或 " ") isBlank(null) = true; isBlank("") = true; isBlank(" ") = true;
     *
     * @param value 输入的字符串
     * @return 是否为空格串
     */
    public static boolean isBlank(String value) {
        //是否为空串，如果为空串直接返回
        return null == value || 0 == value.length() || EMPTY.equals(value.trim());
    }

    /**
     * 字符串是否为非空
     *
     * @param value 输入的字符串
     * @return 是否为非空格串
     */
    public static boolean isNoBlank(String value) {
        return !isBlank(value);
    }

    /**
     * trim字符串操作（如果字符串为空，则返回传入的默认值）
     *
     * @param value    需要处理的字符串
     * @param defValue 默认值字符串
     * @return 处理后的字符串
     */
    public static String defaultIfBlank(String value, String defValue) {
        //是否为空格字符串
        if (isBlank(value)) {
            return defValue;
        }

        //将结果进行trim操作
        return value.trim();
    }


    /**
     * 字符串清理
     *
     * @param value 需要处理的字符串
     * @return 清理后的字符串
     */
    public static String emptyIfBlank(String value) {
        return null == value ? EMPTY : value.trim();
    }

    /**
     * 字符串trim并转小写
     *
     * @param value 待处理的字符串
     * @return 转小写后字符串
     */
    public static String trimAndLowerCase(String value) {
        return str2LowerCase(emptyIfBlank(value));
    }

    /**
     * 字符串trim并转大写
     *
     * @param value 待处理的字符串
     * @return 转大写后字符串
     */
    public static String trimAndUpperCase(String value) {
        return str2UpperCase(emptyIfBlank(value));
    }

    /**
     * ToString方法
     *
     * @param object 对象
     * @return 描述信息
     */
    public static String trimAndToString(Object object) {
        return null == object ? EMPTY : object.toString().trim();
    }

    /**
     * 判断字符串 是否为null或者空串（trim后）
     *
     * @param string 字符串
     * @return 是否为null或者空串（trim后）
     */
    public static boolean isNull(String string) {
        if (null != string) {
            string = string.trim();
            if (string.length() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串截取<BR>
     * 截取发生异常，返回原始值
     *
     * @param originalStr 原始字符串
     * @param beginIndex  截取起始位置
     * @return 截取后字符串
     */
    public static String cutString(String originalStr, int beginIndex) {
        if (isEmpty(originalStr)) {
            return originalStr;
        }

        try {
            return originalStr.substring(beginIndex);
        } catch (IndexOutOfBoundsException e) {
            return originalStr;
        }
    }

    /**
     * 字符串截取<BR>
     * 截取发生异常，返回原始值
     *
     * @param originalStr 原始字符串
     * @param beginIndex  截取起始位置
     * @param endIndex    截取结束位置
     * @return 截取后字符串
     */
    public static String cutString(String originalStr, int beginIndex, int endIndex) {
        if (isEmpty(originalStr)) {
            return originalStr;
        }

        try {
            return originalStr.substring(beginIndex, endIndex);
        } catch (IndexOutOfBoundsException e) {
            return originalStr;
        }
    }

    /**
     * 把字符串转换成大写字符串
     *
     * @param source 原字符串
     * @return 大写字符串
     */
    public static String str2UpperCase(String source) {
        if (TextUtils.isEmpty(source)) {
            return source;
        }

        return source.toUpperCase(Locale.US);
    }

    /**
     * 把字符串转换成小写字符串
     *
     * @param source 原字符串
     * @return 小写字符串
     */
    public static String str2LowerCase(String source) {
        if (TextUtils.isEmpty(source)) {
            return source;
        }

        return source.toLowerCase(Locale.US);
    }

    /**
     * 字节转十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String hex;
        for (int i = 0; i < bytes.length; i++) {
            hex = Integer.toHexString(HEX_VALUE & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 判断两个String是否相等。防止空指针
     *
     * @param str1 第一个string
     * @param str2 第二个string
     * @return 是否相等
     */
    public static boolean isEqual(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }
        return str1.equals(str2);
    }

    /**
     * 判断是否是手机号
     * @param mobiles 手机号码
     * @return true or false
     */
    public static boolean isMobileNum(String mobiles) {
        if(StringUtils.isEmpty(mobiles)) {
            return false;
        }
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +

                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern
                .compile(regExp);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 匿名化处理手机号
     * @param phonenum 手机号
     * @return 匿名化后的示字符串
     */
    public static String anonyMobileNum(String phonenum) {
        if(isMobileNum(phonenum)) {
            if (phonenum.length() > 6) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < phonenum.length(); i++) {
                    char c = phonenum.charAt(i);
                    if (i >= 3 && i <= 6) {
                        sb.append('*');
                    } else {
                        sb.append(c);
                    }
                }
                return sb.toString();
            }
        }
        return phonenum;
    }

}
