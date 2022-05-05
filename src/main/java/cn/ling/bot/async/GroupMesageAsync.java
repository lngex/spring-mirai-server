package cn.ling.bot.async;

import cn.ling.bot.basic.constant.MapConstant;
import cn.ling.bot.basic.constant.OneGroupConstant;
import cn.ling.bot.basic.constant.PublicConstant;
import cn.ling.bot.basic.constant.minCartoon.UrlConstant;
import cn.ling.bot.basic.domain.*;
import cn.ling.bot.basic.domain.mincartoon.MinCartoon;
import cn.ling.bot.basic.domain.netease.NeteaseMusic;
import cn.ling.bot.basic.domain.qqMusic.Data;
import cn.ling.bot.basic.task.MapTaskThread;
import cn.ling.bot.basic.task.SpamTaskThread;
import cn.ling.bot.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotApi;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 消息异步处理类
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
public class GroupMesageAsync {

    public static List<String> colours = new ArrayList<>();

    static {
        colours.add("<%ĀĀ\u0007Ð>");
        colours.add("<%ĀĀ\u0007Ñ>");
        colours.add("<%ĀĀ\u0007Ò>");
        colours.add("<%ĀĀ\u0007Ó>");
        colours.add("<%ĀĀ\u0007Ô>");
        colours.add("<%ĀĀ\u0007Õ>");
        colours.add("<%ĀĀ\u0007Ö>");
        colours.add("<%ĀĀ\u0007×>");
        colours.add("<%ĀĀ\u0007Ø>");
        colours.add("<%ĀĀ\u0007Ù>");
        colours.add("<%ĀĀ\u0007Ú>");
        colours.add("<%ĀĀ\u0007Û>");
        colours.add("<%ĀĀ\u0007Ü>");
        colours.add("<%ĀĀ\u0007Ý>");
        colours.add("<%ĀĀ\u0007Þ>");
    }

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;
    @Autowired
    private DatePersistenceAsync datePersistenceAsync;

    /**
     * 个别改名
     *
     * @param event 消息对象
     * @param bot   机器人对象
     */
    @Async
    public void oneRename(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        long groupId = event.getGroupId();
        OnebotApi.GetStrangerInfoResp strangerInfo = bot.getStrangerInfo(event.getUserId());
        String role = event.getSender().getRole();
        String card = EnCodeUtil.transfromOctalToString(event.getSender().getCard()).trim();
        String sex = strangerInfo.getSex();
        Msg text1 = Msg.builder().at(event.getUserId()).text("我觉得你的名片有亿点点不符合规范,现在已帮你改正,不用感谢我了");
        log.info("QQ:{},名片:{},角色:{},性别:{}", event.getUserId(), card, role, sex);
        // 群主不用修改
        if (OneGroupConstant.Role.OWNER.equals(role)) {
            return;
        }
        if (OneGroupConstant.Role.ADMIN.equals(role)) {
            // 是否为管理员
            if (!card.startsWith(OneGroupConstant.Card.ADMIN)) {
                card = this.cardCheckout(OneGroupConstant.Card.ADMIN + card);
                bot.setGroupCard(event.getGroupId(), event.getUserId(), card);
                log.info("群名：{},QQ:{},名片:{}", groupId, event.getUserId(), card);
                bot.sendGroupMsg(groupId, text1, false);
            }
        } else {
            boolean isCard = true;
            // 普通群员
            if (card.startsWith(OneGroupConstant.Card.MALE)
                    || card.startsWith(OneGroupConstant.Card.FEMALE)
                    || card.startsWith(OneGroupConstant.Card.CLOWN)
                    || card.startsWith(OneGroupConstant.Card.CORRUPT)
                    || card.startsWith(OneGroupConstant.Card.KILL)
                    || card.startsWith(OneGroupConstant.Card.TKILL)
            ) {
                isCard = false;
            }
            if (isCard && ThreadLocalRandom.current().nextInt(0, 100) <= 10) {
                card = this.cardCheckout(OneGroupConstant.Card.KILL + card);
                bot.setGroupCard(event.getGroupId(), event.getUserId(), card);
                bot.sendGroupMsg(groupId, text1, false);
                log.info("群名：{},QQ:{},名片:{}", groupId, event.getUserId(), card);
            } else if (isCard && ThreadLocalRandom.current().nextInt(0, 100) == 50) {
                card = this.cardCheckout(OneGroupConstant.Card.TKILL + card);
                bot.setGroupCard(event.getGroupId(), event.getUserId(), card);
                bot.sendGroupMsg(groupId, text1, false);
                log.info("群名：{},QQ:{},名片:{}", groupId, event.getUserId(), card);
            } else if (isCard && OneGroupConstant.Sex.MALE.equals(sex)) {
                card = this.cardCheckout(OneGroupConstant.Card.MALE + card);
                bot.setGroupCard(event.getGroupId(), event.getUserId(), card);
                bot.sendGroupMsg(groupId, text1, false);
                log.info("群名：{},QQ:{},名片:{}", groupId, event.getUserId(), card);
            } else if (isCard && OneGroupConstant.Sex.FEMALE.equals(sex)) {
                card = this.cardCheckout(OneGroupConstant.Card.FEMALE + card);
                bot.setGroupCard(event.getGroupId(), event.getUserId(), card);
                bot.sendGroupMsg(groupId, text1, false);
                log.info("群名：{},QQ:{},名片:{}", groupId, event.getUserId(), card);
            } else if (isCard) {
                card = this.cardCheckout(OneGroupConstant.Card.KILL + card);
                bot.setGroupCard(event.getGroupId(), event.getUserId(), card);
                bot.sendGroupMsg(groupId, text1, false);
                log.info("群名：{},QQ:{},名片:{}", groupId, event.getUserId(), card);
            }
        }
    }

    /**
     * 投票踢人
     *
     * @param bot   机器人对象
     * @param event 用户对象
     */
    @Async
    public void voteOut(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        if (RouteUtils.msgRoute(event.getRawMessage()) != 1) {
            // 投票踢人
            return;
        }
        long groupId = event.getGroupId();
        if (!"at".equals(event.getMessageList().get(1).getType().trim())) {
            // 必须为 “投票踢人+@qq”格式，否则触发投票踢人操作
            return;
        }
        long userId = Long.parseLong(event.getMessageList().get(1).getData().get("qq"));
        String card = bot.getGroupMemberInfo(event.getGroupId(), userId, false).getCard();
        String key = groupId + "::" + userId;
        VoteOut voteOut = MapConstant.VOTE_OUT_MAP.get(key);
        if (voteOut == null) {
            String uuid = UUID.randomUUID().toString();
            // 创建踢人对象
            voteOut = new VoteOut();
            voteOut.setCount(1);
            voteOut.setGroupId(groupId);
            voteOut.setQq(userId);
            voteOut.setUuid(uuid);
            voteOut.getQqs().add(event.getUserId());
            MapConstant.VOTE_OUT_MAP.put(key, voteOut);
            // 创建任务
            MapTaskThread builde = MapTaskThread.builder(key, uuid);
            // 放入定时器
            scheduledExecutorService.schedule(builde, OneGroupConstant.Limit.EXPIRTIME, TimeUnit.MINUTES);
            // 发送消息
            bot.sendGroupMsg(groupId, String.format(OneGroupConstant.message.VOTEOUT, card, voteOut.getCount(), OneGroupConstant.Limit.VOTEOUT), false);
        } else {
            List<Long> qqs = voteOut.getQqs();
            if (qqs.contains(event.getUserId())) {
                bot.sendGroupMsg(groupId, Msg.builder().at(event.getUserId()).text("你已经投过票了"), false);
                return;
            }
            qqs.add(event.getUserId());
            voteOut.setQqs(qqs);
            Integer count = voteOut.getCount() + 1;
            if (OneGroupConstant.Limit.VOTEOUT <= count) {
                // 投票出局
                bot.sendGroupMsg(groupId, userId + "已被投票踢出群聊", false);
                // 踢出
                bot.setGroupKick(groupId, userId, false);
                MapConstant.VOTE_OUT_MAP.remove(key);
            } else {
                voteOut.setCount(count);
                MapConstant.VOTE_OUT_MAP.put(key, voteOut);
                bot.sendGroupMsg(groupId, String.format(OneGroupConstant.message.VOTEOUT, card, voteOut.getCount(), OneGroupConstant.Limit.VOTEOUT), false);
            }
        }
    }

    /**
     * 违禁词
     *
     * @param bot   机器人对象
     * @param event 消息对象
     */
    @Async
    public void bannedWord(Bot bot, OnebotEvent.GroupMessageEvent event) {
        String rawMessage = StringUtil.removeAllBlank(event.getRawMessage().toUpperCase());
        // 违禁词检测
        for (String e : OneGroupConstant.bannedWord.BANNEDWORD_LIST) {
            String max = e.toUpperCase();
            if (rawMessage.contains(max)) {
                long groupId = event.getGroupId();
                long userId = event.getUserId();
                String key = groupId + "::" + userId;
                // 有违禁词 添加Map
                Integer integer = MapConstant.BANNED_MAP.get(key);
                if (integer == null) {
                    integer = 1;
                    MapConstant.BANNED_MAP.put(key, integer);
                    // 禁言
                    bot.setGroupBan(groupId, userId, 10 * 60);
                    // 撤回消息
                    bot.deleteMsg(event.getMessageId());
                    bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(OneGroupConstant.bannedWord.ONE), false);
                } else {
                    integer = integer + 1;
                    MapConstant.BANNED_MAP.put(key, integer);
                    if (integer == 2) {
                        // 第二次
                        bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(OneGroupConstant.bannedWord.TEW), false);
                        bot.setGroupBan(groupId, userId, 20 * 60);
                    } else if (integer == 3) {
                        // 第三次
                        bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(OneGroupConstant.bannedWord.THREE), false);
                        bot.setGroupBan(groupId, userId, 60 * 60);
                    } else if (integer == 4) {
                        // 第四次
                        bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(OneGroupConstant.bannedWord.FOUR), false);
                        bot.setGroupBan(groupId, userId, 60 * 60 * 2);
                    } else if (integer == 5) {
                        // 第五次
                        bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(OneGroupConstant.bannedWord.FIVE), false);
                        bot.setGroupBan(groupId, userId, 180 * 2 * 60);
                        MapConstant.BANNED_MAP.remove(key);
                    }
                    bot.deleteMsg(event.getMessageId());
                }
                break;
            }
        }
    }

    /**
     * 小爱机器人
     *
     * @param bot
     * @param event
     */
    @Async
    public void minAi(Bot bot, OnebotEvent.GroupMessageEvent event) {
        if (event.getUserId() == 2838686130L) {
            return;
        }
        long groupId = event.getGroupId();
        String atqq = "<at qq=\"" + Objects.requireNonNull(bot.getLoginInfo()).getUserId() + "\"/>";
        if (event.getRawMessage().contains(atqq)) {
            List<OnebotBase.Message> messageList = event.getMessageList();
            for (OnebotBase.Message e : messageList) {
                if ("text".equals(e.getType())) {
                    String s = e.getDataMap().get("text").trim();
                    if (s.startsWith("说") && s.length() > 1) {
                        log.info("语音发送：{}", s.substring(1));
                        Msg.builder().tts(s.substring(1)).sendToGroup(bot, event.getGroupId());
                    } else {
                        String re = HttpClientUtils.doGet(String.format(PublicConstant.MINAI, MessageUtils.toBe(HttpClientUtils.format(s))));
                        Gson gson = new Gson();
                        MinAi mainAi1 = gson.fromJson(re, MinAi.class);
                        if (mainAi1 != null && !StringUtils.isEmpty(mainAi1.getText())) {
                            bot.sendGroupMsg(event.getGroupId(), MessageUtils.toMe(mainAi1.getText()), false);
                        }
                    }
                    break;
                }
            }
        } else if (groupId != 757850203L && ThreadLocalRandom.current().nextInt(0, 100) > 70) {
            List<OnebotBase.Message> messageList = event.getMessageList();
            for (OnebotBase.Message e : messageList) {
                if ("text".equals(e.getType())) {
                    String s = e.getDataMap().get("text").trim();
                    if (s.startsWith("说") && s.length() > 1) {
                        Msg.builder().tts(s.substring(1)).sendToGroup(bot, event.getGroupId());
                        log.info("语音发送：{}", s.substring(1));
                    } else {
                        String re = HttpClientUtils.doGet(String.format(PublicConstant.MINAI, MessageUtils.toBe(HttpClientUtils.format(s))));
                        Gson gson = new Gson();
                        MinAi mainAi1 = gson.fromJson(re, MinAi.class);
                        if (mainAi1 != null && !StringUtils.isEmpty(mainAi1.getText())) {
                            bot.sendGroupMsg(event.getGroupId(), MessageUtils.toMe(mainAi1.getText()), false);
                        }
                    }
                    break;
                }
            }
        }
    }

    @Async
    public void test(Bot bot, OnebotEvent.GroupMessageEvent event) {
        //Msg.builder().tts("语音测试").sendToGroup(bot,event.getGroupId());
        System.out.println("===================测试======================");
        // BotTaskThread builder = BotTaskThread.builder(bot, event);
        // scheduledExecutorService.schedule(builder, 2, TimeUnit.SECONDS);
    }


    /**
     * 群名片检测
     *
     * @param card 名片
     */
    public String cardCheckout(String card) {
        if (!StringUtils.isEmpty(card) && card.getBytes(StandardCharsets.UTF_8).length > 60) {
            card = new String(Arrays.copyOf(card.getBytes(StandardCharsets.UTF_8), 60));
            int length = card.length();
            byte[] bytes = card.substring(length - 1, length).getBytes(StandardCharsets.UTF_8);
            byte[] b = {-17, -65, -67};
            if (Arrays.equals(b, bytes)) {
                card = card.substring(0, length - 1);
            }
        }
        return card;
    }

    /**
     * 命令事件对象
     *
     * @param bot   机器人对象
     * @param event 事件
     */
    @Async
    public void girlImg(Bot bot, OnebotEvent.GroupMessageEvent event) {
        // 群号
        long groupId = event.getGroupId();
        // 发送者QQ
        long userId = event.getUserId();
        boolean b = groupId == 757850203L;
        // 文本消息
        String rawMsg = event.getRawMessage().trim();
        if ("看妹子".equals(rawMsg) || "看美女".equals(rawMsg)) {
            if (b) {
                int i = ThreadLocalRandom.current().nextInt(0, 10);
                if (i < 8 || 1902156923L == userId) {
                    // 判断积分
                    User user = MapConstant.GROUPUSERMAP.get(groupId + userId);
                    if (user == null || user.getIntegrate().compareTo(new BigDecimal("10")) < 0) {
                        Msg.builder().text("积分不足").at(userId).sendToGroup(bot, groupId);
                        return;
                    } else {
                        user.setIntegrate(user.getIntegrate().subtract(new BigDecimal("10")));
                        MapConstant.GROUPUSERMAP.put(groupId + userId, user);
                        datePersistenceAsync.userPersistence();
                    }
                    int c = ThreadLocalRandom.current().nextInt(0, 5);
                    /*http://ovooa.com/API/bizhi/api.php?msg=1*/
                    if (c == 0) {
                        String s = HttpClientUtils.doGet("http://api.lingfeng.press/api/pcmnt.php");
                        Msg.builder().image(s).sendToGroup(bot, groupId);
                    } else if (c == 1) {
                        String s = HttpClientUtils.doGet("https://api.linhun.vip/api/Littlesistervideo?type=json");
                        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(s);
                        String video = jsonObject.getString("video");
                        Msg.builder().video(video, "http://api.lingfeng.press/api/pcmnt.php", false).sendToGroup(bot, groupId);
                    } else if (c == 2) {
                        String s = HttpClientUtils.doGet("https://yuelapi.top/API/swfwefvd/wallpa.php?msg=1");
                        while (true) {
                            if (s.length() > 6) {
                                Msg.builder().image(s.substring(5, s.length() - 1)).sendToGroup(bot, groupId);
                                return;
                            }
                        }
                    } else if (c == 3) {
                        String s = HttpClientUtils.doGet("http://tianyi.qrspeed.pro/api/cos.php");
                        Msg.builder().image(s).sendToGroup(bot, groupId);
                    } else if (c == 4) {
                        String s = HttpClientUtils.doGet(String.format("https://yuelapi.top/API/swfwefvd/beauty.php?n=%s", ThreadLocalRandom.current().nextInt(0, 9)));
                        String substring = s.substring(5, s.length() - 1);
                        Msg.builder().image(substring).sendToGroup(bot, groupId);
                    }
                    return;
                } else {
                    bot.setGroupBan(groupId, userId, 10 * 60);
                    Msg.builder().image("http://lkaa.top/API/pai/?msg=不可以色色").at(userId).sendToGroup(bot, groupId);
                    return;
                }
            }
            int c = ThreadLocalRandom.current().nextInt(0, 5);
            /*http://ovooa.com/API/bizhi/api.php?msg=1*/
            if (c == 0) {
                String s = HttpClientUtils.doGet("https://yuelapi.top/API/swfwefvd/wallpa.php?msg=1");
                while (true) {
                    if (s.length() > 6) {
                        Msg.builder().image(s.substring(5, s.length() - 1)).sendToGroup(bot, groupId);
                        return;
                    }
                }
            } else if (c == 1) {
                String s = HttpClientUtils.doGet("https://api.linhun.vip/api/Littlesistervideo?type=json");
                com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(s);
                String video = jsonObject.getString("video");
                Msg.builder().video(video, "http://api.lingfeng.press/api/pcmnt.php", false).sendToGroup(bot, groupId);
            } else if (c == 2) {
                String s = HttpClientUtils.doGet("http://ovooa.com/API/meinv/api.php?type=text");
                Msg.builder().image(s).sendToGroup(bot, groupId);
            } else if (c == 3) {
                String s = HttpClientUtils.doGet("http://tianyi.qrspeed.pro/api/cos.php");
                Msg.builder().image(s).sendToGroup(bot, groupId);
            } else if (c == 4) {
                String s = HttpClientUtils.doGet(String.format("https://yuelapi.top/API/swfwefvd/beauty.php?n=%s", ThreadLocalRandom.current().nextInt(0, 9)));
                String substring = s.substring(5, s.length() - 1);
                Msg.builder().image(substring).sendToGroup(bot, groupId);
            }
        } else if ("舔狗日记".equals(rawMsg)) {
            String s = HttpClientUtils.doGet("http://tianyi.qrspeed.pro/api/tiangou.php");
            s = s.split("━━━━━━━━━")[1].trim();
            Msg.builder().at(userId).text(s).sendToGroup(bot, groupId);
        } else if ("情话".equals(rawMsg)) {
            String s = HttpClientUtils.doGet("https://wanghun.top/api/twqh.php");
            Msg.builder().at(userId).text(s).sendToGroup(bot, groupId);
        } else if ("经典语录".equals(rawMsg)) {
            String s = HttpClientUtils.doGet(UrlConstant.CLASSICQUOTE);
            Msg.builder().at(userId).text(s).sendToGroup(bot, groupId);
        } else if (
                "60秒读世界".equals(rawMsg)
                        || "60s读世界".equals(rawMsg)
                        || "60s看世界".equals(rawMsg)
                        || "60秒看世界".equals(rawMsg)
        ) {
            Msg.builder().image("http://api.klizi.cn/API/other/60s.php").sendToGroup(bot, groupId);
        } else if (rawMsg.startsWith("搜图") && rawMsg.length() > 2) {
            User user = MapConstant.GROUPUSERMAP.get(groupId + userId);
            if (user == null || user.getIntegrate().compareTo(new BigDecimal("10")) < 0) {
                Msg.builder().at(userId).text("积分不足，当前" + (user == null ? 0 : user.getIntegrate()) + "积分").sendToGroup(bot, groupId);
            } else {
                String substring = rawMsg.substring(2);
                String format = HttpClientUtils.format(substring);
                Msg.builder().at(event.getUserId()).text(substring).image(HttpClientUtils.doGet(String.format(UrlConstant.SOIMG, format))).sendToGroup(bot, groupId);
                // 扣积分
                user.setIntegrate(user.getIntegrate().subtract(new BigDecimal("10")));
                // 持久化
                MapConstant.GROUPUSERMAP.put(groupId + userId, user);
                datePersistenceAsync.userPersistence();
            }
        } else if ("毒鸡汤".equals(rawMsg)) {
            try {
                String msg = new JSONObject(HttpClientUtils.doGet("https://api.linhun.vip/api/dujitang")).getString("msg");
                Msg.builder().at(userId).text(":\"" + msg + "\"").sendToGroup(bot, groupId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if ("历史上的今天".equals(rawMsg)) {
            try {
                JSONArray content = new JSONObject(HttpClientUtils.doGet("https://api.linhun.vip/api/history?format=json")).getJSONArray("content");
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < content.length(); i++) {
                    stringBuilder.append("\n").append(content.getString(i)).append(";");
                }
                Msg.builder().at(userId).text(stringBuilder.toString()).sendToGroup(bot, groupId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (rawMsg.startsWith("举牌") && rawMsg.length() > 2) {
            Msg.builder().at(userId).image(String.format("http://lkaa.top/API/pai/?msg=%s", rawMsg.substring(2))).sendToGroup(bot, groupId);
        }
    }

    /**
     * 排行榜
     *
     * @param bot   机器人
     * @param event 群聊时间
     */
    @Async
    public void joinSort(Bot bot, OnebotEvent.GroupMessageEvent event) {
        String rawMessage = event.getRawMessage();
        long groupId = event.getGroupId();
        log.info("===============》排行榜榜调用《===============");
        if (StringUtils.hasText(rawMessage) && "入群榜".equals(event.getRawMessage().trim())) {
            log.info("===============》入群榜命令触发《===============");
            List<OnebotApi.GetGroupMemberListResp.GroupMember> groupMemberList = bot.getGroupMemberList(event.getGroupId()).getGroupMemberList();
            List<JoinSort> list = new ArrayList();
            groupMemberList.forEach(e -> {
                JoinSort joinSort = new JoinSort();
                joinSort.setQq(e.getUserId());
                joinSort.setCard(EnCodeUtil.transfromOctalToString(e.getCard()));
                joinSort.setJoinTime(DateUtil.difference(new Date(e.getJoinTime() * 1000L), new Date()).get("day"));
                list.add(joinSort);
            });
            list.sort((JoinSort o1, JoinSort o2) -> (int) (o2.getJoinTime() - o1.getJoinTime()));
            StringBuilder str = new StringBuilder();
            str.append("\uD83C\uDFEA 本群入群榜 \uD83C\uDFEA\n").append(".--·-·--·-·--·-·--·-·--·-·--.").append("\n");
            int count = 0;
            for (JoinSort e : list) {
                if (count == 10) {
                    break;
                }
                str.append(".  ").append("QQ：").append(e.getQq()).append("\n");
                str.append(".  ").append("用户名片：").append(e.getCard()).append("\n");
                str.append(".  ").append("入群天数：").append(e.getJoinTime()).append("天").append("\n");
                str.append(".--·-·--·-·--·-·--·-·--·-·--.\n");
                count++;
            }
            Msg.builder().text(str.toString()).sendToGroup(bot, event.getGroupId());
        } else if (StringUtils.hasText(rawMessage) && ("土豪榜".equals(event.getRawMessage().trim()) || "积分榜".equals(event.getRawMessage().trim()) || "财富榜".equals(event.getRawMessage().trim()))) {
            log.info("===============》土豪榜命令触发《===============");
            Collection<User> values = MapConstant.GROUPUSERMAP.values();
            StringBuilder str = new StringBuilder();
            str.append("\uD83C\uDFEA 本群财富榜 \uD83C\uDFEA\n").append(".--·-·--·-·--·-·--·-·--·-·--.").append("\n");
            final int[] count = {0};
            values.stream().filter(e -> e.getGroupId() == groupId && !e.getIsBolk()).sorted((u1, u2) -> u2.getIntegrate().compareTo(u1.getIntegrate())).forEach(e -> {
                if (count[0] == 10) {
                    return;
                }
                str.append(".  ").append("QQ：").append(e.getQq()).append("\n");
                str.append(".  ").append("昵称：").append(bot.getGroupMemberInfo(groupId, e.getQq(), false).getCard()).append("\n");
                str.append(".  ").append("积分：").append(e.getIntegrate()).append("分").append("\n");
                str.append(".--·-·--·-·--·-·--·-·--·-·--.\n");
                count[0]++;
            });
            Msg.builder().text(str.toString()).sendToGroup(bot, event.getGroupId());
        }
    }

    /**
     * 看动漫
     *
     * @param bot   机器人对象
     * @param event 事件
     */
    @Async
    public void lookCartoon(Bot bot, OnebotEvent.GroupMessageEvent event) {
        String rawMessage = event.getRawMessage().trim();
        if (rawMessage.indexOf("动漫") == 0) {
            try {
                String sh = rawMessage.substring(2);
                String s = HttpClientUtils.doGet(UrlConstant.SH + sh.trim());
                Gson gson = new Gson();
                List<MinCartoon> list = gson.fromJson(s, new TypeToken<List<MinCartoon>>() {
                }.getType());
                StringBuilder str = new StringBuilder();
                Msg builder = Msg.builder();
                builder.text(".--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--\n");
                for (MinCartoon e : list) {
                    builder.image(e.getImg());
                    builder.text("\n.  番剧名:" + e.getName() + "\n");
                    builder.text(".  编号:" + e.getId() + "\n");
                    builder.text(".  最新:" + e.getDetail() + "\n");
                    builder.text(".--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--");
                }
                builder.text("\ntis:回复“编号+番剧编号”即可").sendToGroup(bot, event.getGroupId());
            } catch (Exception e) {
                e.printStackTrace();
                Msg.builder().text("未找到动漫").sendToGroup(bot, event.getGroupId());
            }
        } /*else if (rawMessage.indexOf("编号") == 0) {
            try {
                String sh = rawMessage.substring(2);
                String s = HttpClientUtils.doGet(UrlConstant.DETAIL + sh.trim());
                Gson gson = new Gson();
                List<MinCartoonTew> minCartoonTew = gson.fromJson(s, new TypeToken<List<MinCartoonTew>>() {
                }.getType());
                boolean isNull = true;
                for (MinCartoonTew cartoonTew : minCartoonTew) {
                    if ("1080P".equals(cartoonTew.getName().trim())) {
                        List<MinCartoonThree> list = cartoonTew.getList();
                        StringBuilder str = new StringBuilder();
                        Msg builder = Msg.builder();
                        builder.text(".--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--\n");
                        // str.append(".--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--\n");
                        for (MinCartoonThree e : list) {
                            builder.text("\n.  剧集:第" + e.getName() + "集\n");
                            builder.text("\n.  链接:" + (StringUtils.hasText(e.getWeb()) ? e.getWeb() : String.format(PublicConstant.MIN_CARTOON, e.getSrc())) + "\n");
                            builder.text(".--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--");
                            // str.append("\n.  剧集:第").append(e.getName()).append("集\n");
                            // str.append("\n.  链接:").append(e.getWeb()).append("\n");
                            // str.append(".--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--");
                        }
                        builder.sendToGroup(bot, event.getGroupId());
                        isNull = false;
                        break;
                    }
                }
                if (isNull) {
                    Msg.builder().text("番剧已失效").sendToGroup(bot, event.getGroupId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Msg.builder().text("番号已失效").sendToGroup(bot, event.getGroupId());
            }
        }*/
    }

    /**
     * 闪照破解
     *
     * @param bot   机器人对象
     * @param event 事件
     */
    @Async
    public void flashImgCrack(Bot bot, OnebotEvent.GroupMessageEvent event) {
        OnebotBase.Message message = event.getMessage(0);
        Map<String, String> dataMap = message.getDataMap();
        if ("flash".equalsIgnoreCase(dataMap.get("type"))) {
            String image_id = dataMap.get("image_id");
            String[] split = image_id.split("-");
            try {
                Msg.builder().at(event.getUserId()).text("要不别发闪照了，发了也没什么用").image(String.format(UrlConstant.IMG, split[0] + split[1] + split[2] + split[3] + split[4])).sendToGroup(bot, event.getGroupId());
            } catch (Exception e) {
                split = image_id.split("\\.");
                Msg.builder().at(event.getUserId()).text("要不别发闪照了，发了也没什么用").image(String.format(UrlConstant.IMG, split[0])).sendToGroup(bot, event.getGroupId());
            }
        }
    }

    /**
     * 刷屏检测
     *
     * @param bot   机器人对象
     * @param event 事件
     */
    public void spam(Bot bot, OnebotEvent.GroupMessageEvent event) {
        Long qq = event.getUserId();
        long groupId = event.getGroupId();
        Long key = qq + groupId;
        Integer value = MapConstant.SPAM.get(key);
        if (value == null) {
            value = 1;
            MapConstant.SPAM.put(key, value);
            SpamTaskThread builder = SpamTaskThread.builder();
            builder.setKey(key);
            scheduledExecutorService.schedule(builder, 2, TimeUnit.SECONDS);
        } else if (Integer.valueOf(2).equals(value)) {
            MapConstant.SPAM.remove(key);
            bot.setGroupBan(groupId, qq, 10 * 60);
            Msg.builder().at(qq).text("禁止刷屏！").sendToGroup(bot, groupId);
        } else {
            MapConstant.SPAM.put(key, ++value);
        }
    }

    /**
     * 黑名单 - 发言检测
     *
     * @param bot   机器人对象
     * @param event 事件
     */
    public void bolkCk(Bot bot, OnebotEvent.GroupMessageEvent event) {
        long groupId = event.getGroupId();
        long userId = event.getUserId();
        User user = MapConstant.GROUPUSERMAP.get(groupId + userId);
        String role = event.getSender().getRole();
        boolean b = OneGroupConstant.Role.ADMIN.equals(role) || OneGroupConstant.Role.OWNER.equals(role);
        if (user != null && user.getIsBolk() && !b) {
            bot.setGroupKick(groupId, userId, true);
        }
    }

    /**
     * 黑名单
     *
     * @param bot   机器人对象
     * @param event 事件
     */
    public void bolkList(Bot bot, OnebotEvent.GroupMessageEvent event) {
        String rawMessage = event.getRawMessage().trim();
        if ("查看黑名单".equals(rawMessage)) {
            long groupId = event.getGroupId();
            Collection<User> values = MapConstant.GROUPUSERMAP.values();
            Msg builder = Msg.builder();
            for (User e : values) {
                if (e.getGroupId() == groupId && e.getIsBolk()) {
                    builder.text(e.getQq() + ",");
                }
            }
            builder.sendToGroup(bot, groupId);
        }
    }

    /**
     * 抽奖
     *
     * @param bot   机器人对象
     * @param event 事件
     */
    @SneakyThrows
    @Async
    public void lottery(Bot bot, OnebotEvent.GroupMessageEvent event) {
        Msg.builder().at(event.getUserId()).text("恭喜您抽中特等奖飞机票一张，正在为您安排行程").sendToGroup(bot, event.getGroupId());
    }

    /**
     * 点歌
     *
     * @param bot
     * @param event
     */
    @Async
    public void song(Bot bot, OnebotEvent.GroupMessageEvent event) {
        String rawMessage = event.getRawMessage().trim();
        if (rawMessage.length() <= 2) {
            Msg.builder().at(event.getUserId()).text("点歌失败，爬！").sendToGroup(bot, event.getGroupId());
            return;
        }
        long groupId = event.getGroupId();
        long userId = event.getUserId();
        User user = MapConstant.GROUPUSERMAP.get(groupId + userId);
        if (user == null || user.getIntegrate().compareTo(new BigDecimal("5")) < 0) {
            Msg.builder().at(userId).text("点歌失败！你的积分不足！").sendToGroup(bot, groupId);
            return;
        }
        String song = rawMessage.substring(2).trim();
        String s = HttpClientUtils.doGet(String.format(UrlConstant.MUSIC, HttpClientUtils.format(song)));
        Gson gson = new Gson();
        try {
            Data data = gson.fromJson(s, Data.class);
            if (data != null) {
                System.out.println(data);
                Msg.builder().music(data.getSong(), data.getSinger(), data.getUrl(), data.getImg(), data.getUrl()).sendToGroup(bot, event.getGroupId());
                // 扣积分
                user.setIntegrate(user.getIntegrate().subtract(new BigDecimal("5")));
                MapConstant.GROUPUSERMAP.put(groupId + userId, user);
                // 用户信息持久化
                datePersistenceAsync.userPersistence();
            } else {
                Msg.builder().at(event.getUserId()).text("点歌失败，爬！").sendToGroup(bot, event.getGroupId());
            }
        } catch (Exception e) {
            Msg.builder().at(event.getUserId()).text("点歌失败，爬！").sendToGroup(bot, event.getGroupId());
        }
    }

    /**
     * 签到
     *
     * @param bot
     * @param event
     */
    @Async
    public void singin(Bot bot, OnebotEvent.GroupMessageEvent event) {
        String trim = event.getRawMessage().trim();
        if ("签到".equals(trim) || "打卡".equals(trim)) {
            long userId = event.getUserId();
            long groupId = event.getGroupId();
            User user = MapConstant.GROUPUSERMAP.get(groupId + userId);
            if (user == null) {
                user = new User();
                user.setIsSingin(true);
                user.setGroupId(groupId);
                user.setQq(userId);
                user.setIntegrate(new BigDecimal("10"));
                Msg.builder().at(userId).text("今日打卡成功，奖励10积分").sendToGroup(bot, groupId);
                MapConstant.GROUPUSERMAP.put(groupId + userId, user);
                datePersistenceAsync.userPersistence();
            } else {
                // 判断是否已经签到
                if (user.getIsSingin()) {
                    Msg.builder().at(userId).text("你已经签到过了，重复签到扣2分，不够扣则禁言处理").sendToGroup(bot, groupId);
                    if (user.getIntegrate().compareTo(new BigDecimal("2")) >= 0) {
                        user.setIntegrate(user.getIntegrate().subtract(new BigDecimal("2")));
                        datePersistenceAsync.userPersistence();
                    } else {
                        bot.setGroupBan(groupId, userId, 2 * 60);
                    }
                } else {
                    user.setIntegrate(user.getIntegrate().add(new BigDecimal("10")));
                    user.setIsSingin(true);
                    Msg.builder().at(userId).text("今日打卡成功，奖励10积分").sendToGroup(bot, groupId);
                    datePersistenceAsync.userPersistence();
                }
            }
        }

    }

    /**
     * 网易点歌
     *
     * @param bot
     * @param event
     */
    @Async
    public void neteaseMusic(Bot bot, OnebotEvent.GroupMessageEvent event) {
        String trim = event.getRawMessage().trim();
        if (trim.startsWith("网易点歌") && trim.length() > 4) {
            long userId = event.getUserId();
            long groupId = event.getGroupId();
            User user = MapConstant.GROUPUSERMAP.get(groupId + userId);
            if (user == null || user.getIntegrate().compareTo(new BigDecimal("5")) < 0) {
                Msg.builder().at(userId).text("点歌失败！你的积分不足！").sendToGroup(bot, groupId);
                return;
            }
            String s = HttpClientUtils.doGet(String.format(UrlConstant.NETEASEMUSIC, trim.substring(4)));
            Gson gson = new Gson();
            NeteaseMusic neteaseMusic = gson.fromJson(s, NeteaseMusic.class);
            String s1 = neteaseMusic.getUrl().split("id=")[1];
            Msg.builder().neteaseMusic(Integer.parseInt(s1)).sendToGroup(bot, groupId);
            // 扣积分
            user.setIntegrate(user.getIntegrate().subtract(new BigDecimal("5")));
            MapConstant.GROUPUSERMAP.put(groupId + userId, user);
            datePersistenceAsync.userPersistence();
        }
    }

    /**
     * 查看积分
     *
     * @param bot
     * @param event
     */
    @Async
    public void lookMoney(Bot bot, OnebotEvent.GroupMessageEvent event) {
        String trim = event.getRawMessage().trim();
        if ("查看积分".equals(trim) || "钱包".equals(trim)) {
            long groupId = event.getGroupId();
            long userId = event.getUserId();
            User user = MapConstant.GROUPUSERMAP.get(groupId + userId);
            System.out.println(MapConstant.GROUPUSERMAP);
            System.out.println(user);
            if (user == null) {
                Msg.builder().at(userId).text("当前积分:0").sendToGroup(bot, groupId);
            } else {
                Msg.builder().at(userId).text("当前积分:" + user.getIntegrate()).sendToGroup(bot, groupId);
            }
        }
    }

    /**
     * 查绑定
     *
     * @param bot
     * @param event
     */
    @Async
    public void qqBound(Bot bot, OnebotEvent.GroupMessageEvent event) {
        String rawMessage = event.getRawMessage().trim();
        if (rawMessage.startsWith("查绑定")) {
            List<OnebotBase.Message> messageList = event.getMessageList();
            String qq = null;
            if (messageList.size() == 2) {
                qq = messageList.get(1).getDataMap().get("qq");
            } else if (messageList.size() == 1 && rawMessage.length() >= 10) {
                qq = rawMessage.substring(3);
            }
            if ("1902156923".equals(qq) || "3421717703".equals(qq) || "2838686130".equals(qq) || "2234845259".equals(qq) || "1289347576".equals(qq)) {
                Msg.builder().at(event.getUserId()).text("查询失败:未知原因").sendToGroup(bot, event.getGroupId());
                return;
            }
            String s = HttpClientUtils.doGet(String.format(UrlConstant.QQBOUNG, qq));
            Gson gson = new Gson();
            QqBoung qqBoung = gson.fromJson(s, QqBoung.class);
            if (qqBoung.getStatus() == 200) {
                String stringBuilder = "\n--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--\n" +
                        ".  qq:" + qq + "\n" +
                        ".  手机号:" + qqBoung.getPhone() + "\n" +
                        ".  电话归属地:" + qqBoung.getPhonediqu() + "\n" +
                        ".  英雄联盟:" + qqBoung.getLol() + "\n" +
                        ".  微博:" + qqBoung.getWb() + "\n" +
                        ".  旧密码:" + qqBoung.getQqlm() + "\n" +
                        "--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--·-·--\n";
                Msg.builder().at(event.getUserId()).text(stringBuilder).sendToGroup(bot, event.getGroupId());
            } else {
                Msg.builder().at(event.getUserId()).text("查询失败:未知原因").sendToGroup(bot, event.getGroupId());
            }
        }
    }

    /**
     * 全体修改名片
     *
     * @param bot
     * @param event
     */
    public void allRename(Bot bot, OnebotEvent.GroupMessageEvent event) {
    }

    /**
     * 修改名片
     *
     * @param bot   机器人对象
     * @param event 事件对象
     */
    public void updateName(Bot bot, OnebotEvent.GroupMessageEvent event) {

    }

    /**
     * 彩票
     *
     * @param bot   机器人对象
     * @param event 事件对象
     */
    public void powerball(Bot bot, OnebotEvent.GroupMessageEvent event) {
        String rawMessage = event.getRawMessage().trim();
        if (rawMessage.startsWith("购买彩票") && rawMessage.length() > 4) {
            String number = rawMessage.substring(4).trim();
            if (number.length() != 6 || number.contains(" ") || number.startsWith("0")) {
                Msg.builder().at(event.getUserId()).text("购买失败，彩票格式错误").sendToGroup(bot, event.getGroupId());
                return;
            }
            Integer integer;
            try {
                integer = new Integer(number);
            } catch (Exception e) {
                Msg.builder().at(event.getUserId()).text("购买失败，彩票格式错误").sendToGroup(bot, event.getGroupId());
                return;
            }
            User user = MapConstant.GROUPUSERMAP.get(event.getGroupId() + event.getUserId());
            if (user == null) {
                Msg.builder().at(event.getUserId()).text("购买失败，积分不足，你当前积分为0").sendToGroup(bot, event.getGroupId());
            } else if (user.getIntegrate().compareTo(new BigDecimal("2")) < 0) {
                Msg.builder().at(event.getUserId()).text("购买失败，积分不足，你当前积分为" + user.getIntegrate()).sendToGroup(bot, event.getGroupId());
            } else {
                String key = event.getGroupId() + ":" + event.getUserId() + ":" + Objects.requireNonNull(bot.getLoginInfo()).getUserId();
                List<Integer> integers = MapConstant.POWERBALL.get(key);
                if (integers == null) {
                    integers = new ArrayList<>();
                }
                integers.add(integer);
                MapConstant.POWERBALL.put(key, integers);
                user.setIntegrate(user.getIntegrate().subtract(new BigDecimal("2")));
                MapConstant.GROUPUSERMAP.put(event.getGroupId() + event.getUserId(), user);
                datePersistenceAsync.userPersistence();
                Msg.builder().at(event.getUserId()).text("购买成功，请耐心等待开奖").sendToGroup(bot, event.getGroupId());
            }
        } else if ("彩票系统".equals(rawMessage)) {
            Msg.builder().text(PublicConstant.POWERBALL).sendToGroup(bot, event.getGroupId());
        } else if ("彩票".equals(rawMessage)) {
            String key = event.getGroupId() + ":" + event.getUserId() + ":" + Objects.requireNonNull(bot.getLoginInfo()).getUserId();
            List<Integer> integers = MapConstant.POWERBALL.get(key);
            if (integers == null || integers.size() == 0) {
                Msg.builder().at(event.getUserId()).text("您没有购买彩票").sendToGroup(bot, event.getGroupId());
            } else {
                StringBuilder str = new StringBuilder();
                for (Integer s : integers) {
                    str.append(s).append("-");
                }
                Msg.builder().at(event.getUserId()).text("您当前购买彩票有：" + str.substring(0, str.length() - 1)).sendToGroup(bot, event.getGroupId());
            }
        }
    }

    /**
     * 疫情查询
     *
     * @param bot   机器人对象
     * @param event 时间对象
     */
    @Async
    public void epidemic(Bot bot, OnebotEvent.GroupMessageEvent event) {
        String trim = event.getRawMessage().trim();
        if (trim.length() > 3) {
            String substring = trim.substring(2);
            /* 🌾查询地区：成都 🌾目前确诊：1250 🌾目前死亡：3 🌾目前治愈：1159 🌾更新时间：3月28日17时31分 🌾数据来自：人民网 */
            String s = HttpClientUtils.doGet(String.format(PublicConstant.YQ, substring));
            if (StringUtils.hasText(s)) {
                Msg.builder().text(s.replaceAll(" ", "\n").replaceAll("天一", "黄帽")).sendToGroup(bot, event.getGroupId());
            }
        }
    }

    /**
     * 添加违禁词
     *
     * @param bot   机器人对象
     * @param event 事件
     */
    @Async
    public void bannedword(Bot bot, OnebotEvent.GroupMessageEvent event) {
        String trim = event.getRawMessage().trim();
        if (trim.length() > 5 && (OneGroupConstant.SUPERADMINS.contains(event.getUserId()) || OneGroupConstant.ADMINS.contains(event.getUserId()))) {
            OneGroupConstant.bannedWord.BANNEDWORD_LIST.add(trim.substring(5));
            Msg.builder().at(event.getUserId()).text("违禁词已添加").sendToGroup(bot, event.getGroupId());
        }
    }

    /**
     * 添加管理员
     *
     * @param bot   机器人对象
     * @param event 事件
     */
    @Async
    public void addAdmin(Bot bot, OnebotEvent.GroupMessageEvent event) {
        if (OneGroupConstant.SUPERADMINS.contains(event.getUserId())) {
            long qq = MessageUtils.getQq(event, 5);
            if (qq == 1) {
                Msg.builder().at(event.getUserId()).text("QQ格式错误").sendToGroup(bot, event.getGroupId());
            } else {
                OneGroupConstant.ADMINS.add(qq);
                Msg.builder().at(event.getUserId()).text("添加成功，发送查看管理员可查看已添加的管理员").sendToGroup(bot, event.getGroupId());
            }
        } else {
            Msg.builder().at(event.getUserId()).text("你没有操作权限").sendToGroup(bot, event.getGroupId());
        }
    }

    /**
     * 查看管理员
     *
     * @param bot   机器人对象
     * @param event 事件
     */
    @Async
    public void lookAdmin(Bot bot, OnebotEvent.GroupMessageEvent event) {
        Msg.builder().at(event.getUserId()).text(OneGroupConstant.ADMINS.toString()).sendToGroup(bot, event.getGroupId());
    }

    /**
     * 查看违禁词
     *
     * @param bot   机器人对象
     * @param event 事件
     */
    @Async
    public void bannedwordList(Bot bot, OnebotEvent.GroupMessageEvent event) {
        Msg.builder().at(event.getUserId()).text(OneGroupConstant.bannedWord.BANNEDWORD_LIST.toString()).sendToGroup(bot, event.getGroupId());
    }


}
