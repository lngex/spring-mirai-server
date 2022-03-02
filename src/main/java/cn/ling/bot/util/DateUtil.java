package cn.ling.bot.util;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间工具类
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class DateUtil {

    /**
     * 计算时间的差值
     *
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return 时间集合 健day，hour，min，s - 天，时，分，秒
     */
    public static Map<String, Long> difference(Date startTime, Date endTime) {
        long l = endTime.getTime() - startTime.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        HashMap<String, Long> map = new HashMap<>();
        map.put("day", day + 1);
        map.put("hour", hour);
        map.put("min", min);
        map.put("s", s);
        return map;
    }

    /**
     * 时间字符串拼接
     *
     * @param map 储存时间的集合，健 day,hour,min,s
     * @return 时间字符串 xx天xx时xx分
     */
    public static String dateStr(Map<String, Long> map) {
        StringBuilder str = new StringBuilder("");
        Long day = map.get("day");
        Long hour = map.get("hour");
        Long min = map.get("min");
        Long s = map.get("s");
        if (day != null && !Long.valueOf(0).equals(day)) {
            str.append(day).append("天");
        }
        if (hour != null && !Long.valueOf(0).equals(hour)) {
            str.append(hour).append("时");
        }
        if (min != null && !Long.valueOf(0).equals(min)) {
            str.append(min).append("分");
        }
        return str.toString();
    }

    /**
     * 返回当前时辰
     *
     * @return 当前时辰
     */
    public static String date() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String str = df.format(date);
        int a = Integer.parseInt(str);
        if (a >= 0 && a <= 5) {
            return "凌晨";
        }
        if (a > 5 && a <= 12) {
            return "上午";
        }
        if (a > 12 && a <= 13) {
            return "中午";
        }
        if (a > 13 && a <= 18) {
            return "下午";
        }
        if (a > 18 && a <= 24) {
            return "晚上";
        }
        return null;
    }
static class User{
        String name;
        String age;

    public User(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getAge() {
        return age;
    }

    public User setAge(String age) {
        this.age = age;
        return this;
    }
}

    public static void main(String[] args) {
        User user = new User("谢鹏", "18");
        Gson gson = new Gson();
        String s = gson.toJson(user);
        System.out.println(s);
        byte[] bytes1 = s.getBytes(StandardCharsets.UTF_8);
        String string = Arrays.toString(bytes1);
        System.out.println(string);
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        String string1 = Arrays.toString(bytes);
        System.out.println(string1);
    }
}
