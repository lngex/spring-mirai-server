package net.lz1998.pbbot.basic.domain;

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
    private net.lz1998.pbbot.basic.domain.Data data;
}
