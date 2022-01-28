package net.lz1998.pbbot.basic.domain.mincartoon;

import lombok.Data;

/**
 * 小小漫迷搜索结果
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class MinCartoon {
    /**
     * 主键id
     */
    private String id;
    /**
     * 名字
     */
    private String name;
    /**
     * 详细
     */
    private String detail;
    /**
     * 封面
     */
    private String img;
}
