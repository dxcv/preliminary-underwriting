package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/administrator")
@Api(value = "登录退出", tags = "登录退出")
public class AdminLoginController extends BaseController {

    /**
     * 跳转到后台登录
     * @return
     */
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
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
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "后台登录——ajax请求", notes = "后台登录——ajax请求）——>" +
            "参数：userName-用户名，password-密码", httpMethod = "GET")
    public Result adminLogin(String userName, String password, HttpServletRequest request) {
        return userService.adminLogin(userName, password, request);
    }

}
