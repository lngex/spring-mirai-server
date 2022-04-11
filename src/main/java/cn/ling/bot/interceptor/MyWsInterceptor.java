package cn.ling.bot.interceptor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.handler.BotSessionInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author King
 */
@Slf4j
@Primary
@Component
public class MyWsInterceptor extends BotSessionInterceptor {


    /**
     * 可以用来拒绝某个链接
     * @param session 长连接会话
     * @return false拒绝连接，true允许链接
     */
    @SneakyThrows
    @Override
    public boolean checkSession(@NotNull WebSocketSession session) {
        HttpHeaders headers = session.getHandshakeHeaders();
        String botId = headers.getFirst("x-self-id");
        System.out.println(headers);
        log.info("请求连接头：{}",headers);
        log.info("新的链接：{}",botId);
        if ("123".equals(botId)) {
            System.out.println("机器人账号是123，关闭连接");
            session.close();
            // 禁止连接
            return false;
        }
        // 正常连接
        return true;
    }
}
