package cn.ling.bot.plugin;

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

    private static final ArrayList<String> recallNotice = new ArrayList<>();


    static {
        URLS.add("http://wx4.sinaimg.cn/large/006APoFYly1g9cl9foknsj306306omx8.jpg");
        URLS.add("https://imgsa.baidu.com/forum/w%3D580/sign=b565f4e959e736d158138c00ab504ffc/b5da861001e939012c54370b76ec54e736d196b0.jpg");
        URLS.add("https://imgsa.baidu.com/forum/w%3D580/sign=689ca9caf01f4134e0370576151e95c1/3745f558ccbf6c814e8818c1b13eb13532fa40a7.jpg");
        URLS.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.adoutu.com%2Fpicture%2F1538469134324.jpg&refer=http%3A%2F%2Fimg.adoutu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660469111&t=07188a88f9bc5bc28e2d1f6c67523d1e");
        recallNotice.add("https://img1.baidu.com/it/u=3525343135,3005609710&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=375");
        recallNotice.add("http://ww3.sinaimg.cn/bmiddle/6af89bc8gw1f8tzig4q3xj205i04bt8k.jpg");
        recallNotice.add("https://img1.baidu.com/it/u=1217767715,1632402249&fm=253&fmt=auto&app=138&f=JPEG?w=200&h=200");
        recallNotice.add("https://img2.baidu.com/it/u=1123776011,2032142517&fm=253&fmt=auto&app=120&f=GIF?w=216&h=212");
        recallNotice.add("https://img.julinghu.com/2021/09/20210927024931509.jpg");
    }

    /**
     * 被at时调用
     *
     * @param bot   机器人对象
     * @param event 事件内容
     * @return 统一响应对象
     */
    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        if (!IntercepterPlugin.GROUPS.contains(event.getGroupId())) {
            return 1;
        }
        String str = "<at qq=\"" + Objects.requireNonNull(bot.getLoginInfo()).getUserId() + "\"/>";
        String rawMessage = event.getRawMessage().trim();
        if (!StringUtils.isEmpty(rawMessage) && rawMessage.equals(str)) {
            /* http://wx4.sinaimg.cn/large/006APoFYly1g9cl9foknsj306306omx8.jpg */
            Msg.builder()
                    .at(event.getUserId())
                    .image(URLS.get(ThreadLocalRandom.current().nextInt(0, URLS.size())))
                    .sendToGroup(bot, event.getGroupId());
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }

    /**
     * 撤回时调用
     *
     * @param bot   机器人对象
     * @param event 事件
     * @return 统一响应对象
     */
    @Override
    public int onGroupRecallNotice(Bot bot, OnebotEvent.GroupRecallNoticeEvent event) {
        if (!IntercepterPlugin.GROUPS.contains(event.getGroupId())) {
            return 1;
        }
        long operatorId = event.getOperatorId();
        if (operatorId == 1902156923L || operatorId == bot.getSelfId()) {
            return 0;
        }
        Msg.builder().at(event.getUserId())
                .image(recallNotice.get(ThreadLocalRandom.current().nextInt(0, recallNotice.size())))
                .sendToGroup(bot, event.getGroupId());
        return MESSAGE_BLOCK;

    }

    /**
     * 进群时调用
     *
     * @param bot   机器人对象
     * @param event 事件
     * @return 统一响应对象
     */
    @Override
    public int onGroupIncreaseNotice(@NotNull Bot bot, OnebotEvent.GroupIncreaseNoticeEvent event) {
        if (IntercepterPlugin.GROUPS.contains(event.getGroupId())) {
            Msg.builder().at(event.getUserId())
                    .image("http://ww1.sinaimg.cn/large/9150e4e5gw1f8ywdx9hang205p05xq4s.gif")
                    .sendToGroup(bot, event.getGroupId());
        }
        return 1;
    }
}
