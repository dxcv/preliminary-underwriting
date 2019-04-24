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
    void show(String filePath, HttpServletResponse response);

    void download(String fileName, HttpServletResponse response);
}
