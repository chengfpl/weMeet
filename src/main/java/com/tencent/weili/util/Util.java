package com.tencent.weili.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

public class Util {

    /*
     * 从微信服务器获取用户的openid与session_key
     */
    public static String getUserWXInfo(String url){
        String result = "";
        BufferedReader in = null;
        InputStream is = null;
        InputStreamReader isr = null;
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.connect();
            is = conn.getInputStream();
            isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            //异常记录
            e.printStackTrace();
        }finally{
            try {
                in.close();
                is.close();
                isr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
     * 类型转换
     * 2018-05-02 08:00:00_2018-05-02 11:00:00 ---> 2018-05-02_0
     */
    public static String transform(String time) {
        if (time.contains("00:00:00") && time.contains("08:00:00")) {
            return time.split("_")[0].split(" ") + "_1";
        }
        if (time.contains("08:00:00") && time.contains("12:00:00")) {
            return time.split("_")[0].split(" ") + "_2";
        }
        if (time.contains("12:00:00") && time.contains("14:00:00")) {
            return time.split("_")[0].split(" ") + "_3";
        }
        if (time.contains("14:00:00") && time.contains("18:00:00")) {
            return time.split("_")[0].split(" ") + "_4";
        }
        if (time.contains("18:00:00") && time.contains("24:00:00")) {
            return time.split("_")[0].split(" ") + "_5";
        }
        return time.split("_")[0].split(" ") + "_0";
    }

    public static boolean checkTime(String time) {
        return time.length() == 39;
    }

    /*
     * 暂时未开放这个接口
     */
    public static String decode(String encryptedData, String sessionKey, String iv) {
        byte[] dataByte = Base64.getDecoder().decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.getDecoder().decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.getDecoder().decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
