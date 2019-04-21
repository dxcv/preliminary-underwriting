package cn.algerfan.service;

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

    Map<String,Object> insert(String formId, Underwriting underwriting, String encryptedData, String iv, String code);

    Map<String, Object> upload(String name, MultipartFile[] multipartFiles, String encryptedData, String iv, String code);

    Map<String,Object> findUnderwriting(String encryptedData, String iv, String code);

    PageInfo<Underwriting> select(String keyword, int pageNum, int pageSize);

}
