package cn.ling.bot.basic.domain.mincartoon;

import lombok.Data;

import java.util.List;

/**
 * 小小漫迷查看详细
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class MinCartoonTew {
    /**
     * 名字
     */
    private String name;
    /**
     * 值
     */
    private String value;
    /**
     * 资源详细
     */
    private List<MinCartoonThree> list;
}
