package cn.ling.bot.util;

import cn.ling.bot.basic.constant.PublicConstant;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * TODO
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class TestUtils {


    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String s = HttpClientUtils.doGet(String.format(PublicConstant.YQ, "成都"));
        System.out.println(s);
    }
}

 class User{
    private Integer age;
    private String name;
    private String sex;

    public User(Integer age, String name, String sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

     @Override
     public String toString() {
         return "User{" +
                 "age=" + age +
                 ", name='" + name + '\'' +
                 ", sex='" + sex + '\'' +
                 '}';
     }
 }