package cn.algerfan.controller.admin;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Underwriting;
import cn.algerfan.util.FileUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "后台核保人工作池", tags = "后台核保人工作池")
public class AdminUnderwritingController extends BaseController {

    /**
     * 1.代办预核保
     * 2.按姓名查询 keyword（姓名）
     * @param keyword
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询或搜索代办预核保", notes = "查询或搜索代办预核保 搜索加上参数keyword-姓名",
            httpMethod = "GET")
    public ModelAndView select(String keyword, Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "15") int pageSize) {
        PageInfo<Underwriting> select = underwritingService.select(keyword, pageNum, pageSize);
        log.info("查询成功："+select.getList());
        model.addAttribute("list", select.getList());
        model.addAttribute("pages",select.getPages());
        model.addAttribute("pageNum",select.getPageNum());
        return new ModelAndView("underwriting");
    }

    /**
     * 查看预核保详情
     * @param underwritingId
     * @param model
     */
    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    @ApiOperation(value = "查看预核保详情", notes = "查看预核保详情 参数underwritingId-预核保id",
            httpMethod = "GET")
    public ModelAndView selectById(Integer underwritingId, Model model) {
        Underwriting underwriting = underwritingService.selectById(underwritingId);
        if(underwriting==null) {
            log.info("该预核保不存在！");
            model.addAttribute("msg", "该预核保不存在！");
            return new ModelAndView("redirect:/admin/underwriting/select");
        }
        log.info("查询成功："+underwriting);
        model.addAttribute("msg", "查询成功");
        model.addAttribute("underwriting", underwriting);
        return new ModelAndView("underwritingDetails");
    }

    /**
     * 1.预核保历史
     * 2.按姓名查询 keyword（姓名）
     * @param keyword
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/selectHistory", method = RequestMethod.GET)
    @ApiOperation(value = "预核保历史", notes = "查询或搜索预核保历史 搜索加上参数keyword-姓名",
            httpMethod = "GET")
    public ModelAndView selectHistory(String keyword, Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "15") int pageSize) {
        PageInfo<Underwriting> select = underwritingService.selectHistory(keyword, pageNum, pageSize);
        log.info("查询成功："+select.getList());
        model.addAttribute("list", select.getList());
        model.addAttribute("pages",select.getPages());
        model.addAttribute("pageNum",select.getPageNum());
        return new ModelAndView("underwritingHistory");
    }

}
