package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * <p>
 *  文件图片查看下载
 * </p>
 *
 * @author algerfan
 * @since 2019/4/22 19
 */
@Controller
@RequestMapping("/file")
@Api(value = "文件图片查看下载", tags = "文件图片查看下载")
public class PropertiesController extends BaseController {

    /**
     * 单个图片显示
     * @param filePath
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "单个图片显示", notes = "单个图片显示 参数：filePath-文件路径",
            httpMethod = "GET")
    public void showPicture(String filePath, HttpServletResponse response, HttpServletRequest request) throws IOException {
        propertiesService.show(filePath,response);
    }

    /**
     * 单个图片、文件下载
     * @param filePath
     * @param response
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "单个图片、文件下载", notes = "单个图片、文件下载 参数：filePath-文件图片路径",
            httpMethod = "GET")
    public void download(String filePath, HttpServletResponse response){
        propertiesService.download(filePath,response);
    }

    /**
     * 多线程文件下载
     * @param filename
     * @return
     */
    @GetMapping("/files")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(String filename) {
        Resource file = propertiesService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
