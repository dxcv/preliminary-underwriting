package cn.algerfan.controller.admin;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Underwriting;
import cn.algerfan.util.FileUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * <p>
 *  后台核保人工作池
 * </p>
 *
 * @author algerfan
 * @since 2019/4/18 09
 */
@Controller
@RequestMapping("/admin/underwriting")
public class AdminUnderwritingController extends BaseController {
    @Value("${filePath}")
    public String FILE_PATH;

    /**
     * 核保人代办
     * @param keyword
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "select", method = RequestMethod.GET)
    public ModelAndView select(String keyword, Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize) {
        PageInfo<Underwriting> select = underwritingService.select(keyword, pageNum, pageSize);
        log.info("查询成功："+select.getList());
        model.addAttribute("list", select.getList());
        model.addAttribute("pages",select.getPages());
        model.addAttribute("pageNum",select.getPageNum());
        return new ModelAndView("underwriting");
    }

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
    @RequestMapping("/download/{fileName}.{suffix}")
    public void download(@PathVariable("fileName") String fileName,
                         @PathVariable("suffix") String suffix,
                         HttpServletResponse response){
        propertiesService.download(fileName,suffix,response);
    }

    /**
     * 核保人处理历史
     * @param keyword
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "selectHistory", method = RequestMethod.GET)
    public ModelAndView selectHistory(String keyword, Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize) {
        PageInfo<Underwriting> select = underwritingService.selectHistory(keyword, pageNum, pageSize);
        log.info("查询成功："+select.getList());
        model.addAttribute("list", select.getList());
        model.addAttribute("pages",select.getPages());
        model.addAttribute("pageNum",select.getPageNum());
        return new ModelAndView("underwritingHistory");
    }

}
