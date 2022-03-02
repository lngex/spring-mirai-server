package cn.ling.bot.basic.task;

import cn.ling.bot.basic.constant.MapConstant;

/**
 * 禁言时间删除
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class BannedTaskThread implements Runnable {
    /**
     * key groupId+::+QQ
     */
    private final String key;

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
        MapConstant.BANNED_MAP.remove(key);
    }

    private BannedTaskThread(String key){
        this.key = key;
    }

    public static BannedTaskThread builder(String key){
        return new BannedTaskThread(key);
    }
}
