package cn.algerfan.controller.admin;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Company;
import cn.algerfan.domain.Result;
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
@Api(value = "后台公司管理", tags = "后台公司管理")
public class AdminCompanyController extends BaseController {

    /**
     * 新增公司
     * @param company
     * @return Result
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "新增公司——ajax请求", notes = "参数：公司-company，公司简称-firm，工号规则（4位）-jobNumber",
            httpMethod = "POST", response = Result.class)
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
    @ApiOperation(value = "删除公司——ajax请求", notes = "参数：公司id-companyId",
            httpMethod = "DELETE", response = Result.class)
    public Result delete(Integer companyId) {
        log.info(companyId);
        return companyService.delete(companyId);
    }

    /**
     * 跳转到更新页面
     * @param companyId
     * @param model
     */
    @RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
    @ApiOperation(value = "跳转到更新页面", notes = "参数：公司id-companyId", httpMethod = "GET")
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
        model.addAttribute("company",company);
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
    @ApiOperation(value = "更新公司信息——ajax请求",
            notes = "参数：公司id-companyId，公司-company，公司简称-firm，工号规则（4位）-jobNumber",
            httpMethod = "PUT", response = Result.class)
    public Result update(Integer companyId,Company company) {
        log.info(company);
        return companyService.update(companyId,company);
    }

    /**
     * 查询公司或搜索公司 搜索加上参数keyword（公司）
     * @param keyword
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询公司或搜索公司", notes = "查询公司或搜索公司 搜索加上参数keyword（公司）",
            httpMethod = "GET")
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
