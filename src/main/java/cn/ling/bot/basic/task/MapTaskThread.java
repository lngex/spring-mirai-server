package cn.ling.bot.basic.task;

import cn.ling.bot.basic.constant.MapConstant;
import cn.ling.bot.basic.domain.VoteOut;

/**
 * 任务线程
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class MapTaskThread implements Runnable {
    /**
     * 键
     */
    private String key;
    /**
     * 任务标识
     */
    private String uuid;

    private MapTaskThread(String key,String uuid) {
        this.key = key;
        this.uuid = uuid;
    }

    public static MapTaskThread builder(String key, String uuid) {
        return new MapTaskThread(key,uuid);
    }

    @Override
    public void run() {
        // 判断是否为正确的任务对象
        VoteOut voteOut = MapConstant.VOTE_OUT_MAP.get(key);
        if(voteOut != null && uuid.equals(voteOut.getUuid())){
            MapConstant.VOTE_OUT_MAP.remove(key);
        }
    }
}
