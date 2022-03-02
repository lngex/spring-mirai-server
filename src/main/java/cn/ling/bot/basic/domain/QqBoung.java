package cn.ling.bot.basic.domain;

import lombok.Data;

/**
 * qq绑定信息
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class QqBoung {
    /**
     * 状态码
     */
    private Integer status;
    /**
     * 查询结果
     */
    private String message;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 归属地
     */
    private String phonediqu;
    /**
     * 英雄联盟信息
     */
    private String lol;
    /**
     * 微博
     */
    private String wb;
    /**
     * qqlm
     */
    private String qqlm;
}
