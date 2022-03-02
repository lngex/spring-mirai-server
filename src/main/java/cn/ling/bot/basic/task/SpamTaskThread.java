package cn.ling.bot.basic.task;

import cn.ling.bot.basic.constant.MapConstant;
import lombok.Data;

/**
 * 刷屏禁言
 * 时间1分钟
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class SpamTaskThread implements Runnable {
    /**
     * 群号+qq
     */
    private Long key;

    public static SpamTaskThread builder() {
        return new SpamTaskThread();
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
     * @see Thread #run()
     */
    @Override
    public void run() {
        MapConstant.SPAM.remove(key);
    }
}
