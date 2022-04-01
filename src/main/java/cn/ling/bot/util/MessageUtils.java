package cn.ling.bot.util;

import cn.ling.bot.basic.constant.MapConstant;
import org.springframework.util.StringUtils;

import java.util.*;
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
            if (i == 0) {
                if (MapConstant.POWERBALL.size() > 0) {
                    List<Integer> list = new ArrayList<>();
                    MapConstant.POWERBALL.values().forEach(list::addAll);
                    if (list.size() > 0) {
                        number = number + list.get(ThreadLocalRandom.current().nextInt(0, list.size())).toString().charAt(0);
                    }
                } else {
                    number = number + str.charAt(ThreadLocalRandom.current().nextInt(1, str.length()));
                }
            } else {
                number = number + str.charAt(ThreadLocalRandom.current().nextInt(0, str.length()));
            }
        }
        return Integer.parseInt(number);
    }


    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            String s = number().toString().substring(0, 1);
            Integer integer = map.get(s);
            if (integer == null) {
                integer = 0;
            }
            integer = integer + 1;
            map.put(s, integer);
        }
        map.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).forEach(System.out::println);
    }
}
