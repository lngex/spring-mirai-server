package net.lz1998.pbbot.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 文件工具类
 *
 * @author LiaoJianbo
 * @version 1.0.0
 * @since 1.0.0
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 文件上传
     *
     * @param file       文件对象
     * @param parentPath 父级路径
     * @param fileName   文件名(若未空这使用 文件名+时间戳)
     * @return 服务端文件路径
     * @throws IOException 可能抛出的异常
     */
    public static String upload(MultipartFile file, String parentPath, String fileName) throws IOException {
        if (file == null) {
            throw new RuntimeException("请选择文件");
        }
        //获取跟目录
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        logger.info("文件地址：" + path.getAbsolutePath());
        long timeMillis = System.currentTimeMillis();
        String originalFilename = file.getOriginalFilename();
        // 获取类型
        String[] split = originalFilename.split("\\.");
        String type = split[split.length - 1];
        if (StringUtils.isEmpty(fileName)) {
            fileName = split[0] + "==" + timeMillis + "." + type;
        }
        File filePath = new File(path.getAbsolutePath(), "static/" + parentPath + "/");
        // 判断文件路径是否存在，若不存在则创建
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        // 设置储存路径
        File upload = new File(filePath, fileName);
        try (InputStream inputStream = file.getInputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(upload)) {
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (Exception e) {
            logger.error("文件上传异常{}{}", e, e);
            throw new RuntimeException( "文件上传失败!");
        }
        // 设置返回路径 http://"+request.getServerName()+":"+request.getServerPort()+""(域名)
        return "/static/" + parentPath + "/" + fileName;
    }

    /**
     * @param filePath 文件名
     * @return 是否删除成功
     * @throws FileNotFoundException 可能抛出异常
     */
    public static Boolean delFile(String filePath) throws FileNotFoundException {
        if (StringUtils.isEmpty(filePath)) {
            throw new RuntimeException( "请选择文件");
        }
        //获取跟目录
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File upload = new File(path.getAbsolutePath(), filePath);
        if (!upload.delete()) {
            throw new RuntimeException( "文件删除失败!");
        }
        return true;
    }

    /**
     * 获取项目路径
     *
     * @return 项目路径
     * @throws FileNotFoundException 可能抛出异常
     */
    public static String pojertPath() throws FileNotFoundException {
        //获取跟目录
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        return path.getAbsolutePath();
    }

    /**
     * 文件大小计算
     * @param file 文件独享
     * @return  文件大小
     */
    public static String fileSize(MultipartFile file){
        long length = file.getSize();
        BigDecimal bigDecimal = new BigDecimal(length);
        BigDecimal divide = bigDecimal.divide(new BigDecimal("1024"), RoundingMode.HALF_UP);
        if(divide.compareTo(new BigDecimal("1024")) < 0){
            return divide.toString()+"kb";
        }else {
            return divide.divide(new BigDecimal("1024"),RoundingMode.HALF_UP).toString()+"mb";
        }
    }

    /**
     * 文件下载
     *
     * @param fileName 文件名
     * @param response 响应对象
     * @throws  FileNotFoundException 文件不存在
     */
    public static void download(String fileName, HttpServletResponse response) throws FileNotFoundException {
        if (StringUtils.isEmpty(fileName)) {
            throw new RuntimeException("请选择文件");
        }
        //获取跟目录
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        // 创建文件对象
        File upload = new File(path.getAbsolutePath(), fileName);
        // 判断文件是否存在
        if (!upload.exists()) {
            response.setStatus(404);
        } else {
            // 使用自动关流方式
            try (
                    ServletOutputStream outputStream = response.getOutputStream();
                    FileInputStream fileInputStream = new FileInputStream(upload)
            ) {
                // 输出文件
                IOUtils.copy(fileInputStream, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(pojertPath());
    }
}
