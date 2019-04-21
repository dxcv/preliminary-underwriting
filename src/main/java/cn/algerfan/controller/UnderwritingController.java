package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Underwriting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *  小程序部分 核保人Controller层
 * </p>
 *
 * @author algerfan
 * @since 2019/4/17 08
 */
@Controller
@RequestMapping("/underwriting")
public class UnderwritingController extends BaseController {

    /**
     * 新增核保人第一部分
     * @param formId   ---> 表单id
     * @param underwriting ---> name 姓名、sex 性别、birthday 出生日期、phone 手机号（选填）、introduce 疾病史介绍
     * @param encryptedData
     * @param iv
     * @param code
     * @return Result状态码、状态信息
     */
    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Map<String,Object> insert(String formId, Underwriting underwriting,
                                     String encryptedData, String iv, String code) {
        return underwritingService.insert(formId,underwriting,encryptedData,iv,code);
    }

    /**
     * 新增核保人第二部分
     * @param name   ---> 核保人姓名
     * @param encryptedData
     * @param iv
     * @param code
     * @return Result状态码、状态信息
     */
    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Map<String,Object> upload(String name, @RequestParam("file") MultipartFile[] multipartFiles,
                                     String encryptedData, String iv, String code) {
        return underwritingService.upload(name,multipartFiles,encryptedData,iv,code);
    }

    /**
     * 查询代理人所有核保人
     * @param encryptedData
     * @param iv
     * @param code
     * @return Result状态码、状态信息
     */
    @ResponseBody
    @RequestMapping(value = "/findUnderwriting",method = RequestMethod.GET)
    public Map<String,Object> findUnderwriting(String encryptedData, String iv, String code) {
        return underwritingService.findUnderwriting(encryptedData,iv,code);
    }

}
