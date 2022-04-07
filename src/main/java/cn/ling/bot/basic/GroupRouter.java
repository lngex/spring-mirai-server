package cn.ling.bot.basic;

import cn.ling.bot.async.GroupMesageAsync;
import net.lz1998.pbbot.bot.Bot;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 群路由
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */

@Component
public class GroupRouter {

    @Autowired
    private GroupMesageAsync groupMesageAsync;

    @Async
    public void router(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        long groupId = event.getGroupId();
        if (757850203L == groupId) {
            // 改名检测
            groupMesageAsync.oneRename(bot, event);
            // 投票踢人
            groupMesageAsync.voteOut(bot, event);
            // 违禁词处理
            groupMesageAsync.bannedWord(bot, event);
            // 小爱聊天
            groupMesageAsync.minAi(bot,event);
            // 第三方api
            groupMesageAsync.girlImg(bot, event);
            // 按入群时间排序
            groupMesageAsync.joinSort(bot, event);
            // 动漫
            // groupMesageAsync.lookCartoon(bot,event);
            // 闪照破解
            groupMesageAsync.flashImgCrack(bot, event);
            // 刷屏检测
            groupMesageAsync.spam(bot, event);
            // 黑名单
            groupMesageAsync.bolkList(bot, event);
            groupMesageAsync.bolkCk(bot, event);
            // 网易点歌
            groupMesageAsync.neteaseMusic(bot, event);
            // 全体改名
            groupMesageAsync.allRename(bot, event);
            // 彩票
            groupMesageAsync.powerball(bot, event);
            // 投票游戏
            //groupMesageAsync.voteGame(bot, event);
        } else if (1050122161L == groupId) {
            groupMesageAsync.test(bot, event);
            groupMesageAsync.girlImg(bot, event);
            groupMesageAsync.joinSort(bot, event);
            // 动漫
            groupMesageAsync.lookCartoon(bot, event);
            // groupMesageAsync.minAi(bot,event);
            // 黑名单
            groupMesageAsync.bolkList(bot, event);
            groupMesageAsync.bolkCk(bot, event);
            // 网易点歌
            groupMesageAsync.neteaseMusic(bot, event);
            // 查绑定
            groupMesageAsync.qqBound(bot, event);
            // 彩票
            groupMesageAsync.powerball(bot, event);
        } else if (793893098 == groupId) {
            // 动漫
            // groupMesageAsync.lookCartoon(bot,event);
            // 小爱聊天
            groupMesageAsync.minAi(bot, event);
            // 入群排行
            groupMesageAsync.joinSort(bot, event);
            // 闪照破解
            groupMesageAsync.flashImgCrack(bot, event);
            // 刷屏检测
            groupMesageAsync.spam(bot, event);
            // 黑名单
            groupMesageAsync.bolkList(bot, event);
            groupMesageAsync.bolkCk(bot, event);
            // 第三方api
            groupMesageAsync.girlImg(bot, event);
            // 彩票
            groupMesageAsync.powerball(bot, event);
        }
    }
}
