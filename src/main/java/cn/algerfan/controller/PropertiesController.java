package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import cn.algerfan.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 *  图片文件查看下载Controller
 * </p>
 *
 * @author algerfan
 * @since 2019/4/22 19
 */
@Controller
@RequestMapping("/file")
@Api(value = "文件图片查看下载", tags = "文件图片查看下载")
public class PropertiesController extends BaseController {

    private final StorageService storageService;

    @Autowired
    public PropertiesController(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * 处理图片显示
     * @param filePath
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "处理图片显示", notes = "处理图片显示 参数：filePath-文件路径",
            httpMethod = "GET")
    public void showPicture(String filePath, HttpServletResponse response, HttpServletRequest request) throws IOException {
        propertiesService.show(filePath,response);
    }

    /**
     * 处理文件图片下载请求
     * @param filePath
     * @param response
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "处理文件图片下载请求", notes = "处理文件图片下载请求 参数：filePath-文件图片路径",
            httpMethod = "GET")
    public void download(String filePath, HttpServletResponse response){
        propertiesService.download(filePath,response);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @ResponseBody
    @RequestMapping("/download4")
    public void download4() {
        String s = MultiPartDownLoad.downLoad("http://underwriting.algerfan.cn/file/files/npp.7.5.4.Installer.exe");
        log.info(s);
    }

}
