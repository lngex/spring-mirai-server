package cn.ling.bot.util;

import cn.ling.bot.basic.constant.PublicConstant;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Arrays;

/**
 * TODO
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class TestUtils {


    public static void main(String[] args) {
        String s = HttpClientUtils.doGet("https://api.linhun.vip/api/Littlesistervideo?type=json");
        System.out.println(s);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String video = jsonObject.getString("video");
    }


    public static int[] twoSum(int[] nums, int target) {
        int length = nums.length;
        for (int x = 0; x < length; x++) {
            for (int y = x + 1; y < length; y++) {
                if (nums[x] + nums[y] == target) {
                    return new int[]{x,y};
                }
            }
        }
        return new int[]{};
    }
}