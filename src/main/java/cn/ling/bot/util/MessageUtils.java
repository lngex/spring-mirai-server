package cn.ling.bot.util;

import cn.ling.bot.basic.constant.MapConstant;
import onebot.OnebotBase;
import onebot.OnebotEvent;
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

    /**
     * 获取事件qq
     * 1.消息体中存在qq且qq必须在消息体索引1处
     * 2.若条件1不满足则qq必须在字符串最后
     * @param event 事件对象
     * @param prefix 前缀
     * @return qq
     */
    public static long getQq(OnebotEvent.GroupMessageEvent event,int prefix){
        List<OnebotBase.Message> messageList = event.getMessageList();
        String rawMessage = event.getRawMessage().trim();
        String qq = null;
        if (messageList.size() == 2) {
            qq = messageList.get(1).getDataMap().get("qq");
        } else if (messageList.size() == 1 && rawMessage.length() >= (prefix+8)) {
            qq = rawMessage.substring(prefix);
        }
        long l;
        try {
           l = Long.parseLong(qq);
        }catch (Exception e){
            l = 0;
        }
        return l;
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
