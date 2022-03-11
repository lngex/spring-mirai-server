package cn.ling.bot.util;

import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 路由工具
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class RouteUtils {

    /**
     * 0 无
     * 1投票踢人
     *
     * @param msg 路由解析
     */
    public static int msgRoute(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return 0;
        }
        msg = msg.trim();
        if (msg.indexOf("投票踢人") == 0) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        String str1 = "abcdefg*&^7";
        String str2 = "简书谢谢，。";
        byte[] bytes = str1.getBytes(StandardCharsets.UTF_8);
        System.out.println("str1：" + Arrays.toString(bytes));
        byte[] bytes1 = str2.getBytes(StandardCharsets.UTF_8);
        System.out.println("str2：" + Arrays.toString(bytes1));
        bytes1[1] = -82;
        String string = new String(bytes1, StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(bytes1));
        System.out.println(string);
    }
}
