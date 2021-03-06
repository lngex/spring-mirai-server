package cn.ling.bot.async;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.ling.bot.basic.constant.MapConstant;
import cn.ling.bot.basic.domain.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import cn.ling.bot.util.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
    public void userPersistence() {
        String s = FileUtils.pojertPath();
        LOCK.lock();
        try (OutputStream outputStream = new FileOutputStream(s + "/user.xls")) {
            Collection<User> values = MapConstant.GROUPUSERMAP.values();
            ArrayList<User> objects = new ArrayList<>(values);
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("群员资料", "sheet1"),
                    User.class, objects);
            workbook.write(outputStream);
            workbook.close();
        } finally {
            LOCK.unlock();
        }
    }
}
