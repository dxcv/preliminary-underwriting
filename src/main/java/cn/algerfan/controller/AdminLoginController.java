package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  后台登录
 * </p>
 *
 * @author algerfan
 * @since 2019/5/27 10
 */
@Controller
public class AdminLoginController extends BaseController {

    /**
     * 获取16位秘钥随机数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/administrator/random")
    public Result random() {
        String filename= RandomStringUtils.randomAlphanumeric(16);
        session.setAttribute("random",filename);
        session.setMaxInactiveInterval(60 * 20);
        Result result = new Result();
        result.setMsg(filename);
        result.setCode(1);
        return result;
    }

    /**
     * 跳转到后台登录
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "跳转到后台登录", httpMethod = "GET")
    public ModelAndView toLogins() {
        return new ModelAndView("adminLogin");
    }

    /**
     * 跳转到后台登录
     * @return
     */
    @RequestMapping(value = "/administrator/toLogin", method = RequestMethod.GET)
    @ApiOperation(value = "跳转到后台登录", httpMethod = "GET")
    public ModelAndView toLogin() {
        return new ModelAndView("adminLogin");
    }

    /**
     * 后台登录
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/administrator/login", method = RequestMethod.GET)
    @ApiOperation(value = "后台登录——ajax请求", notes = "后台登录——ajax请求）——>" +
            "参数：userName-用户名，password-密码", httpMethod = "GET")
    public Result adminLogin(String userName, String password, HttpServletRequest request) {
        return userService.adminLogin(userName, password, request);
    }

}
