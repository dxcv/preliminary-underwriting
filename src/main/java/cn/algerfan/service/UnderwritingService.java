package cn.algerfan.service;

import cn.algerfan.domain.Result;
import cn.algerfan.domain.Underwriting;
import cn.algerfan.dto.UnderwritingTime;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <p>
 *  核保人服务类
 * </p>
 *
 * @author algerfan
 * @since 2019/4/17 08
 */
public interface UnderwritingService {

    /**
     * 小程序添加
     * @param formId
     * @param underwriting
     * @param encryptedData
     * @param iv
     * @param code
     * @return
     */
    Map<String,Object> insert(String formId, Underwriting underwriting, String encryptedData, String iv, String code);

    /**
     * 小程序上传
     * @param formId
     * @param multipartFiles
     * @param encryptedData
     * @param iv
     * @param code
     * @return
     */
    Map<String, Object> upload(String formId, MultipartFile[] multipartFiles, String encryptedData, String iv, String code);

    /**
     * 小程序查询
     * @param encryptedData
     * @param iv
     * @param code
     * @return
     */
    Map<String,Object> findUnderwriting(String encryptedData, String iv, String code);

    /**
     * 后台查询代办
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UnderwritingTime> select(String keyword, int pageNum, int pageSize);

    /**
     * 后台根据id查询
     * @param underwritingId
     * @return
     */
    Underwriting selectById(Integer underwritingId);

    /**
     * 后台查询历史
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UnderwritingTime> selectHistory(String keyword, int pageNum, int pageSize);

    /**
     * 后台根据时间查询历史
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
//    PageInfo<UnderwritingTime> selectByDate(String keyword, int pageNum, int pageSize);

    /**
     * 核保人员可通过统计功能栏，
     * 输入时间及选择维度（预核保提交/预核保结论回复）后，下载录入时间段的所需统计明细。
     * @param keyword
     * @param type
     * @param response
     * @return
     */
    void statistical(String keyword, Integer type, HttpServletResponse response) throws IOException;

    /**
     * 小程序发送模版
     * @param underwritingId
     * @param auditResult
     * @param note
     * @return
     */
    Result send(Integer underwritingId, String auditResult, String note);

}
