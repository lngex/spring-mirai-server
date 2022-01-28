package net.lz1998.pbbot.basic.adapter;

import net.lz1998.pbbot.bot.Bot;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;

/**
 * 群适配器
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IGroupAdapter {
    /**
     * 指定路由的群
     * @param bot 机器人对象
     * @param event 发送者消息对象
     * @return 1执行下一插件，-1不执行下一插件
     */
    Integer groupRouter(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event);
}
