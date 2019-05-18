package cn.algerfan.service;

import cn.algerfan.domain.Result;
import cn.algerfan.domain.Underwriting;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

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
     * @param name
     * @param multipartFiles
     * @param encryptedData
     * @param iv
     * @param code
     * @return
     */
    Map<String, Object> upload(String name, MultipartFile[] multipartFiles, String encryptedData, String iv, String code);

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
    PageInfo<Underwriting> select(String keyword, int pageNum, int pageSize);

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
    PageInfo<Underwriting> selectHistory(String keyword, int pageNum, int pageSize);

    /**
     * 后台根据时间查询历史
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Underwriting> selectByDate(String keyword, int pageNum, int pageSize);

    /**
     * 小程序发送模版
     * @param underwritingId
     * @param auditResult
     * @param note
     * @return
     */
    Result send(Integer underwritingId, String auditResult, String note);

}
