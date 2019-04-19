package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.User;
import cn.algerfan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  用户测试controller
 * </p>
 *
 * @author algerfan
 * @since 2019/4/15 15
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    private final UserService userService;
    private String prefix = "user/";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/getAllUser")
    public String getAllUser(ModelMap modelMap) {
        modelMap.put("userList", userService.getAllUser());
        return prefix + "user_list";
    }

    @RequestMapping(value = "/getUserDetailForm")
    public String getUserDetailForm(ModelMap modelMap, Integer id) {
        if (id != null) {
            modelMap.put("user", userService.getUserById(id));
        }
        return prefix + "user_detail";
    }

    @RequestMapping(value = "/addUser")
    public String addUser(User user) {
        userService.addUser(user);
        return "redirect:getAllUser";
    }

    @RequestMapping(value = "/updateUser")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:getAllUser";
    }

    @RequestMapping(value = "/deleteUser")
    @ResponseBody
    public String deleteUser(@RequestParam(value = "ids[]") Integer[] ids) {
        userService.deleteUser(ids);
        return "true";
    }
}
