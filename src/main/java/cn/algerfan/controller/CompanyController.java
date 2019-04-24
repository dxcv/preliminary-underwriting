package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "小程序部分 公司Controller", tags = "小程序部分 公司Controller")
public class CompanyController extends BaseController {

    /**
     * 查询所有公司——小程序部分
     */
    @ResponseBody
    @RequestMapping(value = "select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有公司——小程序部分",
            httpMethod = "GET", response = Result.class)
    public Map<String, Object> select() {
        return companyService.selectAllCompany();
    }

}
