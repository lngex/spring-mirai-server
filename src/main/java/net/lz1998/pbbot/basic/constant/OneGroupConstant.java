package net.lz1998.pbbot.basic.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 群枚举常量
 * 757850203
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OneGroupConstant {

    List<Long> BLACKLIST = new ArrayList<>();

    public interface Role {
        /**
         * 群主
         */
        String OWNER = "owner";
        /**
         * 管理员
         */
        String ADMIN = "admin";
        /**
         * 普通群员
         */
        String MEMBER = "member";
    }

    public interface Sex {
        /**
         * 男性
         */
        String MALE = "male";
        /**
         * 女性
         */
        String FEMALE = "female";
    }

    public interface Card {
        /**
         * 全部群员
         */
        String[] ALL_CARD = {"\uD83C\uDF40Free、", "\uD83C\uDF31新木、", "『✗讨伐之剑』", "❀无名、", "『堕落之翼』", "\uD83E\uDD21"};
        /**
         * 管理员
         */
        String ADMIN = "\uD83C\uDF40Free、";
        /**
         * 🌱普通群员男
         */
        String MALE = "\uD83C\uDF31新木、";
        /**
         * 普通群员女
         */
        String FEMALE = "❀无名、";
        /**
         * 堕落之一
         */
        String CORRUPT = "『堕落之翼』";
        /**
         * 小丑🤡
         */
        String CLOWN = "\uD83E\uDD21";
        /**
         * 『🐮牛头人』
         */
        String KILL = "『\uD83D\uDC2E牛头人』";
        /**
         * 『🐮真●牛头人』
         */
        String TKILL = "『\uD83D\uDC2E真●牛头人』";
    }

    public interface Limit {
        /**
         * 投票踢人极限
         */
        Integer VOTEOUT = 5;
        /**
         * 过期时间 单位分钟
         */
        Long EXPIRTIME = 5L;
    }

    public interface message {
        /**
         * 1qq，2得票，3极限
         */
        String VOTEOUT = "%s当前票数%s票," + Limit.VOTEOUT + "分钟内达到%s票会被踢出群聊";
        /**
         * 版权
         */
        String COPYRIGHT = "&lt;?xml version=&#39;1.0&#39; encoding=&#39;UTF-8&#39; standalone=&#39;yes&#39; ?&gt;&lt;msg serviceID=&#34;14&#34; templateID=&#34;1&#34; action=&#34;plugin&#34; actionData=&#34;AppCmd://OpenContactInfo/?uin=1902156923&#34; a_actionData=&#34;mqqapi://card/show_pslcard?src_type=internal&amp;amp;source=sharecard&amp;amp;version=1&amp;amp;uin=1902156923&#34; i_actionData=&#34;mqqapi://card/show_pslcard?src_type=internal&amp;amp;source=sharecard&amp;amp;version=1&amp;amp;uin=1902156923&#34; brief=&#34;推荐了机器主人&#34; sourceMsgId=&#34;0&#34; url=&#34;&#34; flag=&#34;1&#34; adverSign=&#34;0&#34; multiMsgFlag=&#34;0&#34;&gt;&lt;item layout=&#34;0&#34; mode=&#34;1&#34; advertiser_id=&#34;0&#34; aid=&#34;0&#34;&gt;&lt;summary&gt;推荐好友&lt;/summary&gt;&lt;hr hidden=&#34;false&#34; style=&#34;0&#34; /&gt;&lt;/item&gt;&lt;item layout=&#34;2&#34; mode=&#34;1&#34; advertiser_id=&#34;0&#34; aid=&#34;0&#34;&gt;&lt;picture cover=&#34;mqqapi://card/show_pslcard?src_type=internal&amp;amp;source=sharecard&amp;amp;version=1&amp;amp;uin=1902156923&#34; w=&#34;0&#34; h=&#34;0&#34; /&gt;&lt;title&gt;点击联系~&lt;/title&gt;&lt;summary&gt;这是我的主人呦&lt;/summary&gt;&lt;/item&gt;&lt;source name=&#34;&#34; icon=&#34;&#34; action=&#34;&#34; appid=&#34;-1&#34; /&gt;&lt;/msg&gt;";
    }

    public interface bannedWord {
        /**
         * 违禁词数组
         */
        String[] BANNEDWORD = {
                "msl",
                "本子",
                "撸管",
                "cnm",
                "阴痿",
                "你妈",
                "你m",
                "nmlgb",
                "nmd",
                "65310162-F7A5-1673-B943-E482F205CA7B",
                "D034F63A-B380-2BD7-549F-6C0B33FF2F46",
                "D034F63AB3802BD7549F6C0B33FF2F46"
        };
        /**
         * 白名单
         */
        long[] QQS = {1902156923L};
        /**
         * 违禁次数
         */
        Integer NUMBER = 5;
        /**
         * 第一次
         */
        String ONE = "你已经触发了违禁词,禁言10分钟";
        /**
         * 第二次
         */
        String TEW = "你已经触发了违禁词,禁言20分钟";
        /**
         * 第三次
         */
        String THREE = "你已经触发了违禁词,禁言60分钟";
        /**
         * 第四次
         */
        String FOUR = "你已经触发了违禁词,禁言2小时";
        /**
         * 第五次
         */
        String FIVE = "你已经触发了违禁词,禁言6小时";
    }
}
