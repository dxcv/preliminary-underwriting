package cn.algerfan.service;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *
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
}
