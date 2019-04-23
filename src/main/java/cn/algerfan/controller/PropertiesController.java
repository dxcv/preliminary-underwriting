package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

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
@Api(value = "图片文件查看下载", tags = "图片文件查看下载")
public class PropertiesController extends BaseController {

    /**
     * 处理图片显示请求
     * @param filePath
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @ApiOperation(value = "处理图片显示请求", notes = "处理图片显示请求 参数：filePath-图片路径",
            httpMethod = "GET")
    public void showPicture(String filePath, HttpServletResponse response){
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

}
