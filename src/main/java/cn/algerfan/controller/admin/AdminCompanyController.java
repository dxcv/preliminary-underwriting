package cn.algerfan.controller.admin;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Company;
import cn.algerfan.domain.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 *  后台公司管理
 * </p>
 *
 * @author algerfan
 * @since 2019/4/18 11
 */
@Controller
@RequestMapping("/admin/company")
public class AdminCompanyController extends BaseController {

    /**
     * 新增公司
     * @param company
     * @return Result
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(Company company) {
        return companyService.insert(company);
    }

    /**
     * 删除公司
     * @param companyId
     * @return Result
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Result delete(Integer companyId) {
        return companyService.delete(companyId);
    }

    /**
     * 跳转更新
     * @param companyId
     * @param model
     */
    @RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
    public ModelAndView toUpdate(Integer companyId, Model model) {
        if(companyId == null || companyId == 0) {
            model.addAttribute("msg", "查询失败");
            return new ModelAndView("redirect:/admin/company/select");
        }
        Company company = companyService.toUpdate(companyId);
        if(company==null) {
            model.addAttribute("msg", "查询失败");
            return new ModelAndView("redirect:/admin/company/select");
        }
        model.addAttribute("查询成功");
        log.info("查询成功："+company);
        return new ModelAndView("company/updateCompany");
    }

    /**
     * 更新公司信息
     * @param companyId
     * @param company
     * @return Result
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(Integer companyId,Company company) {
        return companyService.update(companyId,company);
    }

    /**
     * 查询公司或搜索公司 关键词keyword
     * @param keyword
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public ModelAndView select(String keyword, Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize) {
        PageInfo<Company> select = companyService.select(keyword, pageNum, pageSize);
        log.info("查询成功："+select.getList());
        model.addAttribute("list", select.getList());
        model.addAttribute("pages",select.getPages());
        model.addAttribute("pageNum",select.getPageNum());
        return new ModelAndView("company/company");
    }

}
