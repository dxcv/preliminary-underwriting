package cn.algerfan.controller.admin;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Agent;
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
 *  后台部分 代理人管理
 * </p>
 *
 * @author algerfan
 * @since 2019/5/17
 */
@Controller
@RequestMapping("/admin/agent")
@Api(value = "后台部分 代理人管理", tags = "后台部分 代理人管理")
public class AdminAgentController extends BaseController {

    /**
     * 删除代理人
     * @param agentId
     * @return Result
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    @ApiOperation(value = "删除代理人——ajax请求", notes = "参数：代理人id-agentId",
            httpMethod = "DELETE", response = Result.class)
    public Result delete(Integer agentId) {
        return agentService.delete(agentId);
    }

    /**
     * 跳转到更新页面
     * @param agentId
     * @param model
     */
    @RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
    @ApiOperation(value = "跳转到更新页面", notes = "参数：公司id-agentId", httpMethod = "GET")
    public ModelAndView toUpdate(Integer agentId, Model model) {
        if(agentId == null || agentId == 0) {
            model.addAttribute("msg", "查询失败");
            return new ModelAndView("redirect:/admin/agent/select");
        }
        Agent agent = agentService.selectById(agentId);
        if(agent==null) {
            model.addAttribute("msg", "查询不存在");
            return new ModelAndView("redirect:/admin/agent/select");
        }
        model.addAttribute("查询成功");
        model.addAttribute("agent",agent);
        log.info("查询成功："+agent);
        return new ModelAndView("agent/updateAgent");
    }

    /**
     * 更新代理人信息
     * @param agentId
     * @param agent
     * @return Result
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "更新代理人信息——ajax请求",
            notes = "参数：代理人id-agentId，代理人工号-employeeId，代理人公司简称-firm",
            httpMethod = "PUT", response = Result.class)
    public Result update(Integer agentId,Agent agent) {
        return agentService.update(agentId,agent);
    }

    /**
     * 查询代理人或搜索代理人 搜索加上参数nickname（昵称）
     * @param nickname
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询代理人或搜索代理人", notes = "查询代理人或搜索代理人 搜索加上参数nickname（昵称）",
            httpMethod = "GET")
    public ModelAndView select(String nickname, Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize) {
        PageInfo<Agent> select = agentService.select(nickname, pageNum, pageSize);
        log.info("查询成功："+select.getList());
        model.addAttribute("list", select.getList());
        model.addAttribute("pages",select.getPages());
        model.addAttribute("pageNum",select.getPageNum());
        model.addAttribute("nickname",nickname);
        return new ModelAndView("agent/agent");
    }

}
