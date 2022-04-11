package cn.ling.bot.basic;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.ling.bot.basic.constant.MapConstant;
import cn.ling.bot.basic.constant.OneGroupConstant;
import cn.ling.bot.basic.domain.User;
import lombok.extern.slf4j.Slf4j;
import cn.ling.bot.util.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
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
        try(InputStream inputStream = new FileInputStream(file)) {
            ImportParams importParams = new ImportParams();
            importParams.setHeadRows(1);
            importParams.setTitleRows(1);
            List<User> users = ExcelImportUtil.importExcel(inputStream, User.class, importParams);
            System.out.println(users);
            users.forEach(e->{
                MapConstant.GROUPUSERMAP.put(e.getGroupId()+e.getQq(),e);
            });
        } catch (Exception e) {
            log.info("=====================群员列表加载失败==================={}{}",e.getMessage(),e);
        }
        log.info("==========================违禁词添加=======================");
        OneGroupConstant.bannedWord.BANNEDWORD_LIST.addAll(Arrays.asList(OneGroupConstant.bannedWord.BANNEDWORD));
        log.info("==========================管理员添加=======================");
        OneGroupConstant.SUPERADMINS.add(1902156923L);
    }

    @PreDestroy
    public void end() {
        log.info("关闭前执行");
    }

}