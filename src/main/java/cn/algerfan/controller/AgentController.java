package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 *  小程序部分 代理人Controller层
 * </p>
 *
 * @author algerfan
 * @since 2019/4/15 15
 */
@Controller
@RequestMapping("/agent")
@Api(value = "小程序部分 代理人Controller层", tags = "小程序部分 代理人Controller层")
public class AgentController extends BaseController {

    /**
     * 注册或登录代理人——小程序部分
     * @param employeeId 员工号
     * @param company 公司名
     * @param encryptedData
     * @param iv
     * @param code
     * @return map用户信息
     */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ApiOperation(value = "注册或登录代理人——小程序部分",
            notes = "参数：工号-employeeId，公司名-company，用户信息三个参数",
            httpMethod = "POST", response = Map.class)
    public Map<String, Object> register(String employeeId, String company,
                                        String encryptedData, String iv, String code){
        return agentService.register(employeeId, company, encryptedData, iv, code, request);
    }

}
