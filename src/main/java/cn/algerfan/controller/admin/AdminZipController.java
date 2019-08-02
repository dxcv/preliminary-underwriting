package cn.algerfan.controller.admin;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Result;
import cn.algerfan.domain.Zip;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 *  压缩文件、提供下载链接、删除文件
 * </p>
 *
 * @author algerfan
 * @since 2019/8/2 09
 */
@Controller
@RequestMapping("/admin/zip")
public class AdminZipController extends BaseController {

    /**
     * 查询所有月份下载地址
     * @return 视图
     */
    @GetMapping("/selectMonth")
    public ModelAndView selectMonth(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
    @RequestParam(name = "pageSize", defaultValue = "15") int pageSize) {
        PageInfo<Zip> select = zipService.selectMonth(pageNum, pageSize);
        model.addAttribute("list", select.getList());
        model.addAttribute("pages",select.getPages());
        model.addAttribute("pageNum",select.getPageNum());
        return new ModelAndView("zip/zip");
    }

    /**
     * 打包文件
     * @param zipId id
     * @param url 当前域名
     * @return
     */
    @PostMapping("/getZip")
    @ResponseBody
    public Result getZip(Integer zipId, String url) {
        return zipService.getZip(zipId, url);
    }

    /**
     * 删除文件
     * @param zipId id
     * @return
     */
    @DeleteMapping
    @ResponseBody
    public Result deleteZip(Integer zipId) {
        return zipService.deleteZip(zipId);
    }

}
