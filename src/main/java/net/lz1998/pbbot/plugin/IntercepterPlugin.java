package net.lz1998.pbbot.plugin;

import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class IntercepterPlugin extends BotPlugin {


    // private final long[] groups = {757850203L,1050122161L,793893098L};
    /**
     * 允许群聊的群
     */
    public static final List<Long> GROUPS = new ArrayList<>();
    static {
        GROUPS.add(757850203L);
        GROUPS.add(1050122161L);
        GROUPS.add(793893098L);
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
        if(GROUPS.contains(event.getGroupId())) {
            return 0;
        }
        return 1;
    }

    @Override
    public int onGroupAdminNotice(@NotNull Bot bot, @NotNull OnebotEvent.GroupAdminNoticeEvent event) {
        if(GROUPS.contains(event.getGroupId())) {
            return 0;
        }
        return 1;
    }

    @Override
    public int onGroupBanNotice(@NotNull Bot bot, @NotNull OnebotEvent.GroupBanNoticeEvent event) {
        if(GROUPS.contains(event.getGroupId())) {
            return 0;
        }
        return 1;
    }

    @Override
    public int onGroupDecreaseNotice(@NotNull Bot bot, @NotNull OnebotEvent.GroupDecreaseNoticeEvent event) {
        if(GROUPS.contains(event.getGroupId())) {
            return 0;
        }
        return 1;
    }

    @Override
    public int onGroupIncreaseNotice(@NotNull Bot bot, @NotNull OnebotEvent.GroupIncreaseNoticeEvent event) {
        if(GROUPS.contains(event.getGroupId())) {
            return 0;
        }
        return 1;
    }

    @Override
    public int onGroupRecallNotice(@NotNull Bot bot, @NotNull OnebotEvent.GroupRecallNoticeEvent event) {
        if(GROUPS.contains(event.getGroupId())) {
            return 0;
        }
        return 1;
    }

    @Override
    public int onGroupRequest(@NotNull Bot bot, @NotNull OnebotEvent.GroupRequestEvent event) {
        if(GROUPS.contains(event.getGroupId())) {
            return 0;
        }
        return 1;
    }

    @Override
    public int onGroupUploadNotice(@NotNull Bot bot, @NotNull OnebotEvent.GroupUploadNoticeEvent event) {
        if(GROUPS.contains(event.getGroupId())) {
            return 0;
        }
        return 1;
    }
}
