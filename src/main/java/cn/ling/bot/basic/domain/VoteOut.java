package cn.ling.bot.basic.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 投票出局对象
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode
public class VoteOut {
    /**
     * 票数
     */
    private Integer count;
    /**
     * 对象qq
     */
    private Long qq;
    /**
     * 群qq
     */
    private Long groupId;
    /**
     * 唯一标识
     */
    private String uuid;
    /**
     * 参与投票踢出的人
     */
    private List<Long> qqs = new ArrayList<Long>();

}
