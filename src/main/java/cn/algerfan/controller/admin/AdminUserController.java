package cn.algerfan.controller.admin;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Result;
import cn.algerfan.domain.User;
import cn.algerfan.dto.UserDTO;
import cn.algerfan.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
public class AdminUserController extends BaseController {

    @RequestMapping(method = RequestMethod.POST)
    public Result addUser(User user) {
        return userService.addUser(user);
    }

    /**
     * 后台查找所有用户
     * @param model
     * @return
     */
    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    @ApiOperation(value = "后台查找所有用户", notes = "后台查找所有用户）——>" +
            "userId，用户名userName，姓名name，电话phone，角色role", httpMethod = "GET")
    public ModelAndView getAllUser(Model model) {
        List<UserDTO> allUser = userService.getAllUser();
        log.info("查询成功："+allUser);
        model.addAttribute("userList", allUser);
        return new ModelAndView("user/userList");
    }

    @RequestMapping(value = "/getUserDetailForm")
    public String getUserDetailForm(ModelMap modelMap, Integer id) {
        if (id != null) {
            modelMap.put("user", userService.getUserById(id));
        }
        return "user_detail";
    }

    @RequestMapping(value = "/updateUser")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:getAllUser";
    }

    @RequestMapping(value = "/deleteUser")
    @ResponseBody
    public String deleteUser(Integer ids) {
        userService.deleteUser(ids);
        return "true";
    }
}
