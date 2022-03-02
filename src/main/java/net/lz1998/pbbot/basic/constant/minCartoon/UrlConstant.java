package net.lz1998.pbbot.basic.constant.minCartoon;

/**
 * 小小漫迷
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public interface UrlConstant {
    /**
     * 搜索动漫
     */
    String SH = "https://wanghun.top/api/v5/yingshi.php?msg=异世界&m3u8=yes&type=json&n=2 ";
    /**
     * 闪照破解链接
     */
    String IMG = "https://gchat.qpic.cn/gchatpic_new/1029004173/757850203-2719114958-%s/0?term=3";
    /**
     * 点歌
     */
    String MUSIC = "http://xiaobai.klizi.cn/API/music/music.php?data=&msg=%s&n=1&c=json";
    /**
     * qq音乐
     */
    String QQMUSIC = "http://ovooa.com/API/QQ_Music/?Skey=&uin=&msg=%s&n=1";
    /**
     * 网易音乐
     */
    String NETEASEMUSIC = "http://xiaobai.klizi.cn/API/music/netease.php?data=&msg=%s&n=1";
    /**
     * qq查询绑定
     */
    String QQBOUNG = "https://zy.xywlapi.cc/qqcx?qq=%s";
    /**
     * 经典语录
     */
    String CLASSICQUOTE = "https://wanghun.top/api/jdyl.php";
    /**
     * 搜图
     */
    String SOIMG = "https://wanghun.top/api/yh/v2/360st.php?ty=%s";
}
