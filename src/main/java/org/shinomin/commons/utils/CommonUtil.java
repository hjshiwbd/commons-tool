package org.shinomin.commons.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hjin
 * @cratedate 2013-8-29 下午2:21:21
 */
public class CommonUtil {
    /**
     * 进行SHA1大写加密
     * 
     * @param str
     * @return
     * @author hjin
     */
    public static String encryptSHA1(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] result = md.digest();

        StringBuffer sb = new StringBuffer();

        for (byte b : result) {
            int i = b & 0xff;
            if (i < 0xf) {
                sb.append(0);
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 进行MD5大写加密
     * 
     * @param info
     *            要加密的信息
     * @return String 加密后的字符串
     * @author hjin
     */
    public static String encryptToMD5(String info) {
        byte[] digesta = null;
        try {
            // 得到一个md5的消息摘要
            MessageDigest alga = MessageDigest.getInstance("MD5");
            // 添加要进行计算摘要的信息
            alga.update(info.getBytes("utf-8"));
            // 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将摘要转为字符串
        String rs = byte2hex(digesta);
        return rs;
    }

    /**
     * 将二进制转化为16进制字符串
     * 
     * @param b
     *            二进制字节数组
     * @return String
     * @author hjin
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 邮箱格式校验
     * 
     * @param email
     * @return
     */
    public static boolean emailFormatCheck(String email) {
        if (email == null || "".equals(email)) {
            return false;
        }
        // String reg =
        // "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{1,4}|[0-9]{1,3})(\\]?)$";
        String reg = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z\\.]{2,10})+$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 手机号格式校验
     * 
     * @param mobile
     * @return
     * @author hjin
     * @cratedate 2013-9-10 上午10:37:33
     */
    public static boolean mobileFormatCheck(String mobile) {
        if (mobile == null || "".equals(mobile)) {
            return false;
        }
        String reg = "^1[3|4|5|8][\\d]{9}$";
        return mobile.matches(reg);
    }

    /**
     * 获取http请求的客户IP
     * 
     * @param request
     * @return IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void main(String[] args) {

    }
}
