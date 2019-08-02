package cn.algerfan.service;

import cn.algerfan.domain.Result;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;

/**
 * <p>
 *  文件图片查看下载
 * </p>
 *
 * @author algerfan
 * @since 2019/4/21 15
 */
public interface PropertiesService {
    /**
     * 文件展示
     * @param filePath
     * @param response
     */
    void show(String filePath, HttpServletResponse response);

    /**
     * 文件下载
     * @param fileName
     * @param response
     */
    void download(String fileName, HttpServletResponse response);

    /**
     * 多线程文件下载
     * @param filename
     * @return
     */
    Resource loadAsResource(String filename);

}
