package cn.ling.bot.basic;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.ling.bot.basic.constant.MapConstant;
import cn.ling.bot.basic.constant.OneGroupConstant;
import cn.ling.bot.basic.domain.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import cn.ling.bot.util.FileUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class RunScript {

    @PostConstruct
    public void start() throws FileNotFoundException {
        log.info("=====================加载excel===================");
        String s = FileUtils.pojertPath();
        File file = new File(s + "/user.xls");
        if (!file.exists()) {
            return;
        }
        try (InputStream inputStream = new FileInputStream(file)) {
            ImportParams importParams = new ImportParams();
            importParams.setHeadRows(1);
            importParams.setTitleRows(1);
            List<User> users = ExcelImportUtil.importExcel(inputStream, User.class, importParams);
            System.out.println(users);
            users.forEach(e -> {
                MapConstant.GROUPUSERMAP.put(e.getGroupId() + e.getQq(), e);
            });
        } catch (Exception e) {
            log.info("=====================群员列表加载失败==================={}{}", e.getMessage(), e);
        }
        log.info("==========================违禁词读取=======================");
        try (FileInputStream fileInputStream = new FileInputStream(new File(FileUtils.pojertPath(), "违禁词列表.txt"))) {
            byte[] bytes = IOUtils.toByteArray(fileInputStream);
            String s1 = new String(bytes, StandardCharsets.UTF_8).trim();
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>() {
            }.getType();
            List<String> list = gson.fromJson(s1, type);
            OneGroupConstant.bannedWord.BANNEDWORD_LIST.addAll(list);
        } catch (IOException e) {
            log.error("违禁词列表读取失败，采用默认违禁词{}{}", e.getMessage(), e);
            OneGroupConstant.bannedWord.BANNEDWORD_LIST.addAll(Arrays.asList(OneGroupConstant.bannedWord.BANNEDWORD));
        }
        log.info("==========================管理员添加=======================");
        OneGroupConstant.SUPERADMINS.add(1902156923L);
    }

    @PreDestroy
    public void end() {
        log.info("关闭前执行");
        // 违禁词持久化
        log.info("违禁词持久化========================");
        Gson gson = new Gson();
        byte[] bytes = gson.toJson(OneGroupConstant.bannedWord.BANNEDWORD_LIST).getBytes(StandardCharsets.UTF_8);
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(FileUtils.pojertPath(), "违禁词列表.txt"))) {
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } catch (Exception e) {
            log.error("违禁词持久化失败{}{}", e.getMessage(), e);
        }
    }

}