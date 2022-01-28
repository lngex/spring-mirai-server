package net.lz1998.pbbot.util;

import net.lz1998.pbbot.basic.constant.minCartoon.UrlConstant;
import net.lz1998.pbbot.basic.constant.xml.XmlConstant;

import java.util.concurrent.ThreadLocalRandom;

/**
 * TODO
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class TestUtils {
    public static void main(String[] args) {
        // System.out.println(HttpClientUtils.doGet(String.format(UrlConstant.MUSIC,"青花瓷")));
        System.out.println(HttpClientUtils.doGet("http://api.weijieyue.cn/api/youhuo/api.php?return=txt"));
    }
}