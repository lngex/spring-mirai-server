package net.lz1998.pbbot.util;

import onebot.OnebotEvent;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
     * @param msg 路由解析
     */
    public static int msgRoute(String msg){
        if(StringUtils.isEmpty(msg)){
            return 0;
        }
        msg = msg.trim();
        if(msg.indexOf("投票踢人") == 0){
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        int count=0;
        while (count++ <= 100){
            System.out.println(count);
        }
    }
}
