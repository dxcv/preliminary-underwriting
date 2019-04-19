package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Result;
import cn.algerfan.domain.Underwriting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  核保人Controller层
 * </p>
 *
 * @author algerfan
 * @since 2019/4/17 08
 */
@Controller
@RequestMapping("/underwriting")
public class UnderwritingController extends BaseController {

    /**
     * 新增核保人
     * @param underwriting ---> name 姓名、sex 性别、age 年龄、introduce 疾病史介绍、file 文件
     * @return Result状态码、状态信息
     */
    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result insert(Underwriting underwriting, @RequestParam("file") MultipartFile[] myFileNames) {
        return underwritingService.insert(underwriting,myFileNames);
    }

    /**
     * 查询代理人所有核保人
     * @param openid 微信用户openid
     * @return json数据
     */
    @ResponseBody
    @RequestMapping(value = "/findUnderwriting",method = RequestMethod.GET)
    public Result findUnderwriting(String openid) {
        return underwritingService.findUnderwriting(openid);
    }

}
