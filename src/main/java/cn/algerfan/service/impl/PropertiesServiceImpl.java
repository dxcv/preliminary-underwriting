package cn.algerfan.service.impl;

import cn.algerfan.service.PropertiesService;
import cn.algerfan.util.FileUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>
 *  文件图片查看下载
 * </p>
 *
 * @author algerfan
 * @since 2019/4/21 15
 */
@Service
public class PropertiesServiceImpl implements PropertiesService {
    @Value("${filePath}")
    private String path;

    /**
     *  日志
     */
    protected Logger log;

    public PropertiesServiceImpl() {
        log = Logger.getLogger(this.getClass());
    }

    @Override
    public void show(String filePath, HttpServletResponse response) {
        File file = new File(path + filePath);
        new FileUtil().responseFile(response, file);
    }

    @Override
    public void download(String fileName, HttpServletResponse response) {
        // 设置下载的响应头信息
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + "headPic.jpg");
        File file = new File(path + fileName);
        new FileUtil().responseFile(response, file);
    }

    @Override
    public Resource loadAsResource(String filename) {
        Path rootLocation = Paths.get(path +"/uploadData/");
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.info("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            log.info("Could not read file: " + filename);
        }
        return null;
    }

}
