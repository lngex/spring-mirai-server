package net.lz1998.pbbot.async;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.basic.constant.MapConstant;
import net.lz1998.pbbot.basic.domain.User;
import net.lz1998.pbbot.util.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据持久化
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
public class DatePersistenceAsync {

    public static final ReentrantLock LOCK = new ReentrantLock();

    /**
     * 用户持久化
     */
    @SneakyThrows
    @Async
    public void userPersistence(){
        String s = FileUtils.pojertPath();
        LOCK.lock();
        try(OutputStream outputStream = new FileOutputStream(s+"\\user.xls")) {
            Collection<User> values = MapConstant.GROUPUSERMAP.values();
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("群员资料","sheet1"),
                    User.class, values);
            workbook.write(outputStream);
            workbook.close();
        }finally {
            LOCK.unlock();
        }
    }
}
