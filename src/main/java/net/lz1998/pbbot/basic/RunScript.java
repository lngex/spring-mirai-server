package net.lz1998.pbbot.basic;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.basic.constant.MapConstant;
import net.lz1998.pbbot.basic.domain.User;
import net.lz1998.pbbot.util.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.List;

@Component
@Slf4j
public class RunScript {

    @PostConstruct
    public void start() throws FileNotFoundException {
        log.info("=====================加载excel===================");
        String s = FileUtils.pojertPath();
        File file = new File(s + "\\user.xls");
        if(!file.exists()){
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
    }

    @PreDestroy
    public void end() {
        log.info("关闭前执行");
    }

}