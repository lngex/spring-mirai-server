package cn.ling.bot.basic.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * qq用户
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@ExcelTarget("User")
public class User implements Serializable {
    /**
     * 群号
     */
    @Excel(name = "QQ群")
    private Long groupId;
    /**
     * qq
     */
    @Excel(name = "QQ号")
    private Long qq;
    /**
     * 积分
     */
    @Excel(name = "积分")
    private BigDecimal integrate = BigDecimal.ZERO;
    /**
     * 是否黑名单
     */
    @Excel(name = "黑名单")
    private Boolean isBolk = false;
    /**
     * 是否已签到
     */
    private Boolean isSingin = false;
    /**
     * 连续打卡天数
     */
    @Excel(name = "连续打卡天数")
    private Integer singinDay = 0;
}
