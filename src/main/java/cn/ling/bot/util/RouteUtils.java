package cn.ling.bot.util;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 路由工具
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class RouteUtils {

    /**
     * 0 无
     * 1投票踢人
     *
     * @param msg 路由解析
     */
    public static int msgRoute(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return 0;
        }
        msg = msg.trim();
        if (msg.indexOf("投票踢人") == 0) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("E:\\嗨创project\\辞职信.docx");
        System.out.println(file.getName());
        FileInputStream fileInputStream = new FileInputStream(file);
        System.out.println(file.getName());
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer[] byteBuffers = new ByteBuffer[(int) channel.size()];
        channel.read(byteBuffers);
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\嗨创project\\辞职信2.docx");
        FileChannel channel1 = fileOutputStream.getChannel();
        channel1.write(byteBuffers);
    }
}
