package cn.ling.bot.basic;

import cn.ling.bot.async.DatePersistenceAsync;
import cn.ling.bot.basic.constant.MapConstant;
import cn.ling.bot.basic.domain.User;
import cn.ling.bot.plugin.IntercepterPlugin;
import cn.ling.bot.util.MessageUtils;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import net.lz1998.pbbot.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * TODO
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class MyScableTask {

    @Autowired
    private BotContainer botContainer;

    @Autowired
    private DatePersistenceAsync datePersistenceAsync;

    @Scheduled(cron = "0 0 0 */1 * ?")
    public void task1() {
        MapConstant.GROUPUSERMAP.values().forEach(e -> {
            e.setIsSingin(false);
        });
    }

    @Scheduled(cron = "0 0 22 * * ?")
    public void task2() {
        if (MapConstant.POWERBALL.isEmpty()) {
            return;
        }
        int number = MessageUtils.number();
        int i4 = number / 100000;
        int i = number / 10000;
        int i1 = number / 1000;
        int i2 = number / 100;
        int i3 = number / 10;
        StringBuffer str = new StringBuffer();
        StringBuffer str2 = new StringBuffer();
        StringBuffer str3 = new StringBuffer();
        HashSet<Long> bots = new HashSet<>();
        HashSet<Long> groups = new HashSet<>();
        MapConstant.POWERBALL.keySet().forEach(e -> {
            String[] split = e.split(":");
            long key = Long.parseLong(split[0]) + Long.parseLong(split[1]);
            groups.add(Long.parseLong(split[0]));
            bots.add(Long.parseLong(split[2]));
            List<Integer> integers = MapConstant.POWERBALL.get(e);
            for (Integer e2 : integers) {
                User user = MapConstant.GROUPUSERMAP.get(key);
                if (user == null) {
                    continue;
                }
                if (number == e2) {
                    str.append(split[2]).append("、");
                    user.setIntegrate(user.getIntegrate().add(new BigDecimal("5000000")));
                } else if (i3 == e2 / 10) {
                    str2.append(split[2]).append("、");
                    user.setIntegrate(user.getIntegrate().add(new BigDecimal("3000000")));
                } else if (i2 == e2 / 100) {
                    str3.append(split[2]).append("、");
                    user.setIntegrate(user.getIntegrate().add(new BigDecimal("1000000")));
                } else if (i1 == e2 / 1000) {
                    user.setIntegrate(user.getIntegrate().add(new BigDecimal("100000")));
                } else if (i == e2 / 10000) {
                    user.setIntegrate(user.getIntegrate().add(new BigDecimal("10")));
                } else if (i4 == e2 / 100000) {
                    user.setIntegrate(user.getIntegrate().add(new BigDecimal("2")));
                }
                MapConstant.GROUPUSERMAP.put(key, user);
            }
        });
        datePersistenceAsync.userPersistence();
        Map<Long, Bot> bots1 = botContainer.getBots();
        bots.stream().filter(e -> {
            Bot bot = bots1.get(e);
            return bot != null;
        }).forEach(e -> {
            Bot bot = bots1.get(e);
            groups.stream().filter(IntercepterPlugin.GROUPS::contains).forEach(g -> {
                Msg.builder().text("------->本期彩票开奖号码为" + number + "<--------\n" +
                        "一等奖获得者:" + str + "\n" +
                        "二等奖获得者:" + str2 + "\n" +
                        "三等奖获得者:" + str3 + "\n"
                ).sendToGroup(bot, g);
            });
        });
        MapConstant.POWERBALL.clear();
    }
}
