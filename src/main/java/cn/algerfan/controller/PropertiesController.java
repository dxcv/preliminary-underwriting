package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  文件Controller
 * </p>
 *
 * @author algerfan
 * @since 2019/4/22 19
 */
@Controller
@RequestMapping("/file")
public class PropertiesController extends BaseController {

    /**
     * 处理图片显示请求
     * @param fileName
     */
    @RequestMapping("/show")
    public void showPicture(String fileName, HttpServletResponse response){
        propertiesService.show(fileName,response);
    }

    /**
     * 处理图片下载请求
     * @param fileName
     * @param response
     */
    @RequestMapping("/download")
    public void download(String fileName, HttpServletResponse response){
        propertiesService.download(fileName,response);
    }

}
