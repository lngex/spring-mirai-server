package cn.ling.bot.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LIPENGCHENG
 * Description 字符串工具类
 */
public class StringUtil {

    static byte[] base = new byte[]{-17, -65, -67};

    /**
     * 是否为数字
     *
     * @param s 字符
     * @return 是/否
     */
    public static boolean isNumber(String s) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(s).matches();
    }

    /**
     * 检查是否为空
     *
     * @param s 字符
     * @return 是/否
     */
    public static boolean checkNull(String s) {
        if ("".equals(s)) {
            return true;
        }
        if (null == s) {
            return true;
        }
        return false;
    }

    /**
     * 是否为空
     *
     * @param str 字符
     * @return 是/否
     */
    public static boolean isEmpty(String str) {
        return null == str || str.trim().length() == 0 || "".equals(str) || "null".equals(str) || "undefined".equals(str);
    }

    /**
     * 是否非空
     *
     * @param str 字符
     * @return 是/否
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 是否全视空
     *
     * @param strs 字符
     * @return 是/否
     */
    public static boolean isAllEmpty(String... strs) {
        for (String str : strs) {
            if (isNotEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全不是空
     *
     * @param strs 字符
     * @return 是/否
     */
    public static boolean isAllNotEmpty(String... strs) {
        for (String str : strs) {
            if (isEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否包含空
     *
     * @param strs 字符
     * @return 是/否
     */
    public static boolean hasEmpty(String... strs) {
        for (String str : strs) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串是否包含中文字符
     *
     * @param str 字符
     * @return 是/否
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 去除字符串中所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
     *
     * @param s 字符
     * @return 字符
     */
    public static String removeAllBlank(String s) {
        String result = "";
        if (!isEmpty(s)) {
            result = s.replaceAll("[　*| *| *|\\s*]*", "");
        }
        return result;
    }

    /**
     * 去除字符串头部和尾部所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
     *
     * @param s 字符
     * @return 字符
     */
    public static String trim(String s) {
        String result = "";
        if (!isEmpty(s)) {
            result = s.replaceAll("^[　*| *| *|\\s*]*", "").replaceAll("[　*| *| *|\\s*]*$", "");
        }
        return result;
    }

    /**
     * 使用下划线_替换掉字符串中不符合文件的非法字符
     *
     * @param s 字符
     * @return 字符
     */
    public static String replaceFileName(String s) {
        String result = "";
        if (!isEmpty(s)) {
            result = s.replaceAll("[/\\\":*?<>|]", "_");
        }
        return result;
    }

    /**
     * 获取指定长度的字符串
     *
     * @param length 字符串长度
     * @return 随机生成的字符串
     */
    public static String getComplexRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" +
                     "敳屮屰屲屳屴屵屶屷屸屹屺屻屼屽屾屿岃岄岅岆岇岈岉岊岋岌岍岎岏岐岑岒岓岔岕岖岘岙岚" +
                     "岜岝岞岟岠岗岢岣岤岥岦岧岨岪岫岬岮岯岰岲岴岵岶岷岹岺岻岼岽岾岿峀峁峂峃峄峅峆峇峈" +
                     "峉峊峋峌峍峎峏峐峑峒峓崓峖峗峘峚峙峛峜峝峞峟峠峢峣峤峥峦峧峨峩峪峬峫峭峮峯峱峲峳" +
                     "岘峵峷峸峹峺峼峾峿崀崁崂崃崄崅崆崇崈崉崊崋崌崃崎崏崐崒崓崔崕崖崘崚崛崜崝崞崟岽崡" +
                     "峥崣崤崥崦崧崨崩崪崫崬崭崮崯崰崱崲嵛崴崵崶崷崸崹崺崻崼崽崾崿嵀嵁嵂嵃嵄嵅嵆嵇嵈嵉" +
                     "嵊嵋嵌嵍嵎嵏岚嵑岩嵓嵔嵕嵖嵗嵘嵙嵚嵛嵜嵝嵞嵟嵠嵡嵢嵣嵤嵥嵦嵧嵨嵩嵪嵫嵬嵭嵮嵯嵰嵱" +
                     "嵲嵳嵴嵵嵶嵷嵸嵹嵺嵻嵼嵽嵾嵿嶀嵝嶂嶃崭嶅嶆岖嶈嶉嶊嶋嶌嶍嶎嶏嶐嶑嶒嶓嵚嶕嶖嶘嶙嶚" +
                     "嶛嶜嶝嶞嶟峤嶡峣嶣嶤嶥嶦峄峃嶩嶪嶫嶬嶭崄嶯嶰嶱嶲嶳岙嶵嶶嶷嵘嶹岭嶻屿岳帋巀巁巂巃" +
                     "巄巅巆巇巈巉巊岿巌巍巎巏巐巑峦巓巅巕岩巗巘巙巚";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 字节截取
     *
     * @param len 字节长度
     * @param str 被截取的字符串
     * @return 截取后
     */
    public static String intercept(int len, String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > len) {
            byte[] bytes1 = Arrays.copyOf(bytes, len);
            str = new String(bytes1, StandardCharsets.UTF_8);
            byte[] bytes2 = str.substring(str.length() - 1).getBytes(StandardCharsets.UTF_8);
            if (Arrays.equals(base, bytes2)) {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("E:\\嗨创project\\s.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(10);
        allocate.put("廖某".getBytes(StandardCharsets.UTF_8));
        channel.write(allocate);
        channel.close();
        randomAccessFile.close();
    }
}
