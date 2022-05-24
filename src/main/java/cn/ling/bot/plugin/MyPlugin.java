package cn.ling.bot.plugin;

import lombok.extern.slf4j.Slf4j;
import cn.ling.bot.async.DatePersistenceAsync;
import cn.ling.bot.async.GroupMesageAsync;
import cn.ling.bot.basic.GroupRouter;
import cn.ling.bot.basic.constant.MapConstant;
import cn.ling.bot.basic.constant.OneGroupConstant;
import cn.ling.bot.basic.constant.PublicConstant;
import cn.ling.bot.basic.domain.User;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotApi;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Component
public class MyPlugin extends BotPlugin {

    @Autowired
    private GroupRouter groupRouter;

    @Autowired
    private DatePersistenceAsync datePersistenceAsync;

    @Autowired
    private GroupMesageAsync groupMesageAsync;

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        // 这里展示了event消息链的用法. List里面可能是 at -> text -> image -> face -> text 形式, 根据元素类型组成 List。
        // List 每一个元素 有type(String)和data(Map<String, String>)，type 表示元素是什么, data 表示元素的具体数据，如at qq，image url，face id
        List<OnebotBase.Message> messageChain = event.getMessageList();
        if (messageChain.size() > 0) {
            OnebotBase.Message message = messageChain.get(0);
            if (message.getType().equals("text")) {
                String text = message.getDataMap().get("text");
                System.out.println(message.getDataMap());
                if ("hello".equals(text)) {
                    bot.sendPrivateMsg(event.getUserId(), "hi", false);
                }
            }
        }
        return MESSAGE_IGNORE;
    }

    /**
     * 收到群消息时调用
     *
     * @param bot   机器人对象
     * @param event 消息对象
     * @return 0下一插件，1终止
     */
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        if (!IntercepterPlugin.GROUPS.contains(event.getGroupId())) {
            return 1;
        }
        String rawMessage = event.getRawMessage().trim();
        String role = event.getSender().getRole();
        boolean b = (OneGroupConstant.Role.ADMIN.equals(role) || OneGroupConstant.Role.OWNER.equals(role));
        if ("功能".equals(rawMessage) || "菜单".equals(rawMessage)) {
            Msg.builder().text(PublicConstant.MENU).sendToGroup(bot, event.getGroupId());
            return 1;
        } else if ("群管系统".equals(rawMessage)) {
            Msg.builder().text(PublicConstant.GROUPMANIGER).sendToGroup(bot, event.getGroupId());
            return 1;
        } else if ("娱乐系统".equals(rawMessage)) {
            Msg.builder().text(PublicConstant.ENTERTAINMENT).sendToGroup(bot, event.getGroupId());
            return 1;
        } else if ("排行系统".equals(rawMessage)) {
            Msg.builder().text(PublicConstant.SORTSYSTEM).sendToGroup(bot, event.getGroupId());
            return 1;
        } else if ("其他".equals(rawMessage)) {
            Msg.builder().text(PublicConstant.OTHER).sendToGroup(bot, event.getGroupId());
            return 1;
        } else if ("日常".equals(rawMessage)) {
            Msg.builder().text(PublicConstant.EVERYDAY).sendToGroup(bot, event.getGroupId());
            return 1;
        } else if (rawMessage.startsWith("禁言") && b) {
            List<OnebotBase.Message> messageList = event.getMessageList();
            String qq = null;
            Integer time = 60;
            if (messageList.size() == 2) {
                qq = messageList.get(1).getDataMap().get("qq");
                time = time * 3;
            } else if (messageList.size() == 3) {
                qq = messageList.get(1).getDataMap().get("qq");
                String text = messageList.get(2).getDataMap().get("text");
                if (StringUtils.hasText(text)) {
                    String str;
                    if (text.endsWith("分钟")) {
                        str = text.substring(0, text.length() - 2).trim();
                    } else if (text.endsWith("小时")) {
                        time = time * 60;
                        str = text.substring(0, text.length() - 2).trim();
                    } else if (text.endsWith("天")) {
                        time = time * 60 * 24;
                        str = text.substring(0, text.length() - 1).trim();
                    } else {
                        str = "3";
                    }
                    int l = Integer.parseInt(str);
                    time = time * l;
                    if (time >= 259200) {
                        time = 2505600;
                    }
                }
            }
            bot.setGroupBan(event.getGroupId(), Long.parseLong(qq), time);
            Msg.builder().at(Long.parseLong(qq)).text("你已被管理员禁言，请注意发言");
        } else if (rawMessage.startsWith("解除禁言") && b) {
            List<OnebotBase.Message> messageList = event.getMessageList();
            if (messageList.size() == 2 || (messageList.size() == 3 && "at".equals(messageList.get(1).getType()))) {
                String qq = messageList.get(1).getDataMap().get("qq");
                bot.setGroupBan(event.getGroupId(), Long.parseLong(qq), 0);
            }
        } else if (rawMessage.startsWith("拉黑") && b) {
            List<OnebotBase.Message> messageList = event.getMessageList();
            String qq = null;
            if (messageList.size() == 2) {
                qq = messageList.get(1).getDataMap().get("qq");
            } else if (messageList.size() == 1 && rawMessage.length() >= 10) {
                qq = rawMessage.substring(2);
            }else if((messageList.size() == 3 && "at".equals(messageList.get(1).getType()))){
                qq = messageList.get(1).getDataMap().get("qq");
            }
            if (StringUtils.hasText(qq) && qq.length() >= 8) {
                try {
                    long l = Long.parseLong(qq);
                    long groupId = event.getGroupId();
                    long key = groupId + l;
                    // 加入黑名单
                    User user = MapConstant.GROUPUSERMAP.get(key);
                    if (user == null) {
                        user = new User();
                    }
                    user.setIsBolk(true);
                    user.setQq(l);
                    user.setGroupId(groupId);
                    MapConstant.GROUPUSERMAP.put(key, user);
                    Msg.builder().text("QQ:" + l + "已被拉黑，我在时将无法加入群聊").sendToGroup(bot, event.getGroupId());
                    datePersistenceAsync.userPersistence();
                } catch (Exception e) {
                    Msg.builder().text("命令对象" + qq + "无效").sendToGroup(bot, event.getGroupId());
                }
            }
            return 1;
        } else if (rawMessage.startsWith("解除拉黑") && b) {
            List<OnebotBase.Message> messageList = event.getMessageList();
            String qq = null;
            if (messageList.size() == 2 || (messageList.size() == 3 && "at".equals(messageList.get(1).getType()))) {
                qq = messageList.get(1).getDataMap().get("qq");
            } else if (messageList.size() == 1 && rawMessage.length() >= 12) {
                qq = rawMessage.substring(4);
            }
            if (StringUtils.hasText(qq) && qq.length() >= 8) {
                try {
                    long l = Long.parseLong(qq);
                    long groupId = event.getGroupId();
                    long key = groupId + l;
                    // 解除黑名单
                    User user = MapConstant.GROUPUSERMAP.get(key);
                    if (user == null) {
                        user = new User();
                    }
                    user.setIsBolk(false);
                    user.setQq(l);
                    user.setGroupId(groupId);
                    MapConstant.GROUPUSERMAP.put(key, user);
                    Msg.builder().text("QQ:" + l + "已被解除拉黑").sendToGroup(bot, event.getGroupId());
                    datePersistenceAsync.userPersistence();
                } catch (Exception e) {
                    Msg.builder().text("命令对象" + qq + "无效").sendToGroup(bot, event.getGroupId());
                }
            }
            return 1;
        } else if ("抽奖".equals(rawMessage)) {
            groupMesageAsync.lottery(bot, event);
        } else if (rawMessage.startsWith("点歌")) {
            groupMesageAsync.song(bot, event);
            return 1;
        } else if ("签到".equals(rawMessage) || "打卡".equals(rawMessage)) {
            groupMesageAsync.singin(bot, event);
            return 1;
        } else if ("查看积分".equals(rawMessage) || "钱包".equals(rawMessage)) {
            groupMesageAsync.lookMoney(bot, event);
            return 1;
        } else if (rawMessage.startsWith("修改名片")) {
            groupMesageAsync.updateName(bot, event);
            return 1;
        } else if (rawMessage.startsWith("疫情")) {
            groupMesageAsync.epidemic(bot, event);
            return 1;
        } else if (rawMessage.contains("我是说")
                || rawMessage.contains("我就说")
                || rawMessage.contains("原来")
                || rawMessage.contains("原本")
                || rawMessage.contains("因为")
                || rawMessage.contains("以前")
                || rawMessage.contains("之前")) {
            /* http://tva1.sinaimg.cn/large/006APoFYly8gw4ulbqwedj304g03amx0.jpg */
            Msg.builder().at(event.getUserId()).image("http://tva1.sinaimg.cn/large/006APoFYly8gw4ulbqwedj304g03amx0.jpg").sendToGroup(bot, event.getGroupId());
            return 1;
        } else if (rawMessage.startsWith("添加违禁词")) {
            groupMesageAsync.bannedword(bot, event);
            return 1;
        } else if (rawMessage.startsWith("添加管理员")) {
            groupMesageAsync.addAdmin(bot, event);
            return 1;
        } else if ("查看管理员".equals(rawMessage)) {
            groupMesageAsync.lookAdmin(bot, event);
            return 1;
        } else if ("违禁词列表".equals(rawMessage)) {
            groupMesageAsync.bannedwordList(bot, event);
            return 1;
        } else {
            groupRouter.router(bot, event);
        }
        return MESSAGE_IGNORE;
    }

    /**
     * 加群事件方法
     *
     * @param bot   机器人对象
     * @param event 事件
     * @return 0下一插件，1终止
     */
    @Override
    public int onGroupRequest(@NotNull Bot bot, @NotNull OnebotEvent.GroupRequestEvent event) {
        if (!IntercepterPlugin.GROUPS.contains(event.getGroupId())) {
            return 1;
        }
        long userId = event.getUserId();
        long groupId = event.getGroupId();
        OnebotApi.GetStrangerInfoResp strangerInfo = bot.getStrangerInfo(userId);
        int level = strangerInfo.getLevel();
        User user = MapConstant.GROUPUSERMAP.get(userId + groupId);
        if ((user == null || !user.getIsBolk()) && level > 14) {
            bot.setGroupAddRequest(event.getFlag(), event.getSubType(), true, "");
        } else {
            bot.setGroupAddRequest(event.getFlag(), event.getSubType(), false, "QQ等级未达到15级，拒绝加入");
        }
        return MESSAGE_IGNORE;
    }

    /**
     * 群管理员变动时调用此方法
     *
     * @param bot   机器人对象
     * @param event 事件内容
     * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
     */
    @Override
    public int onGroupAdminNotice(@NotNull Bot bot, @NotNull OnebotEvent.GroupAdminNoticeEvent event) {
        if (!IntercepterPlugin.GROUPS.contains(event.getGroupId())) {
            return 1;
        }
        // 设置管理员-set，取消管理员unset
        String subType = event.getSubType();
        return MESSAGE_IGNORE;
    }
}
