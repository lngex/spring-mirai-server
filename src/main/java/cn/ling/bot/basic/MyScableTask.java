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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static void main(String[] args) throws InterruptedException {
        String str = "-------------------------";
        String horse = "\uD83D\uDC0E";
        AtomicInteger atomicInteger = new AtomicInteger(0);
        AtomicInteger i = new AtomicInteger(0);
        // 储存名次
        Map<Integer, Integer> map1 = new ConcurrentHashMap<>();
        // 储存赛马
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 26);
        map.put(2, 26);
        map.put(3, 26);
        map.put(4, 26);
        int size = map.size();
        // 在所有赛马的成绩出来后就停止赛马
        while (map1.size() < size) {
            Thread.sleep(2000);
            // 储存每次循环结果通知
            StringBuilder builder1 = new StringBuilder("");
            map.entrySet().parallelStream().filter(e -> {
                // 储存到达终点的马成绩并停止奔跑
                if (e.getValue() <= 1) {
                    return false;
                }
                return true;
            }).forEach(e -> {
                // 每次循环奔跑步数
                e.setValue(e.getValue() - ThreadLocalRandom.current().nextInt(1, 6));
                if (e.getValue() <= 1 && map1.get(e.getKey()) == null) {
                    map1.put(e.getKey(), atomicInteger.incrementAndGet());
                }
            });
            builder1.append("第").append(i.incrementAndGet()).append("场").append("\n");
            // 获取名次自增加
            AtomicInteger atomicInteger1 = new AtomicInteger(0);
            // 获取每场结果
            map.entrySet().forEach(e -> {
                if (e.getValue() < 1) {
                    e.setValue(1);
                }
                StringBuilder builder = new StringBuilder(str);
                builder1.append(builder.insert(0, atomicInteger1.incrementAndGet()).insert(e.getValue(), horse)).append("\n");
            });
            System.out.println(builder1);
            System.out.println(map);
            System.out.println(map1);
        }
        System.out.println(map1);
    }

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

    /**
     * 赛马
     * 每晚20点进行
     * 🐎
     */
    @Scheduled(cron = "0 0 20 * * ?")
    public void task3() {

    }
}
