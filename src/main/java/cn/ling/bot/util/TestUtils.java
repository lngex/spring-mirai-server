package cn.ling.bot.util;

import cn.ling.bot.basic.constant.OneGroupConstant;
import cn.ling.bot.basic.constant.PublicConstant;
import cn.ling.bot.basic.constant.xml.XmlConstant;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * TODO
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class TestUtils {


    public static void main(String[] args) throws SocketException {
    }
    public static List<String> getMACAddress() throws SocketException {
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        List<String> list = new ArrayList<>();
        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            if (ni != null && ni.isUp()) {
                byte[] bytes = ni.getHardwareAddress();
                if (bytes != null && bytes.length == 6) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b : bytes) {
                        //与11110000作按位与运算以便读取当前字节高4位
                        sb.append(Integer.toHexString((b & 240) >> 4));
                        //与00001111作按位与运算以便读取当前字节低4位
                        sb.append(Integer.toHexString(b & 15));
                        sb.append("-");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    list.add(sb.toString().toUpperCase());
                }
            }
        }
        return list;
    }
}