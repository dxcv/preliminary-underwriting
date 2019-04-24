package cn.algerfan.service.impl;

import cn.algerfan.service.PropertiesService;
import cn.algerfan.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * <p>
 *
 * </p>
 *
 * @author algerfan
 * @since 2019/4/21 15
 */
@Service
public class PropertiesServiceImpl implements PropertiesService {
    @Value("${filePath}")
    private String FILE_PATH;

    @Override
    public void show(String filePath, HttpServletResponse response) {
        File file = new File(FILE_PATH + filePath);
        new FileUtil().responseFile(response, file);
    }

    @Override
    public void download(String fileName, HttpServletResponse response) {
        // 设置下载的响应头信息
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + "headPic.jpg");
        File file = new File(FILE_PATH + fileName);
        new FileUtil().responseFile(response, file);
    }

}
