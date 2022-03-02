package net.lz1998.pbbot.basic.domain.netease;

import lombok.Data;

/**
 * TODO
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class NeteaseMusic {
    /**
     * 封面
     */
    private String img;
    /**
     * 曲名
     */
    private String song;
    /**
     * 歌手
     */
    private String singer;
    /**
     * 音乐地址
     */
    private String url;
}
