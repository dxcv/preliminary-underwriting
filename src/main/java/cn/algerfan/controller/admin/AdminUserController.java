package cn.algerfan.controller.admin;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Result;
import cn.algerfan.domain.User;
import cn.algerfan.dto.UserDTO;
import cn.algerfan.util.AesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  管理员
 * </p>
 *
 * @author algerfan
 * @since 2019/4/15 15
 */
@Controller
@RequestMapping("/admin/user")
@Api(value = "管理员管理", tags = "管理员管理")
public class AdminUserController extends BaseController {

    /**
     * 退出登录
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "out", method = RequestMethod.GET)
    @ApiOperation(value = "退出登录", httpMethod = "GET")
    public ModelAndView out(HttpSession session){
        session.removeAttribute("user");
        return new ModelAndView("adminLogin");
    }

    /**
     * 添加管理员
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "添加管理员——ajax请求", notes = "添加管理员——ajax请求）——>" +
            "角色role（普通管理员为100，超级管理员为200），用户名userName，密码password，" +
            "姓名name（选填），电话phone（选填）", httpMethod = "POST")
    public Result addUser(User user) {
        return userService.addUser(user);
    }

    /**
     * 删除管理员
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    @ApiOperation(value = "删除管理员——ajax请求", notes = "参数：管理员id-userId",
            httpMethod = "DELETE", response = Result.class)
    public Result deleteUser(Integer userId) {
        return userService.deleteUser(userId);
    }

    /**
     * 跳转到更新页面
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
    @ApiOperation(value = "跳转到更新页面", notes = "参数：管理员id-userId", httpMethod = "GET")
    public ModelAndView toUpdate(Model model, Integer userId) {
        if(userId == null || userId == 0) {
            model.addAttribute("msg", "查询失败");
            return new ModelAndView("redirect:/admin/user/select");
        }
        User user = userService.getUserById(userId);
        if(user==null) {
            model.addAttribute("msg", "查询失败");
            return new ModelAndView("redirect:/admin/user/select");
        }
        try {
            user.setPassword(AesUtil.aesDecrypt(user.getPassword(),"lovewlgzs5201314"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("查询成功");
        model.addAttribute("user",user);
        log.info("查询成功："+user);
        return new ModelAndView("user/updateUser");
    }

    /**
     * 跳转到修改个人信息
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping(value = "/toUpdatePerson", method = RequestMethod.GET)
    @ApiOperation(value = "跳转到修改个人信息", notes = "参数：管理员id-userId", httpMethod = "GET")
    public ModelAndView toUpdatePerson(Model model, Integer userId) {
        if(userId == null || userId == 0) {
            model.addAttribute("msg", "查询失败");
            return new ModelAndView("redirect:/admin/user/select");
        }
        User user = userService.getUserById(userId);
        if(user==null) {
            model.addAttribute("msg", "查询失败");
            return new ModelAndView("redirect:/admin/user/select");
        }
        try {
            user.setPassword(AesUtil.aesDecrypt(user.getPassword(),"lovewlgzs5201314"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("查询成功");
        model.addAttribute("user",user);
        log.info("查询成功："+user);
        return new ModelAndView("user/updatePerson");
    }

    /**
     * 更新管理员信息
     * @param userId
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "更新管理员信息——ajax请求",
            notes = "参数：管理员id-userId，角色role（普通管理员为100，超级管理员为200），用户名userName，" +
                    "密码password，姓名name（选填），电话phone（选填）",
            httpMethod = "PUT", response = Result.class)
    public Result updateUser(Integer userId,User user) {
        return userService.updateUser(userId, user);
    }

    /**
     * 后台查找所有管理员
     * @param model
     * @return
     */
    @RequestMapping(value = "/select",method = RequestMethod.GET)
    @ApiOperation(value = "后台查找所有管理员", notes = "后台查找所有管理员）——>" +
            "userId，用户名userName，姓名name，电话phone，角色role", httpMethod = "GET")
    public ModelAndView select(Model model) {
        List<UserDTO> allUser = userService.getAllUser();
        log.info("查询成功："+allUser);
        model.addAttribute("userList", allUser);
        return new ModelAndView("user/userList");
    }

    /**
     * 修改管理员权限
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改管理员权限——ajax请求",
            notes = "参数：管理员id-userId",
            httpMethod = "POST", response = Result.class)
    public Result updateAdministrator(Integer userId) {
        return userService.updateAdministrator(userId);
    }

}
