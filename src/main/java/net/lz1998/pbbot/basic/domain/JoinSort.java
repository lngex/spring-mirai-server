package net.lz1998.pbbot.basic.domain;

import lombok.Data;

/**
 * 入群榜对象
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class JoinSort {
    /**
     * qq
     */
    private Long qq;
    /**
     * 马甲
     */
    private String card;
    /**
     * 入群时间
     */
    private Long joinTime;
}
