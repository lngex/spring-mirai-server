package cn.ling.bot.util;

import org.springframework.util.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 消息工具类
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class MessageUtils {

    public static String toMe(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            msg = msg.replaceAll("小爱", "大聪明");
            msg = msg.replaceAll("小米", "巨人");
        }
        return msg;
    }

    public static String toBe(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            msg = msg.replaceAll("大聪明", "小爱");
            msg = msg.replaceAll("巨人", "小米");
        }
        return msg;
    }

    public static Integer number() {
        String str = "0123456789";
        String number = "";
        for (int i = 0; i < 6; i++) {
            if(i == 0){
                number = number + str.charAt(ThreadLocalRandom.current().nextInt(1, str.length()));
            }else {
                number = number + str.charAt(ThreadLocalRandom.current().nextInt(0, str.length()));
            }
        }
        return Integer.parseInt(number);
    }


    public static void main(String[] args) {
        System.out.println(number());
    }
}
