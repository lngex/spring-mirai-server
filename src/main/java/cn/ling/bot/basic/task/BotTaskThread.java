package cn.ling.bot.basic.task;

import lombok.Data;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;

/**
 * 机器人任务对象
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class BotTaskThread implements Runnable {
    /**
     * 机器人
     */
    private Bot bot;
    private OnebotEvent.GroupMessageEvent event;

    private BotTaskThread(Bot bot, OnebotEvent.GroupMessageEvent event) {
        this.bot = bot;
        this.event = event;
    }

    public static BotTaskThread builder(Bot bot, OnebotEvent.GroupMessageEvent event) {
        return new BotTaskThread(bot, event);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        Msg.builder().text(event.getRawMessage()).sendToGroup(bot, event.getGroupId());
    }
}
