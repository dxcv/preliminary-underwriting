package cn.algerfan.controller.admin;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  后台发送模版
 * </p>
 *
 * @author algerfan
 * @since 2019/4/23 15
 */
@Controller
@RequestMapping("/admin/template")
@Api(value = "发送模版", tags = "后台发送模版")
public class AdminSendTemplate extends BaseController {

    /**
     * 发送模版
     * @param underwritingId    预核保id
     * @param auditResult     申请结果
     * @param note             备注
     */
    @ResponseBody
    @RequestMapping("/send")
    @ApiOperation(value = "发送模版——小程序部分", notes = "发送模版 参数underwritingId-预核保id，auditResult-申请结果，note-备注",
            httpMethod = "GET")
    public Result send(Integer underwritingId, String auditResult, String note) {
        return underwritingService.send(underwritingId, auditResult, note);
    }

}
