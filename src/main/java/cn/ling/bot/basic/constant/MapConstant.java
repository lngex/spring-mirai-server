package cn.ling.bot.basic.constant;

import cn.ling.bot.basic.domain.User;
import cn.ling.bot.basic.domain.VoteOut;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Map常量
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MapConstant {
    /**
     * 记录投票踢人
     * 群id+::+QQ
     */
    ConcurrentHashMap<String, VoteOut> VOTE_OUT_MAP = new ConcurrentHashMap<>();
    /**
     * 违禁词处罚存储（保留7天）
     * 群id+::+QQ
     */
    ConcurrentHashMap<String, Integer> BANNED_MAP = new ConcurrentHashMap<>();
    /**
     * 刷屏
     * key 群号 + qq，value 发言次数
     */
    ConcurrentHashMap<Long, Integer> SPAM = new ConcurrentHashMap<>();
    /**
     * 群与群员MAp key 群号 + qq号
     * 内部Map keyqq，value用户
     */
    HashMap<Long, User> GROUPUSERMAP = new HashMap<>();
    /**
     * 群与群员MAp key 群号 + : + qq号 + ":" 机器人qq号
     * 内部List,彩票号码集合
     */
    HashMap<String, List<Integer>> POWERBALL = new HashMap<>();
}
