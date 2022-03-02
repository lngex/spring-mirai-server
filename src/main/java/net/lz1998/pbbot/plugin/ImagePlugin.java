package net.lz1998.pbbot.plugin;

import com.google.gson.Gson;
import net.lz1998.pbbot.basic.domain.Img;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.util.HttpClientUtils;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ImagePlugin extends BotPlugin {
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        if (!IntercepterPlugin.GROUPS.contains(event.getGroupId())) {
            return 1;
        }
        // 群号
        long groupId = event.getGroupId();
        // 发送者QQ
        long userId = event.getUserId();
        // 文本消息
        String rawMsg = event.getRawMessage();
        if("看妹子".equals(rawMsg.trim())){
            String s = HttpClientUtils.doGet("http://ovooa.com/API/meizi/api.php");
            Gson gson = new Gson();
            Img img = gson.fromJson(s, Img.class);
            Msg.builder().at(userId).image(img.getText()).sendToGroup(bot,groupId);
        }
        return 1;
    }
}
