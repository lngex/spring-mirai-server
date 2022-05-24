package cn.ling.bot.util;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;

import java.awt.*;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringJoiner;

/**
 *
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class TestUtils {


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