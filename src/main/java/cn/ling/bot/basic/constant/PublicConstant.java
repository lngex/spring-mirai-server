package cn.ling.bot.basic.constant;

/**
 * 公共的常量
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public interface PublicConstant {
    /**
     * 小爱机器人API
     */
    String MINAI = "http://ruohuan.xiaoapi.cn/API/other/xiaoai.php?msg=%s";
    /**
     * 小小漫迷1080P
     */
    String MIN_CARTOON = "https://www.xxmanmi.com/video.html?u=%s";
    /**
     * 社工
     */
    String SW = "http://zy.xywlapi.cc/qqcx?qq=%s";
    /**
     * 菜单
     */
    String MENU = "" +
            "[群管系统]\uD83C\uDF38[娱乐系统]\n" +
            "[排行系统]\uD83C\uDF38[日常]\n"+
            "[其他]\n";
    /**
     * 群管系统
     */
    String GROUPMANIGER = "" +
            "[投票踢人]\uD83C\uDF38[名片规范]\n" +
            "[违禁词]\uD83C\uDF38[禁言@+qq+时间]\n" +
            "[解除禁言]\uD83C\uDF38[刷屏检测]\n"+
            "[黑名单]\n";
    /**
     * 娱乐系统
     */
    String ENTERTAINMENT = "" +
            "[看美女]\uD83C\uDF38[动漫+动漫名]\n" +
            "[点歌]\uD83C\uDF38[网易点歌]\n"+
            "[彩票系统]";
    /**
     * 排行系统
     */
    String SORTSYSTEM = "" +
            "[入群榜]\uD83C\uDF38[土豪榜]";

    /**
     * 日常
     */
    String EVERYDAY = "" +
            "[打卡/签到]";
    /**
     * 其他
     */
    String OTHER = "" +
            "[AI聊天]\uD83C\uDF38[闪照破解]\n"+
            "[舔狗日记]\uD83C\uDF38[情话]\n"+
            "[经典语录]\uD83C\uDF38[60秒读世界]\n"+
            "[搜图]\uD83C\uDF38[历史上的今天]\n"+
            "[举牌]\n";

    /**
     * 彩票系统
     */
    String POWERBALL = "" +
            "============彩票系统=============\n"+
            "一等奖：500W积分\n"+
            "二等奖：300W积分\n"+
            "三等奖：100W积分\n"+
            "四等奖：10W积分\n"+
            "五等奖：10积分\n"+
            "六等奖：2积分\n"+
            "规则：全中=》一等奖，前5中=》二等奖，前4中=》三等奖1\n" +
            "     前3中=》四等奖,前2中=》五等奖，第一个中=》六等奖\n"+
            "每次消耗2积分，每天22点开奖\n"+
            "参与方式：发送购买彩票xxxxxx（x必须为数字）";
}
