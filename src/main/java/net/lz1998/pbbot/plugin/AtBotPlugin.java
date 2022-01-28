package net.lz1998.pbbot.plugin;

import lombok.SneakyThrows;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 艾特机器人对象
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class AtBotPlugin extends BotPlugin {

    private static final ArrayList<String> URLS = new ArrayList<>();


    static {
        URLS.add("http://wx4.sinaimg.cn/large/006APoFYly1g9cl9foknsj306306omx8.jpg");
        URLS.add("https://imgsa.baidu.com/forum/w%3D580/sign=b565f4e959e736d158138c00ab504ffc/b5da861001e939012c54370b76ec54e736d196b0.jpg");
    }

    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        String str ="<at qq=\""+ Objects.requireNonNull(bot.getLoginInfo()).getUserId()+"\"/>";
        String rawMessage = event.getRawMessage();
        if(!StringUtils.isEmpty(rawMessage) && rawMessage.contains(str)){
            /* http://wx4.sinaimg.cn/large/006APoFYly1g9cl9foknsj306306omx8.jpg */
            Msg.builder()
                    .at(event.getUserId())
                    .image(URLS.get(ThreadLocalRandom.current().nextInt(0,URLS.size())))
                    .sendToGroup(bot,event.getGroupId());
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }

    /**
     * 撤回时调用
     * @param bot 机器人对象
     * @param event 事件
     * @return 统一响应对象
     */
    @Override
    public int onGroupRecallNotice(Bot bot, OnebotEvent.GroupRecallNoticeEvent event){
        long operatorId = event.getOperatorId();
        if(operatorId == 1902156923L || operatorId == bot.getSelfId()){
return 0;
        }
        Msg.builder().at(event.getUserId())
                .image("http://ww3.sinaimg.cn/bmiddle/6af89bc8gw1f8tzig4q3xj205i04bt8k.jpg")
                .sendToGroup(bot,event.getGroupId());
        return MESSAGE_BLOCK;
    }

    /**
     * 进群时调用
     * @param bot 机器人对象
     * @param event 事件
     * @return 统一响应对象
     */
    @Override
    public int onGroupIncreaseNotice(@NotNull Bot bot, OnebotEvent.GroupIncreaseNoticeEvent event){
        Msg.builder().at(event.getUserId())
                .image("http://ww1.sinaimg.cn/large/9150e4e5gw1f8ywdx9hang205p05xq4s.gif")
                .sendToGroup(bot,event.getGroupId());
        return MESSAGE_BLOCK;
    }
}
