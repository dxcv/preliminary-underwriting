package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 *  小程序部分 公司Controller
 * </p>
 *
 * @author algerfan
 * @since 2019/4/19 16
 */
@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "select", method = RequestMethod.GET)
    public Map<String, Object> select() {
        return companyService.selectAllCompany();
    }

}
