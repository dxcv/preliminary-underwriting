package cn.algerfan.controller.admin;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Agent;
import cn.algerfan.domain.Underwriting;
import cn.algerfan.util.CheckUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        model.addAttribute("keyword",keyword);
        model.addAttribute("list", select.getList());
        model.addAttribute("pages",select.getPages());
        log.info(select.getPages());
        model.addAttribute("pageNum",select.getPageNum());
        return new ModelAndView("/underwriting/underwriting");
    }

    /**
     * 查看预核保详情
     * @param underwritingId
     * @param model
     */
    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    @ApiOperation(value = "查看预核保详情", notes = "查看预核保详情 参数underwritingId-预核保id  type-代办为1，历史为2",
            httpMethod = "GET")
    public ModelAndView selectById(String type, Integer underwritingId, Model model) {
        Underwriting underwriting = underwritingService.selectById(underwritingId);
        if(underwriting==null) {
            log.info("该预核保不存在！");
            model.addAttribute("msg", "该预核保不存在！");
            return new ModelAndView("redirect:/admin/underwriting/select");
        }
        log.info("查询成功："+underwriting);
        String[] split = underwriting.getData().split(",");
        CheckUtil checkUtil = new CheckUtil();
        List<String> images = new ArrayList<>();
        List<String> files = new ArrayList<>();
        for (String s : split) {
            String substring = s.substring(s.length() - 40);
            if (checkUtil.checkImage(substring)) {
                images.add(s);
            }
            if (checkUtil.checkFile(substring)) {
                files.add(s);
            }
        }
        model.addAttribute("images",images);
        model.addAttribute("files",files);
        Agent agent = agentService.selectById(underwriting.getAgentId());
        model.addAttribute("msg", "查询成功");
        model.addAttribute("agent",agent);
        model.addAttribute("underwriting", underwriting);
        if("1".equals(type)) {
            return new ModelAndView("/underwriting/underwritingDetails");
        }
        if("2".equals(type)) {
            return new ModelAndView("/underwriting/underwritingHistoryDetails");
        }
        model.addAttribute("msg","查询失败！");
        return new ModelAndView("/underwriting/underwritingDetails");
    }

    /**
     * 1.查询或搜索预核保历史
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
        model.addAttribute("keyword",keyword);
        model.addAttribute("list", select.getList());
        model.addAttribute("pages",select.getPages());
        model.addAttribute("pageNum",select.getPageNum());
        return new ModelAndView("/underwriting/underwritingHistory");
    }

    /**
     * 按时间查询核保人历史
     * @param keyword
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/selectByDate", method = RequestMethod.GET)
    @ApiOperation(value = "按时间查询预核保历史", notes = "按时间查询预核保历史 参数keyword-时间间隔",
            httpMethod = "GET")
    public ModelAndView selectByDate(String keyword, Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "15") int pageSize) {
        PageInfo<Underwriting> select = underwritingService.selectByDate(keyword, pageNum, pageSize);
        log.info("查询成功："+select.getList());
        model.addAttribute("keyword",keyword);
        model.addAttribute("list", select.getList());
        model.addAttribute("pages",select.getPages());
        model.addAttribute("pageNum",select.getPageNum());
        return new ModelAndView("/underwriting/underwritingSelectByDate");
    }

    /**
     * 核保人员可通过统计功能栏，
     * 输入时间及选择维度（预核保提交/预核保结论回复）后，下载录入时间段的所需统计明细。
     * @param keyword
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/statistical", method = RequestMethod.GET)
    @ApiOperation(value = "工作量统计（代办统计/历史统计）", notes = "工作量统计（代办统计/历史统计） 参数keyword-时间间隔，type-类型（1为统计代办，2为统计历史）",
            httpMethod = "GET")
    public void statistical(String keyword, Integer type, HttpServletResponse response) throws IOException {
        underwritingService.statistical(keyword, type, response);
    }

}
