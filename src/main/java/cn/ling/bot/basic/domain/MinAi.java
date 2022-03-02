package cn.ling.bot.basic.domain;

import lombok.Data;

/**
 * 小艾响应对象
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class MinAi {
    /**
     * 文本消息
     */
    private String text;
    /**
     * 语音链接
     */
    private String mp3;
}
