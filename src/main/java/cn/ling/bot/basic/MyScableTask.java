package cn.ling.bot.basic;

import cn.ling.bot.basic.constant.MapConstant;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class MyScableTask {

    @Scheduled(cron = "0 0 0 */1 * ?")
    public void task1(){
        MapConstant.GROUPUSERMAP.values().forEach(e->{
            e.setIsSingin(false);
        });
    }
}
