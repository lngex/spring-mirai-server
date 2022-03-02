package cn.ling.bot.util;

import org.springframework.util.StringUtils;

/**
 * 消息工具类
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class MessageUtils {
    
    public static String toMe(String msg){
        if (!StringUtils.isEmpty(msg)){
            msg = msg.replaceAll("小爱","大聪明");
            msg = msg.replaceAll("小米","巨人");
        }
        return msg;
    }

    public static String toBe(String msg){
        if (!StringUtils.isEmpty(msg)){
            msg = msg.replaceAll("大聪明","小爱");
            msg = msg.replaceAll("巨人","小米");
        }
        return msg;
    }
}
