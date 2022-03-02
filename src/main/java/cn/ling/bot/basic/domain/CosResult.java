package cn.ling.bot.basic.domain;

import lombok.Data;

/**
 * 响应结果
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class CosResult {
    private String code;
    private String text;
    private cn.ling.bot.basic.domain.Data data;
}
